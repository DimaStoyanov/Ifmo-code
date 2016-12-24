package ru.ifmo.droid2016.rzddemo.cache;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.ifmo.droid2016.rzddemo.model.TimeTableContract;
import ru.ifmo.droid2016.rzddemo.model.TimetableEntry;
import ru.ifmo.droid2016.rzddemo.utils.TimeUtils;

import static ru.ifmo.droid2016.rzddemo.Constants.LOG_DATE_FORMAT;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.ARRIVAL_STATION_ID;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.ARRIVAL_STATION_NAME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.ARRIVAL_TIME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.DATE;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.DEPARTURE_STATION_ID;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.DEPARTURE_STATION_NAME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.DEPARTURE_TIME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.ROUTE_END_STATION_NAME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.ROUTE_START_STATION_NAME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.TRAIN_NAME;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Columns.TRAIN_ROUTE_ID;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Table.V1_COLUMNS;
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Table.V2_COLUMNS;

/**
 * Кэш расписания поездов.
 * <p>
 * Ключом является комбинация трех значений:
 * ID станции отправления, ID станции прибытия, дата в москомском часовом поясе
 * <p>
 * Единицей хранения является список поездов - {@link TimetableEntry}.
 */

public class TimetableCache {

    @NonNull
    private final Context context;

    /**
     * Версия модели данных, с которой работает кэщ.
     */
    @DataSchemeVersion
    private final int version;


    private final TimeTableDBHandler databaseHandler;
    private final SimpleDateFormat schemaDate;
    private final SimpleDateFormat schemaDateTime;

    /**
     * Создает экземпляр кэша с указанной версией модели данных.
     * <p>
     * Может вызываться на лююбом (в том числе UI потоке). Может быть создано несколько инстансов
     * {@link TimetableCache} -- все они должны потокобезопасно работать с одним физическим кэшом.
     */
    @AnyThread
    public TimetableCache(@NonNull Context context,
                          @DataSchemeVersion int version) {
        this.context = context.getApplicationContext();
        this.version = version;
        this.databaseHandler = TimeTableDBHandler.getInstance(this.context, this.version);
        schemaDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        schemaDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA);
    }

    /**
     * Берет из кэша расписание - список всех поездов, следующих по указанному маршруту с
     * отправлением в указанную дату.
     *
     * @param fromStationId ID станции отправления
     * @param toStationId   ID станции прибытия
     * @param dateMsk       дата в московском часовом поясе
     * @return - список {@link TimetableEntry}
     * @throws FileNotFoundException - если в кэше отсуствуют запрашиваемые данные.
     */
    @WorkerThread
    @NonNull
    public List<TimetableEntry> get(@NonNull String fromStationId,
                                    @NonNull String toStationId,
                                    @NonNull Calendar dateMsk)
            throws FileNotFoundException {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();

        Cursor cursor = db.query(
                TimeTableContract.Table.NAME,
                ((version == DataSchemeVersion.V2) ? V2_COLUMNS : V1_COLUMNS), DATE + "=? AND " +
                        DEPARTURE_STATION_ID + "=? AND " + ARRIVAL_STATION_ID + "=?",
                new String[]{schemaDate.format(dateMsk.getTime()), fromStationId, toStationId}, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                List<TimetableEntry> timetable = new ArrayList<>();

                do {
                    TimetableEntry entry;
                    try {
                        int cnt = 0;
                        entry = new TimetableEntry(cursor.getString(cnt++), cursor.getString(cnt++),
                                getTime(cursor.getString(cnt++)),
                                cursor.getString(cnt++),
                                cursor.getString(cnt++),
                                getTime(cursor.getString(cnt++)),
                                cursor.getString(cnt++),
                                ((version == DataSchemeVersion.V2) ? cursor.getString(cnt++) : null),
                                cursor.getString(cnt++),
                                cursor.getString(cnt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }
                    timetable.add(entry);
                } while (cursor.moveToNext());
                return timetable;
            }

            throw new FileNotFoundException("No data in timetable cache for: fromStationId=" +
                    fromStationId + ", toStationId=" + toStationId +
                    ", dateMsk=" + LOG_DATE_FORMAT.format(dateMsk.getTime()));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Calendar getTime(String string) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeUtils.getMskTimeZone());
        calendar.setTime(schemaDateTime.parse(string));
        return calendar;
    }

    @WorkerThread
    public void put(@NonNull String fromStationId,
                    @NonNull String toStationId,
                    @NonNull Calendar dateMsk, @NonNull List<TimetableEntry> timetable) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(
                "INSERT INTO " + TimeTableContract.Table.NAME + " ("
                        + DATE + "," + DEPARTURE_STATION_ID + ","
                        + DEPARTURE_STATION_NAME + "," + DEPARTURE_TIME + ","
                        + ARRIVAL_STATION_ID + "," + ARRIVAL_STATION_NAME + ","
                        + ARRIVAL_TIME + "," + TRAIN_ROUTE_ID + ","
                        + ((version == DataSchemeVersion.V2) ? TRAIN_NAME + "," : "")
                        + ROUTE_START_STATION_NAME + "," + ROUTE_END_STATION_NAME
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
                        + ((version == DataSchemeVersion.V2) ? ", ?" : "" + ")"));

        db.beginTransaction();
        try {
            for (TimetableEntry entry : timetable) {
                int key = 1;
                statement.bindString(key++, schemaDate.format(dateMsk.getTime()));
                statement.bindString(key++, entry.departureStationId);
                statement.bindString(key++, entry.departureStationName);
                statement.bindString(key++, schemaDateTime.format(entry.departureTime.getTime()));
                statement.bindString(key++, entry.arrivalStationId);
                statement.bindString(key++, entry.arrivalStationName);
                statement.bindString(key++, schemaDateTime.format(entry.arrivalTime.getTime()));
                statement.bindString(key++, entry.trainRouteId);
                if (version == DataSchemeVersion.V2) {
                    if (entry.trainName == null) {
                        statement.bindNull(key++);
                    } else {
                        statement.bindString(key++, entry.trainName);
                    }
                }
                statement.bindString(key++, entry.routeStartStationName);
                statement.bindString(key, entry.routeEndStationName);
                statement.executeInsert();
            }

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            db.endTransaction();
        }

    }

    public void close() {
        databaseHandler.close();
    }
}

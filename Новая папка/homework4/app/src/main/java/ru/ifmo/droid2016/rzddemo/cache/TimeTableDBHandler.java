package ru.ifmo.droid2016.rzddemo.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
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
import static ru.ifmo.droid2016.rzddemo.model.TimeTableContract.Table.NAME;

/**
 * Created by Dima Stoyanov on 05.12.2016.
 * Project homework4
 * Start time : 0:24
 */

public class TimeTableDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rzd.timetable.db";

    @DataSchemeVersion
    private int version;

    private static volatile TimeTableDBHandler instance;

    public static TimeTableDBHandler getInstance(Context context, @DataSchemeVersion int version) {
        if (instance == null) {
            synchronized (TimeTableDBHandler.class) {
                if (instance == null) {
                    instance = new TimeTableDBHandler(context, version);
                }
            }
        }
        return instance;
    }


    TimeTableDBHandler(Context context, @DataSchemeVersion int version) {
        super(context, DATABASE_NAME, null, version);
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + NAME + " " + "ADD COLUMN " + TRAIN_NAME + " TEXT");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DOWNGRADE_TABLE_NAME = NAME + "_downgrade";
        db.execSQL("ALTER TABLE " + NAME + " " + "RENAME TO " + DOWNGRADE_TABLE_NAME);
        createTable(db, DataSchemeVersion.V1);
        String columns = "" + _ID + "," + DATE + "," + DEPARTURE_STATION_ID + "," +
                DEPARTURE_STATION_NAME + "," + DEPARTURE_TIME + "," + ARRIVAL_STATION_ID + "," +
                ARRIVAL_STATION_NAME + "," + ARRIVAL_TIME + "," + TRAIN_ROUTE_ID + "," +
                ROUTE_START_STATION_NAME + "," + ROUTE_END_STATION_NAME;

        db.execSQL("INSERT INTO " + NAME + " (" + columns + ") "
                + "SELECT " + columns + " "
                + "FROM " + DOWNGRADE_TABLE_NAME);
        db.execSQL("DROP TABLE " + DOWNGRADE_TABLE_NAME);
    }

    private void createTable(SQLiteDatabase db, int version) {
        db.execSQL(
                "CREATE TABLE " + NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY" + ","
                        + DATE + " TEXT" + ","
                        + DEPARTURE_STATION_ID + " TEXT" + ","
                        + DEPARTURE_STATION_NAME + " TEXT" + ","
                        + DEPARTURE_TIME + " TEXT" + ","
                        + ARRIVAL_STATION_ID + " TEXT" + ","
                        + ARRIVAL_STATION_NAME + " TEXT" + ","
                        + ARRIVAL_TIME + " TEXT" + ","
                        + TRAIN_ROUTE_ID + " TEXT" + ","
                        + ((version == DataSchemeVersion.V2)
                        ? TRAIN_NAME + " TEXT" + "," : "")
                        + ROUTE_START_STATION_NAME + " TEXT" + ","
                        + ROUTE_END_STATION_NAME + " TEXT"
                        + ")"
        );
    }
}

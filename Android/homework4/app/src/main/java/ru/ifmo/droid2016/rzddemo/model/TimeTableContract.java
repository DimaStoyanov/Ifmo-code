package ru.ifmo.droid2016.rzddemo.model;

import android.provider.BaseColumns;

/**
 * Created by Dima Stoyanov on 05.12.2016.
 * Project homework4
 * Start time : 0:21
 */

public final class TimeTableContract {

    public interface Columns extends BaseColumns {

        String DATE = "date";
        String DEPARTURE_STATION_ID = "departure_station_id";
        String DEPARTURE_STATION_NAME = "departure_station_name";
        String DEPARTURE_TIME = "departure_time";
        String ARRIVAL_STATION_ID = "arrival_station_id";
        String ARRIVAL_STATION_NAME = "arrival_station_name";
        String ARRIVAL_TIME = "arrival_time";
        String TRAIN_ROUTE_ID = "train_route_id";
        String TRAIN_NAME = "train_name";
        String ROUTE_START_STATION_NAME = "route_start_station_name";
        String ROUTE_END_STATION_NAME = "route_end_station_name";
    }

    public static final class Table implements Columns {
        public static final String NAME = "timetable";
        public static String[] V1_COLUMNS = new String[]{DEPARTURE_STATION_ID, DEPARTURE_STATION_NAME, DEPARTURE_TIME, ARRIVAL_STATION_ID, ARRIVAL_STATION_NAME,
                ARRIVAL_TIME, TRAIN_ROUTE_ID, ROUTE_START_STATION_NAME, ROUTE_END_STATION_NAME};
        public static String[] V2_COLUMNS = new String[]{DEPARTURE_STATION_ID, DEPARTURE_STATION_NAME, DEPARTURE_TIME, ARRIVAL_STATION_ID, ARRIVAL_STATION_NAME,
                ARRIVAL_TIME, TRAIN_ROUTE_ID, TRAIN_NAME, ROUTE_START_STATION_NAME, ROUTE_END_STATION_NAME};
    }
}

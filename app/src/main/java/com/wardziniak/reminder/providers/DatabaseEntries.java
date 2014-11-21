package com.wardziniak.reminder.providers;

import android.provider.BaseColumns;

/**
 * Created by wardziniak on 11/12/14.
 */
public final class DatabaseEntries {

    public static final int ACTIVE_ALARM_STATUS = 0;
    public static final int FIRED_ALARM_STATUS = 1;
    public static final int REMOVED_ALARM_STATUS = 2;

    public static final String[] sAlarmProjection = {
            AlarmEntry.COLUMN_NAME_ALA_ID,
            AlarmEntry.COLUMN_NAME_ALA_MESSAGE,
            AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP,
            AlarmEntry.COLUMN_NAME_ALA_STATUS
    };

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.wardziniak.reminder.provider";
    public static final String ALARM_PATH = "/alarms/";
    public static final String ACTIVE_ALARM_PATH = "/active_alarms/";

    public static final String ALARM_CONTENT_DIR_TYPE = "vnd.android.cursor.dir/com.wardziniak.reminder.alarm";
    public static final String ALARM_CONTENT_ITEM_TYPE = "vnd.andorid.cursor.item/com.wardziniak.reminder.alarm";

    public static final class AlarmEntry implements BaseColumns {
        public static final String TABLE_NAME = "alarms";
        public static final String COLUMN_NAME_ALA_ID = "ala_id";
        public static final String COLUMN_NAME_ALA_TIMESTAMP = "ala_timestamp";
        public static final String COLUMN_NAME_ALA_MESSAGE = "ala_message";
        public static final String COLUMN_NAME_ALA_STATUS = "ala_status";
    }


}

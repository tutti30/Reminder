package com.wardziniak.reminder.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wardziniak on 11/12/14.
 */
public class ReminderDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "reminder.db";

    private static final String SQL_CREATE_ALARMS_TABLE = "CREATE TABLE " + DatabaseEntries.AlarmEntry.TABLE_NAME + "("
            + DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_MESSAGE + " TEXT, "
            + DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP + " LONG, "
            + DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_STATUS + " INTEGER DEFAULT 0);";

    public ReminderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ALARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE " + DatabaseEntries.AlarmEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

package com.wardziniak.reminder.fragments.lists;

import android.database.Cursor;

import com.wardziniak.reminder.providers.DatabaseEntries;

/**
 * Created by wardziniak on 11/14/14.
 */
public class AlarmItem {

    private long id;
    private String alarmMessage;
    private long triggerTime;

    public AlarmItem(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID));
        this.alarmMessage = cursor.getString(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_MESSAGE));
        this.triggerTime = cursor.getLong(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP));
    }

    public long getId() {
        return id;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public long getTriggerTime() {
        return triggerTime;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof AlarmItem))
            return false;
        AlarmItem ai = (AlarmItem) o;
        return id == ai.getId();
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public String toString() {
        return alarmMessage;
    }
}

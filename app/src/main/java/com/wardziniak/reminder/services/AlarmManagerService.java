package com.wardziniak.reminder.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import com.wardziniak.reminder.fragments.lists.AlarmItem;
import com.wardziniak.reminder.providers.DatabaseEntries;
import com.wardziniak.reminder.receivers.AlarmaReceiver;

import java.net.URI;
import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmManagerService extends IntentService {

    private static final String TAG = "ReminderAlarmManagerService";

    public static final String EXTRA_TRIGGER_TIME = "triggerTime";
    public static final String EXTRA_ALARM_MESSAGE = "alarmMessage";
    public static final String EXTRA_ALARM_ID = "alarmId";
    public static final String ACTION_REMOVE_ALARM = "REMOVE_ALARM";
    public static final String ACTION_ADD_ALARM = "ADD_ALARM";
    public static final String ACTION_EDIT_ALARM = "EDIT_ALARM";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_REMINDER_ALARM = "com.wardziniak.reminder.alarm_fire.";



    public AlarmManagerService() {
        super("AlarmManagerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String acion = intent.getAction();

        if (ACTION_ADD_ALARM.equals(acion)) {
            addAlarm(intent);
            return;
        }
        else if (ACTION_REMOVE_ALARM.equals(acion)) {
            removeAlarm(intent);
            return;
        }
        else if (ACTION_EDIT_ALARM.equals(acion)) {

        }
        Log.d(TAG, "onHandleIntent");
    }


    private void removeAlarm(Intent intent) {
        final long alarmId = intent.getLongExtra(EXTRA_ALARM_ID, 0);
        final String alarmMessage = intent.getStringExtra(EXTRA_ALARM_MESSAGE);
        String selection = DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID + " = ?";
        String[] selectionArgs = new String[] {"" + alarmId};

        Intent i = new Intent(this, AlarmaReceiver.class);
        i.setAction(ACTION_REMINDER_ALARM + alarmId);
        i.putExtra(EXTRA_ALARM_ID, alarmId);
        i.putExtra(EXTRA_ALARM_MESSAGE, alarmMessage);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_STATUS, DatabaseEntries.REMOVED_ALARM_STATUS);
        getApplicationContext().getContentResolver().update(Uri.parse(DatabaseEntries.SCHEME + DatabaseEntries.AUTHORITY + DatabaseEntries.ACTIVE_ALARM_PATH),
                contentValues, selection, selectionArgs);
        //sentResponse(responseMsg);

        Log.d(TAG, "removeAlarm:OK:" + alarmId);
    }

    private void addAlarm(Intent intent) {
        final long triggerTime = intent.getLongExtra(EXTRA_TRIGGER_TIME, 0);
        final String alarmMessage = intent.getStringExtra(EXTRA_ALARM_MESSAGE);
        final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Logging part
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(triggerTime);
        Log.d(TAG, "addAlarm:time::" + triggerTime + ":message:" + alarmMessage);
        //
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP, triggerTime);
        contentValue.put(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_MESSAGE, alarmMessage);
        Uri uri = getContentResolver().insert(Uri.parse(DatabaseEntries.SCHEME + DatabaseEntries.AUTHORITY + DatabaseEntries.ALARM_PATH), contentValue);
        // Prepearing intetn for alarm manager
        Intent i = new Intent(this, AlarmaReceiver.class);
        final long alaId = ContentUris.parseId(uri);
        i.setAction(ACTION_REMINDER_ALARM + alaId);
        i.putExtra(EXTRA_ALARM_MESSAGE, alarmMessage);
        i.putExtra(EXTRA_ALARM_ID, alaId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,  triggerTime, pendingIntent);
        Log.d(TAG, "addAlarm:" + uri);
    }

    public void editAlarm(Intent intent) {

    }

}

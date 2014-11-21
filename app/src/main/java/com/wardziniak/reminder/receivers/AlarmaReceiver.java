package com.wardziniak.reminder.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.wardziniak.reminder.providers.DatabaseEntries;
import com.wardziniak.reminder.services.AlarmManagerService;

public class AlarmaReceiver extends BroadcastReceiver {

    public AlarmaReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Reminder", "AlarmaReceiver:dostalem intent:" + intent.getStringExtra(AlarmManagerService.EXTRA_ALARM_MESSAGE) + ":"
            +intent.getLongExtra(AlarmManagerService.EXTRA_ALARM_ID, 0));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("Alarm").setContentText(intent.getStringExtra(AlarmManagerService.EXTRA_ALARM_MESSAGE))
                .setAutoCancel(true);
        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification);
        // set alarm as passed
        ContentValues cv = new ContentValues();
        cv.put(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_STATUS, DatabaseEntries.FIRED_ALARM_STATUS);
        String selectionClause = DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID + " = ? ";
        String[] selectionArgs = new String[] {""+ intent.getLongExtra(AlarmManagerService.EXTRA_ALARM_ID, 0)};
        context.getContentResolver().update(Uri.parse(DatabaseEntries.SCHEME + DatabaseEntries.AUTHORITY+ DatabaseEntries.ACTIVE_ALARM_PATH), cv, selectionClause, selectionArgs);
    }
}

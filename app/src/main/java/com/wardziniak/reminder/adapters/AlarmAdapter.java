package com.wardziniak.reminder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.activities.AlarmListFragmentInteractionListener;
import com.wardziniak.reminder.fragments.lists.AlarmItem;
import com.wardziniak.reminder.providers.DatabaseEntries;

import java.util.Calendar;

/**
 * Created by wardziniak on 11/14/14.
 */
public class AlarmAdapter<T extends Context & AlarmListFragmentInteractionListener> extends BaseAdapter {

    private static final String TAG = "ReminderAlarmAdapter";

    private Cursor cursor;
    private T contextAlarmListListner;
    private Calendar calendar = Calendar.getInstance();

    public AlarmAdapter(T context) {
        this.contextAlarmListListner = context;
    }

    public AlarmAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount:" + cursor);
        if (cursor == null)
            return 0;
        Log.d(TAG, "getCount:" + cursor.getCount());
        return cursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public AlarmItem getItem(int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            return new AlarmItem(cursor);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (cursor != null && cursor.moveToPosition(position))
            return cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID);
        return 0;
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup viewGroup) {
        Log.d(TAG, "getView:");
        if (cursor == null || (!cursor.moveToPosition(position)))
            return null;
        if (convertedView == null) {
            LayoutInflater inflater = (LayoutInflater) contextAlarmListListner.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertedView = inflater.inflate(R.layout.alarm_list_item, viewGroup, false);
        }
        TextView alarmMessageTextView = (TextView) convertedView.findViewById(R.id.alarmMessage);
        TextView triggerTimeTextView = (TextView) convertedView.findViewById(R.id.timeTrigger);
        ImageView removeAlarmImageView = (ImageView) convertedView.findViewById(R.id.removeAlarm);
        final AlarmItem alarmItem = getItem(position);
        removeAlarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextAlarmListListner.removeAlarm(alarmItem);
            }
        });
        alarmMessageTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_MESSAGE)));
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP)));
        triggerTimeTextView.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
            + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR));
        return convertedView;
    }
}

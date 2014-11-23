package com.wardziniak.reminder.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.fragments.AddAlarmFragment;
import com.wardziniak.reminder.fragments.DatePickerFragment;
import com.wardziniak.reminder.fragments.TimePickerFragment;
import com.wardziniak.reminder.services.AlarmManagerService;

import java.util.Calendar;

public class AddAlarmActivity extends Activity implements AddAlarmFragmentInteractionListener {

    private AddAlarmFragment addAlarmFragment;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final long alarmId = getIntent().getLongExtra(AddAlarmFragment.ARG_ALARM_ID, 0);
        addAlarmFragment = AddAlarmFragment.newInstance(alarmId);
        getFragmentManager().beginTransaction().add(android.R.id.content, addAlarmFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateChange(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        addAlarmFragment.updateDate(calendar);
    }

    @Override
    public void showDatePicker() {
        DialogFragment dialogFragment = DatePickerFragment.newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void refreshAddAlarmFragment() {
        addAlarmFragment.updateDate(calendar);
        addAlarmFragment.updateTime(calendar);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        addAlarmFragment.updateTime(calendar);
    }

    @Override
    public void showTimePicker() {
        DialogFragment dialogFragment = TimePickerFragment.newInstance(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        dialogFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void setAlarm(long alarmId, String message) {
        Log.d("Reminder", "setAlarm:" + alarmId);
        Intent startServiceIntent = new Intent(this, AlarmManagerService.class);
        startServiceIntent.putExtra(AlarmManagerService.EXTRA_TRIGGER_TIME, calendar.getTimeInMillis());
        startServiceIntent.putExtra(AlarmManagerService.EXTRA_ALARM_MESSAGE, message);
        if (alarmId == 0) {
            startServiceIntent.setAction(AlarmManagerService.ACTION_ADD_ALARM);
        }
        else {
            startServiceIntent.setAction(AlarmManagerService.ACTION_EDIT_ALARM);
            startServiceIntent.putExtra(AlarmManagerService.EXTRA_ALARM_ID, alarmId);
        }
        startService(startServiceIntent);
        //setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onAlarmLoad(Calendar calendar) {
        this.calendar = calendar;
    }
}

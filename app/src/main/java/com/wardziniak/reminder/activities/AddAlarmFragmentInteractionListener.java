package com.wardziniak.reminder.activities;

import android.app.TimePickerDialog;

/**
 * Created by wardziniak on 11/13/14.
 */
public interface AddAlarmFragmentInteractionListener extends TimePickerDialog.OnTimeSetListener {

    public void onDateChange(int year, int month, int day);

    public void refreshAddAlarmFragment();

    public void showDatePicker();

    public void showTimePicker();

    public void setAlarm(String message);

}

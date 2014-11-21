package com.wardziniak.reminder.activities;

import com.wardziniak.reminder.fragments.lists.AlarmItem;

/**
 * Created by wardziniak on 11/17/14.
 */
public interface AlarmListFragmentInteractionListener {

    public void onListItemClick(AlarmItem alarmItem);

    public void editAlarm(AlarmItem alarmItem);

    public void removeAlarm(AlarmItem alarmItem);
}

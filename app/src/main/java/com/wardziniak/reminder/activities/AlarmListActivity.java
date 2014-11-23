package com.wardziniak.reminder.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.fragments.AddAlarmFragment;
import com.wardziniak.reminder.fragments.AlarmListFragment;
import com.wardziniak.reminder.fragments.lists.AlarmItem;
import com.wardziniak.reminder.services.AlarmManagerService;

public class AlarmListActivity extends Activity implements AlarmListFragmentInteractionListener {

    public static final String TAG = "ReminderAlarmListActivity";


    public static final int REQUEST_CREATE_NEW_ALARM = 0;

    private AlarmListFragment alarmListFragment;

//    private Messenger mService = null;
//    private boolean bound;
//    private final Messenger messenger = new Messenger(new AlarmManagerHandler());

/*    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            bound = true;
            Message msg = Message.obtain(null, AlarmManagerService.MSG_REGISTER_CLIENT);
            msg.replyTo = messenger;
            try {
                mService.send(msg);
            }
            catch (RemoteException remoteException) {
                // Nie udalo sie skomunikowac
                Log.e(TAG, "Nie udalo sie wyslac odpowiedzi:onServiceConnected:" + msg.what);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            bound = false;
        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmListFragment = new AlarmListFragment();
        getFragmentManager().beginTransaction().add(android.R.id.content, alarmListFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        bindService(new Intent(this, AlarmManagerService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
/*        if (bound) {
            unbindService(mConnection);
            bound = false;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_new_alarm) {
            Intent intent = new Intent(this, AddAlarmActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_NEW_ALARM);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CREATE_NEW_ALARM) {
            //alarmListFragment.getLoaderManager().restartLoader(0, null, alarmListFragment);
        }
    }

    @Override
    public void onListItemClick(AlarmItem alarmItem) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        intent.putExtra(AddAlarmFragment.ARG_ALARM_ID, alarmItem.getId());
        startActivity(intent);
    }

    @Override
    public void editAlarm(AlarmItem alarmItem) {

    }

    @Override
    public void removeAlarm(AlarmItem alarmItem) {
        Intent startServiceIntent = new Intent(this, AlarmManagerService.class);
        startServiceIntent.putExtra(AlarmManagerService.EXTRA_ALARM_ID, alarmItem.getId());
        startServiceIntent.putExtra(AlarmManagerService.EXTRA_ALARM_MESSAGE, alarmItem.getAlarmMessage());
//        startServiceIntent.putExtra(AlarmManagerService.EXTRA_TRIGGER_TIME, alarmItem.getTriggerTime());
        startServiceIntent.setAction(AlarmManagerService.ACTION_REMOVE_ALARM);
        startService(startServiceIntent);

/*        if (!bound)
            return;
        Log.e(TAG, "removeAlarmstart:" + alarmItem.getId());
        Message msg = Message.obtain(null, AlarmManagerService.MSG_REMOVE_ALARM, (int)alarmItem.getId(), 0);
        try {
            mService.send(msg);
        }
        catch (RemoteException re) {
            Log.e(TAG, "Nie udalo sie wyslac odpowiedzi:removeAlarm:" + msg.what);
        }*/
    }


/*    private class AlarmManagerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AlarmManagerService.MSG_ALARM_REMOVED:
                    alarmListFragment.getLoaderManager().restartLoader(0, null, alarmListFragment);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }*/

}

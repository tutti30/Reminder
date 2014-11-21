package com.wardziniak.reminder.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.wardziniak.reminder.activities.AlarmListFragmentInteractionListener;
import com.wardziniak.reminder.adapters.AlarmAdapter;
import com.wardziniak.reminder.providers.DatabaseEntries;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link AlarmListFragmentInteractionListener}
 * interface.
 */
public class AlarmListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ReminderAlarmListFragment";

    private AlarmAdapter alarmAdapter;

    //private SimpleCursorAdapter alarmAdapter;

    private AlarmListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
        //alarmAdapter = new SimpleCursorAdapter(getActivity())
        alarmAdapter = new AlarmAdapter(getActivity());
        setListAdapter(alarmAdapter);
        getLoaderManager().initLoader(0, null, this);
        //setListAdapter(new ArrayAdapter<AlarmItem>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<AlarmItem>()));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AlarmListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AlarmListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            mListener.onListItemClick(alarmAdapter.getItem(position));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader:");
        return new CursorLoader(getActivity(), Uri.parse(DatabaseEntries.SCHEME + DatabaseEntries.AUTHORITY + DatabaseEntries.ACTIVE_ALARM_PATH),
                DatabaseEntries.sAlarmProjection, null, null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished:" + cursor.getCount());
        alarmAdapter.setCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        Log.d(TAG, "onLoaderReset:");
        alarmAdapter.setCursor(null);
    }

}

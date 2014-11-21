package com.wardziniak.reminder.fragments;


import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.providers.DatabaseEntries;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String ARG_ALARM_ID = "alarm_id";

    private Calendar calendar;
    private long alarmId;

    private TextView messageTextView;
    private TextView triggerTimeTextView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param alarmId Alarm id to fetch.
     * @return A new instance of fragment AlarmDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmDetailFragment newInstance(long alarmId) {
        AlarmDetailFragment fragment = new AlarmDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ALARM_ID, alarmId);
        fragment.setArguments(args);
        return fragment;
    }

    public AlarmDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        if (getArguments() != null) {
            alarmId = getArguments().getLong(ARG_ALARM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_alarm_detail, container, false);
        messageTextView = (TextView) layout.findViewById(R.id.alarmMessage);
        triggerTimeTextView = (TextView) layout.findViewById(R.id.timeTrigger);
        return  layout;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), ContentUris.withAppendedId(Uri.parse(DatabaseEntries.AUTHORITY + DatabaseEntries.ALARM_PATH), alarmId),
                DatabaseEntries.sAlarmProjection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_TIMESTAMP)));
        messageTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_MESSAGE)));
        triggerTimeTextView.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
            + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}

package com.wardziniak.reminder.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.activities.AddAlarmFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    private AddAlarmFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hour Parameter 1.
     * @param minute Parameter 2.
     * @return A new instance of fragment TimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimePickerFragment newInstance(int hour, int minute) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HOUR, hour);
        args.putInt(ARG_MINUTE, minute);
        fragment.setArguments(args);
        return fragment;
    }

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null)
            return new TimePickerDialog(getActivity(), mListener, getArguments().getInt(ARG_HOUR), getArguments().getInt(ARG_MINUTE), true);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddAlarmFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddAlarmFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

package com.wardziniak.reminder.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wardziniak.reminder.R;
import com.wardziniak.reminder.activities.AddAlarmFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddAlarmFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";

    private AddAlarmFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Parameter 1.
     * @param month Parameter 2.
     * @param day Parameter 2.
     * @return A new instance of fragment DatePickerFragment.
     */
    public static DatePickerFragment newInstance(int year, int month, int day) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    public DatePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null)
            return new DatePickerDialog(getActivity(), this, getArguments().getInt(ARG_YEAR), getArguments().getInt(ARG_MONTH), getArguments().getInt(ARG_DAY));
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mListener.onDateChange(year, month, day);
    }

}

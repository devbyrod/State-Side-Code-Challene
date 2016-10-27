package com.devbyrod.statesidetechtest.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mandrake on 10/27/16.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public interface DatePickerFragmentListener {
        void onDateSet(Date date);
    }

    public static final String TAG = DatePickerFragment.class.getSimpleName();
    private DatePickerFragmentListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        Date date = c.getTime();

        if(mListener != null) {

            mListener.onDateSet(date);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DatePickerFragmentListener) {
            mListener = (DatePickerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DatePickerFragmentListener");
        }
    }
}

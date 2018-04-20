package com.example.berzerk.amfr;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by berzerk on 31/03/18.
 */

public class SDatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar sc = Calendar.getInstance();
        int year = sc.get(Calendar.YEAR);
        int month = sc.get(Calendar.MONTH);
        int day = sc.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}

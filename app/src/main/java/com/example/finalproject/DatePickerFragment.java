package com.example.finalproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = 1997;//c.get(Calendar.YEAR);
        int month = 3;//c.get(Calendar.MONTH);
        int day = 3;//c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this,year, month, day);
    }
    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);

    }
    public void populateSetDate(int year, int month, int day) {
        TextView dob= (TextView)getActivity(). findViewById(R.id.ageTxt);
        int dyr = 2020;

        int age = dyr  - year;

        dob.setText(String.valueOf(age));
    }
}

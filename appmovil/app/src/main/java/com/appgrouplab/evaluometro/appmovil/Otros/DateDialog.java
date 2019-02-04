package com.appgrouplab.evaluometro.appmovil.Otros;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


import java.util.Calendar;
import java.util.Date;

public class DateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR,-24);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
                getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(), year,month,day);


    }



}

package com.appgrouplab.evaluometro.appmovil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.Toast;


import com.appgrouplab.evaluometro.appmovil.Otros.RegistroFragment;


public class registro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
        {
            RegistroFragment fragment = new RegistroFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content,fragment,"RegistroFragment")
                    .commit();


        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {


        RegistroFragment fragment = (RegistroFragment) getSupportFragmentManager().findFragmentByTag("RegistroFragment");
        if(fragment!=null){
            fragment.setFecha(year,monthOfYear+1,dayOfMonth);
        }
    }
    }

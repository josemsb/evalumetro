package com.appgrouplab.evaluometro.appmovil;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class visorPdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdf);

        String strSubtitulo;
        strSubtitulo = "Visor Plan de Gobierno";
        getSupportActionBar().setSubtitle(strSubtitulo);


    }

}

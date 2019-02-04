package com.appgrouplab.evaluometro.appmovil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class acerca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        String strSubtitulo;
        strSubtitulo = "Acerca de ";
        getSupportActionBar().setSubtitle(strSubtitulo);
    }
}

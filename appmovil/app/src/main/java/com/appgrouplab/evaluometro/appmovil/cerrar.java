package com.appgrouplab.evaluometro.appmovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;

public class cerrar extends AppCompatActivity {
    Button btnCerrar;
    preferencias pPreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar);

        btnCerrar = (Button) findViewById(R.id.btnCerrar);
        pPreferencia = new preferencias(this) ;

        String strSubtitulo;
        strSubtitulo = "Cerrar Sesión";
        getSupportActionBar().setSubtitle(strSubtitulo);



        btnCerrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                pPreferencia.limpiarPreferencias();
                Intent ventana = new Intent(getApplicationContext(),visor.class);
                ventana.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ventana);

            }
        });

    }
}

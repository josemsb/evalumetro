package com.appgrouplab.evaluometro.appmovil;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Datos.DatosCandidato;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.Candidato.*;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class detalleVisor extends AppCompatActivity {
    ImageButton imgEducacion,imgSentencia,imgPlan,imgPartidoV;
    ImageView imgFondo, imgCandidato, imgExperiencia,imgPartido;
    TextView txtCandidato,txtPartido,txtSigla,txtTipoCandidatura;
    preferencias pPreferencia = new preferencias(this) ;

    Button btnPlan;
    String strSubtitulo, strTipoCandidatura;
    Integer idCandidato;
    private static CustomProgressBar progressBar;

    private AdView mAdView;

    @Override
    protected void onPause() {
        super.onPause();
        progressBar.getDialog().dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar = new CustomProgressBar();
        progressBar.show(this,"Cargando...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_visor);

        strSubtitulo = "Detalle de Candidato";
        getSupportActionBar().setSubtitle(strSubtitulo);

        imgFondo = findViewById(R.id.imgFondoVisor);
        imgCandidato = findViewById(R.id.imgCandidatoVisor);
        txtCandidato = findViewById(R.id.txtCandidatoVisor);
        imgPartido = findViewById(R.id.imgPartidoVisor);
        txtPartido = findViewById(R.id.txtPartidoVisor);
        txtSigla = findViewById(R.id.txtAbreviaturaVisor);
        txtTipoCandidatura = findViewById(R.id.txtTipoCandidaturaVisor);

        imgEducacion = findViewById(R.id.imgdv_educacion);
        imgSentencia= findViewById(R.id.imgdv_sentencias);
        imgExperiencia= findViewById(R.id.imgdv_trabajos);
        imgPlan =  findViewById(R.id.imgdv_plan);
        imgPartidoV = findViewById(R.id.imgdv_partidos);

        MobileAds.initialize(this, "ca-app-pub-3918734194731544~1658461566");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            strTipoCandidatura=extras.getString("TipoCandidato");

            switch (strTipoCandidatura) {
                case "R":
                    txtTipoCandidatura.setText("Candidato Región " + pPreferencia.obtenersRegion());
                    break;
                case "P":
                    txtTipoCandidatura.setText("Candidato Provincia de " + pPreferencia.obtenersProvincia());
                    break;
                case "D":
                    txtTipoCandidatura.setText("Candidato Distrito de " + pPreferencia.obtenersDistrito());
                    break;

            }


            idCandidato =extras.getInt("idCandidato");
            txtCandidato.setText(extras.getString("nombreCandidato"));
            txtPartido.setText(extras.getString("nombrePartido"));
            txtSigla.setText(extras.getString("sigla"));

            Picasso.with(this).load(extras.getString("fotoFondo"))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.fondo)
                    .fit()
                    .into(imgFondo);

            Picasso.with(this).load(extras.getString("fotoPartido"))
                    .into(imgPartido);

            Picasso.with(this).load(extras.getString("fotoCandidato"))
                    .into(imgCandidato);
        }


        imgEducacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent ventana = new Intent(getApplicationContext(),candidato_educacion.class);
                ventana.putExtra("idCandidato",idCandidato);
                startActivity(ventana);
            }
        });

        imgSentencia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent ventana = new Intent(getApplicationContext(),candidato_sentencia.class);
                ventana.putExtra("idCandidato",idCandidato);
                startActivity(ventana);
            }
        });

        imgExperiencia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent ventana = new Intent(getApplicationContext(),candidato_trabajo.class);
                ventana.putExtra("idCandidato",idCandidato);
                startActivity(ventana);
            }
        });

        imgPlan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                progressBar.mostrar();
                new cargarDatosCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=C&i=" + idCandidato +"&p=");
            }
        });

        imgPartidoV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent ventana = new Intent(getApplicationContext(),candidato_partido.class);
                ventana.putExtra("idCandidato",idCandidato);
                startActivity(ventana);
            }
        });


    }


    public Boolean isOnlineNet()
    {
        try{
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 8.8.8.8");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return  reachable;

        }catch (Exception e){}
        return false;
    }




    public class cargarDatosCandidatos extends AsyncTask<String, Void, String> {
        acceso a = new acceso();

        @Override
        protected String doInBackground(String... urls){
            try{

                return a.downloadUrl(urls[0]);

            }catch (IOException e){
                return "Unable to retrieve web page. URL may be invalid";
            }
        }
        @Override
        protected  void onPostExecute(String result){

            JSONArray ja = null;
            DatosCandidato datoscandidato = new DatosCandidato();
            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    datoscandidato = new DatosCandidato();
                    datoscandidato.setPlan(jo.getString("p"));

                }


            }catch (Exception e){e.printStackTrace();}

            progressBar.getDialog().hide();

            if (datoscandidato.getPlan().trim().length()>0) {
                Intent ventana = new Intent(Intent.ACTION_VIEW);
                ventana.setDataAndType(Uri.parse(datoscandidato.getPlan()), "application/pdf");
                startActivity(ventana);
            }
            else
            {
                Toast mensaje = Toast.makeText(getApplicationContext(),"Ummm... No se ha encontrado Plan de Gobierno.",Toast.LENGTH_SHORT);
                mensaje.setGravity(Gravity.CENTER,0,0);
                mensaje.show();
            }
        }

    }
}

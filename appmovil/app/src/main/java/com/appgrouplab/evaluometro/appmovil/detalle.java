package com.appgrouplab.evaluometro.appmovil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Candidato.candidato_educacion;
import com.appgrouplab.evaluometro.appmovil.Candidato.candidato_partido;
import com.appgrouplab.evaluometro.appmovil.Candidato.candidato_sentencia;
import com.appgrouplab.evaluometro.appmovil.Candidato.candidato_trabajo;
import com.appgrouplab.evaluometro.appmovil.Datos.DatosCandidato;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class detalle extends AppCompatActivity {
    ImageButton imgEducacion,imgSentencia,imgPlan,imgdPartido;
    ImageView imgFondo, imgCandidato,imgExperiencia,imgPartido;
    TextView txtCandidato,txtPartido,txtSigla,txtTipoCandidatura;
    preferencias pPreferencia = new preferencias(this) ;
    ImageButton imgVotar;
    String strSubtitulo, strTipoCandidatura,strPostulacion;
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
        setContentView(R.layout.activity_detalle);




        strSubtitulo = "Detalle de Candidato";
        getSupportActionBar().setSubtitle(strSubtitulo);

        imgFondo = findViewById(R.id.imgFondoD);
        imgCandidato = findViewById(R.id.imgCandidatoD);
        imgVotar = findViewById(R.id.imgVotar);
        imgPartido= findViewById(R.id.imgPartidoD);
        txtCandidato = findViewById(R.id.txtCandidatoD);
        txtPartido = findViewById(R.id.txtPartidoD);
        txtSigla = findViewById(R.id.txtAbreviatura);
        txtTipoCandidatura = findViewById(R.id.txtTipoCandidatura);


        imgEducacion = findViewById(R.id.imgd_educacion);
        imgSentencia= findViewById(R.id.imgd_sentencias);
        imgExperiencia= findViewById(R.id.imgd_trabajos);
        imgPlan =  findViewById(R.id.imgd_plan);
        imgdPartido = findViewById(R.id.imgd_partidos);

        MobileAds.initialize(this, "ca-app-pub-3918734194731544~1658461566");

        mAdView = findViewById(R.id.adViewd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle extras = getIntent().getExtras();
        if(extras != null){

            strTipoCandidatura=extras.getString("TipoCandidato");
            strPostulacion=extras.getString("Postulacion");

            switch (strTipoCandidatura) {
                case "R":
                    if(strPostulacion.equals("V"))
                        txtTipoCandidatura.setText("Candidato Región " + pPreferencia.obtenersRegion());
                    else
                        txtTipoCandidatura.setText("Candidato Región " + strPostulacion);
                    break;
                case "P":
                    if(strPostulacion.equals("V"))
                        txtTipoCandidatura.setText("Candidato Provincia de " + pPreferencia.obtenersProvincia());
                    else
                        txtTipoCandidatura.setText("Candidato Provincia de " + strPostulacion);
                    break;
                case "D":
                    if(strPostulacion.equals("V"))
                        txtTipoCandidatura.setText("Candidato Distrito de " + pPreferencia.obtenersDistrito());
                    else
                        txtTipoCandidatura.setText("Candidato Distrito de " + strPostulacion);
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

        String credencial = pPreferencia.obtenerCorreo();

        if (!credencial.isEmpty()){ verificarVoto();}


        imgVotar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                String credencial = pPreferencia.obtenerCorreo();
                if (!credencial.isEmpty()) {
                    if (strPostulacion.equals("V")) {
                        Calendar diaActual = Calendar.getInstance();
                        Integer anio = diaActual.get(Calendar.YEAR);
                        Integer mes = diaActual.get(Calendar.MONTH);
                        Integer dia = diaActual.get(Calendar.DAY_OF_MONTH);
                        String fecha = anio.toString() + "-" + mes.toString() + "-" + dia.toString();

                        progressBar.mostrar();

                        new guardarVoto().execute("http://evaluometro.pe/services_movil/voto.php?cs=RegistrarVoto&co=" + pPreferencia.obtenerCorreo() + "&f=" + fecha + "&c=" + idCandidato + "&t=" + strTipoCandidatura + "&p=" + pPreferencia.obtenerPeriodo());


                       }
                    else{
                        Toast mensaje = Toast.makeText(getApplicationContext(),"Ummm... En modo visor no puedes marcar tu candidato preferido.",Toast.LENGTH_LONG);
                        mensaje.setGravity(Gravity.CENTER,0,0);
                        mensaje.show();

                    }
                }
                else {
                    eleccion("Regístrate o inicia sesión para votar.\nEs gratuito anímate!");
                }
            }
        });


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

        imgdPartido.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent ventana = new Intent(getApplicationContext(),candidato_partido.class);
                ventana.putExtra("idCandidato",idCandidato);
                startActivity(ventana);
            }
        });


    }

    private void verificarVoto()
    {
        new verificarVoto().execute("http://evaluometro.pe/services_movil/voto.php?cs=VerificarVoto&co=" + pPreferencia.obtenerCorreo()  + "&f=&c=" + idCandidato +  "&t=&p=" + pPreferencia.obtenerPeriodo());
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


    public class guardarVoto extends AsyncTask<String, Void, String> {
        acceso a = new acceso();

        @Override
        protected String doInBackground(String... urls) {
            try {

                return a.downloadUrl(urls[0]);

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {

          if(result.trim().substring(0,2).equals("SR"))
          {
              imgVotar.setImageResource(R.drawable.check_circle_verde);
              imgVotar.setEnabled(false);
              }
           else{ imgVotar.setEnabled(true); }

            progressBar.getDialog().hide();

            Toast mensaje = Toast.makeText(getApplicationContext(),"Gracias por marcar tu candidato preferente!" + '\n' + "No olvides deslizar el listado de candidiatos para actualizar resultados.",Toast.LENGTH_SHORT);
            mensaje.setGravity(Gravity.CENTER,0,0);
            mensaje.show();

        }


    }

    public class verificarVoto extends AsyncTask<String, Void, String> {
        acceso a = new acceso();

        @Override
        protected String doInBackground(String... urls) {
            try {

                return a.downloadUrl(urls[0]);

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.trim().substring(0,2).equals("VA"))
            {
                imgVotar.setImageResource(R.drawable.check_circle_verde);
                imgVotar.setEnabled(false);
            }
            else{ imgVotar.setEnabled(true); }

        }


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

    public void eleccion(String cadena){
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        alertbox.setMessage(cadena);
        //elegimos un positivo SI y creamos un Listener
        alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton Si
            public void onClick(DialogInterface arg0, int arg1) {
                Intent ventana = new Intent(getApplicationContext(),inicio.class);
                //ventana.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ventana);
            }
        });

        //elegimos un positivo NO y creamos un Listener
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton No
            public void onClick(DialogInterface arg0, int arg1) {
                //mensaje("Pulsado el botón NO");
            }
        });

        //mostramos el alertbox
        alertbox.show();
    }

}

package com.appgrouplab.evaluometro.appmovil.Candidato;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionNoUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_SentenciaPenal;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_SentenciaNoObligatoria;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.SentenciaNoObligatoria;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.SentenciaPenal;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class candidato_sentencia extends AppCompatActivity {

    String strSubtitulo;
    Integer _idCandidato;

    listadoCandidatos_SentenciaPenal mAdapterPenal;
    listadoCandidatos_SentenciaNoObligatoria mAdapterNoObligatora;
    ArrayList<SentenciaPenal> sentenciaPenales = new ArrayList<SentenciaPenal>();
    ArrayList<SentenciaNoObligatoria> sentenciaNoObligatorias = new ArrayList<SentenciaNoObligatoria>();
    RecyclerView recyclerViewPenal;
    RecyclerView recyclerViewNoObligatoria;

    private static CustomProgressBar progressBarEdu;
    preferencias pPreferencia = new preferencias(this) ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_sentencia);

        strSubtitulo = "Datos sobre Denuncias y Sentencias";
        getSupportActionBar().setSubtitle(strSubtitulo);

        recyclerViewPenal = (RecyclerView) findViewById(R.id.recyclePenales);
        recyclerViewPenal.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPenal.setLayoutManager(layoutManager);

        recyclerViewNoObligatoria = (RecyclerView) findViewById(R.id.recyclerNoObligatorias);
        recyclerViewNoObligatoria.setHasFixedSize(true);

        final LinearLayoutManager layoutManagerNoObligatoria = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNoObligatoria.setLayoutManager(layoutManagerNoObligatoria);

        mAdapterPenal = new listadoCandidatos_SentenciaPenal(this);
        recyclerViewPenal.setAdapter(mAdapterPenal);

        mAdapterNoObligatora = new listadoCandidatos_SentenciaNoObligatoria(this);
        recyclerViewNoObligatoria.setAdapter(mAdapterNoObligatora);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            _idCandidato =extras.getInt("idCandidato");
            new cargar_SentenciaPenal().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=SP&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
            new cargar_SentenciaNoObligatoria().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=SNO&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        progressBarEdu.getDialog().dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBarEdu = new CustomProgressBar();
        progressBarEdu.show(this,"Cargando...");
    }


    public class cargar_SentenciaPenal extends AsyncTask<String, Void, String> {
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
            //Log.d("onPostExecute", result);
            JSONArray ja = null;
            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);

                    SentenciaPenal sentenciaPenal = new SentenciaPenal();

                    sentenciaPenal.setExpedientepenal(jo.getString("s1"));
                    sentenciaPenal.setOrganojudipenal(jo.getString("s2"));
                    sentenciaPenal.setDelitopenal(jo.getString("s3"));
                    sentenciaPenal.setFallopenal(jo.getString("s4"));
                    sentenciaPenal.setParammodalidad(jo.getString("s5"));

                    sentenciaPenal.setOtramodalidad(jo.getString("s6"));
                    sentenciaPenal.setParamcuplefallo(jo.getString("s7"));

                    sentenciaPenales.add(sentenciaPenal);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterPenal.setDataset(sentenciaPenales);
            progressBarEdu.getDialog().hide();

        }

    }

    public class cargar_SentenciaNoObligatoria extends AsyncTask<String, Void, String> {
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
            //Log.d("onPostExecute", result);
            JSONArray ja = null;
            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);

                    SentenciaNoObligatoria sentenciaNoObligatoria = new SentenciaNoObligatoria();

                    sentenciaNoObligatoria.setParammateriasentencia(jo.getString("s1"));
                    sentenciaNoObligatoria.setExpedienteobliga(jo.getString("s2"));
                    sentenciaNoObligatoria.setOrganojuridicionalobliga(jo.getString("s3"));
                    sentenciaNoObligatoria.setFalloobliga(jo.getString("s4"));


                    sentenciaNoObligatorias.add(sentenciaNoObligatoria);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterNoObligatora.setDataset(sentenciaNoObligatorias);
            progressBarEdu.getDialog().hide();

        }

    }
}

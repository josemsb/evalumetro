package com.appgrouplab.evaluometro.appmovil.Candidato;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.*;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionNoUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionTecnica;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class candidato_educacion extends AppCompatActivity {


    String strSubtitulo;
    listadoCandidatos_EducacionUniversitaria mAdapter;
    listadoCandidatos_EducacionNoUniversitaria mAdapterNoUniversitaria;
    listadoCandidatos_EducacionTecnica mAdapterTecnica;

    ArrayList<EducacionUniversitaria> educacionUniversitarias = new ArrayList<EducacionUniversitaria>();
    ArrayList<EducacionNoUniversitaria> educacionNoUniversitarias = new ArrayList<EducacionNoUniversitaria>();
    ArrayList<EducacionTecnica> educacionTecnicas = new ArrayList<EducacionTecnica>();

    Integer _idCandidato;

    private static CustomProgressBar progressBarEdu;
    preferencias pPreferencia = new preferencias(this) ;

    RecyclerView recyclerView;
    RecyclerView recyclerViewNoUniversitaria;
    RecyclerView recyclerViewTecnica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_educacion);

        strSubtitulo = "Datos de Educación";
        getSupportActionBar().setSubtitle(strSubtitulo);


        recyclerView = (RecyclerView) findViewById(R.id.recycleEducacion);
        recyclerView.setHasFixedSize(true);

        recyclerViewNoUniversitaria = (RecyclerView) findViewById(R.id.recycleEducacionNoUniversitaria);
        recyclerViewNoUniversitaria.setHasFixedSize(true);

        recyclerViewTecnica = (RecyclerView) findViewById(R.id.recycleEducacionTecnica);
        recyclerViewTecnica.setHasFixedSize(true);



        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        final LinearLayoutManager layoutManagerNoUniversitaria = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNoUniversitaria.setLayoutManager(layoutManagerNoUniversitaria);

        final LinearLayoutManager layoutManagerTecnica = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTecnica.setLayoutManager(layoutManagerTecnica);



        mAdapter = new listadoCandidatos_EducacionUniversitaria(this);
        recyclerView.setAdapter(mAdapter);

        mAdapterNoUniversitaria = new listadoCandidatos_EducacionNoUniversitaria(this);
        recyclerViewNoUniversitaria.setAdapter(mAdapterNoUniversitaria);

        mAdapterTecnica = new listadoCandidatos_EducacionTecnica(this);
        recyclerViewTecnica.setAdapter(mAdapterTecnica);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            _idCandidato =extras.getInt("idCandidato");
            new cargar_EducacionUniversitaria().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=EU&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
            new cargar_EducacionNoUniversitaria().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=ENU&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
            new cargar_EducacionTecnica().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=ET&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
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


    public class cargar_EducacionUniversitaria extends AsyncTask<String, Void, String> {
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

                    EducacionUniversitaria educacionUniversitaria = new EducacionUniversitaria();

                    educacionUniversitaria.setUniversidad(jo.getString("e2"));
                    educacionUniversitaria.setCarrerauni(jo.getString("e3"));
                    educacionUniversitaria.setConcluidoeduuni(jo.getString("e4"));
                    educacionUniversitaria.setEgresadoeduuni(jo.getString("e5"));
                    educacionUniversitaria.setBachillereduuni(jo.getString("e6"));

                    educacionUniversitaria.setAniobachiller(Integer.parseInt(jo.getString("e7")));
                    educacionUniversitaria.setTitulouni(jo.getString("e8"));
                    educacionUniversitaria.setAniotitulo(Integer.parseInt(jo.getString("e9")));

                    educacionUniversitarias.add(educacionUniversitaria);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapter.setDataset(educacionUniversitarias);
            progressBarEdu.getDialog().hide();

        }

    }

    public class cargar_EducacionNoUniversitaria extends AsyncTask<String, Void, String> {
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

                    EducacionNoUniversitaria educacionNoUniversitaria = new EducacionNoUniversitaria();

                    educacionNoUniversitaria.setCentroestudiosnouni(jo.getString("nu1"));
                    educacionNoUniversitaria.setCarreranouni(jo.getString("nu2"));
                    educacionNoUniversitaria.setConcluidonouni(jo.getString("nu3"));

                    educacionNoUniversitarias.add(educacionNoUniversitaria);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterNoUniversitaria.setDataset(educacionNoUniversitarias);
            progressBarEdu.getDialog().hide();

        }

    }

    public class cargar_EducacionTecnica extends AsyncTask<String, Void, String> {
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

                    EducacionTecnica educacionTecnica = new EducacionTecnica();

                    educacionTecnica.setCenestudiotecnico(jo.getString("et1"));
                    educacionTecnica.setCarreratecnica(jo.getString("et2"));
                    educacionTecnica.setConcluidoedutecnico(jo.getString("et3"));

                    educacionTecnicas.add(educacionTecnica);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterTecnica.setDataset(educacionTecnicas);
            progressBarEdu.getDialog().hide();

        }

    }


}

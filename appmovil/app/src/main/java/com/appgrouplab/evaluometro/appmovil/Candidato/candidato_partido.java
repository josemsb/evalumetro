package com.appgrouplab.evaluometro.appmovil.Candidato;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;

import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_CargoEleccionPopular;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_CargoPartidario;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionNoUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionTecnica;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_EducacionUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_Renuncias;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.CargoEleccionPopular;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.CargoPartidario;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionNoUniversitaria;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionTecnica;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionUniversitaria;


import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.Renuncias;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class candidato_partido extends AppCompatActivity {

    String strSubtitulo;

    listadoCandidatos_CargoEleccionPopular mAdapterCargoPopular;
    listadoCandidatos_CargoPartidario mAdapterCargoPartidario;
    listadoCandidatos_Renuncias mAdapterRenuncias;

    ArrayList<CargoEleccionPopular> cargoEleccionPopulares = new ArrayList<CargoEleccionPopular>();
    ArrayList<CargoPartidario> cargoPartidarios = new ArrayList<CargoPartidario>();
    ArrayList<Renuncias> renunciass = new ArrayList<Renuncias>();

    Integer _idCandidato;

    private static CustomProgressBar progressBarEdu;
    preferencias pPreferencia = new preferencias(this) ;

    RecyclerView recyclerViewEleccionesPopulares;
    RecyclerView recyclerViewCargoPartidario;
    RecyclerView recyclerViewRenuncias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_partido);

        strSubtitulo = "Datos Partidarios";
        getSupportActionBar().setSubtitle(strSubtitulo);


        recyclerViewEleccionesPopulares = (RecyclerView) findViewById(R.id.recycleEleccionPopular);
        recyclerViewEleccionesPopulares.setHasFixedSize(true);

        recyclerViewCargoPartidario = (RecyclerView) findViewById(R.id.recycleCargoPartidario);
        recyclerViewCargoPartidario.setHasFixedSize(true);

        recyclerViewRenuncias = (RecyclerView) findViewById(R.id.recycleRenuncias);
        recyclerViewRenuncias.setHasFixedSize(true);



        final LinearLayoutManager layoutManagerEleccionesPopulares = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewEleccionesPopulares.setLayoutManager(layoutManagerEleccionesPopulares);

        final LinearLayoutManager layoutManagerCargoPartidario = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCargoPartidario.setLayoutManager(layoutManagerCargoPartidario);

        final LinearLayoutManager layoutManagerRenuncias= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRenuncias.setLayoutManager(layoutManagerRenuncias);



        mAdapterCargoPopular = new listadoCandidatos_CargoEleccionPopular(this);
        recyclerViewEleccionesPopulares.setAdapter(mAdapterCargoPopular);

        mAdapterCargoPartidario = new listadoCandidatos_CargoPartidario(this);
        recyclerViewCargoPartidario.setAdapter(mAdapterCargoPartidario);

        mAdapterRenuncias = new listadoCandidatos_Renuncias(this);
        recyclerViewRenuncias.setAdapter(mAdapterRenuncias);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            _idCandidato =extras.getInt("idCandidato");
            new cargar_CargoPopular().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=CEP&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
            new cargar_Partidario().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=CP&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
            new cargar_Renuncia().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=RE&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());
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


    public class cargar_CargoPopular extends AsyncTask<String, Void, String> {
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

                    CargoEleccionPopular cargoEleccionPopular = new CargoEleccionPopular();

                    cargoEleccionPopular.setOrgpolcargoelec(jo.getString("c1"));
                    cargoEleccionPopular.setCargoeleccion(jo.getString("c2"));
                    cargoEleccionPopular.setAniocargoelecdesde(jo.getString("c3"));
                    cargoEleccionPopular.setAniocargoelechasta(jo.getString("c4"));

                    cargoEleccionPopulares.add(cargoEleccionPopular);
                }

            }catch (Exception e){e.printStackTrace();}

            mAdapterCargoPopular.setDataset(cargoEleccionPopulares);
            progressBarEdu.getDialog().hide();

        }

    }

    public class cargar_Partidario extends AsyncTask<String, Void, String> {
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

                    CargoPartidario cargoPartidario = new CargoPartidario();

                    cargoPartidario.setOrgpolcargopartidario(jo.getString("c1"));
                    cargoPartidario.setCargopartidario(jo.getString("c2"));
                    cargoPartidario.setAniocargopartidesde(jo.getString("c3"));
                    cargoPartidario.setAniocargopartihasta(jo.getString("c4"));
                    cargoPartidarios.add(cargoPartidario);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterCargoPartidario.setDataset(cargoPartidarios);
            progressBarEdu.getDialog().hide();

        }

    }

    public class cargar_Renuncia extends AsyncTask<String, Void, String> {
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

                    Renuncias renuncias = new Renuncias();

                    renuncias.setOrgpolrenunciaop(jo.getString("c1"));
                    renuncias.setAniorenunciaop(jo.getString("c2"));

                    renunciass.add(renuncias);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterRenuncias.setDataset(renunciass);
            progressBarEdu.getDialog().hide();

        }

    }

}

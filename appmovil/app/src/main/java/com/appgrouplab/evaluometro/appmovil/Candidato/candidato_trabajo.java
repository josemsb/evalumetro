package com.appgrouplab.evaluometro.appmovil.Candidato;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatos_Experiencia;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.ExperienciaLaboral;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class candidato_trabajo extends AppCompatActivity {

    String strSubtitulo;
    Integer _idCandidato;

    listadoCandidatos_Experiencia mAdapterExperiencia;
    ArrayList<ExperienciaLaboral> experienciaLaborales = new ArrayList<ExperienciaLaboral>();
    RecyclerView recyclerExperiencia;

    private static CustomProgressBar progressBarEdu;
    preferencias pPreferencia = new preferencias(this) ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_trabajos);

        strSubtitulo = "Datos sobre Experiencia Laboral";
        getSupportActionBar().setSubtitle(strSubtitulo);

        recyclerExperiencia = (RecyclerView) findViewById(R.id.recycleExperiencia);
        recyclerExperiencia.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerExperiencia.setLayoutManager(layoutManager);

        mAdapterExperiencia = new listadoCandidatos_Experiencia(this);
        recyclerExperiencia.setAdapter(mAdapterExperiencia);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            _idCandidato =extras.getInt("idCandidato");
            new cargar_ExperienciaLaboral().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=EX&i=" + _idCandidato +"&p=" + pPreferencia.obtenerPeriodo().toString());

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

    public class cargar_ExperienciaLaboral extends AsyncTask<String, Void, String> {
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

                    ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral();

                    experienciaLaboral.setCentrotrabajo(jo.getString("s1"));
                    experienciaLaboral.setOcupacionprofesional(jo.getString("s2"));
                    experienciaLaboral.setAniotrabajodesde(jo.getString("s3"));
                    experienciaLaboral.setAniotrabajohasta(jo.getString("s4"));
                    experienciaLaboral.setTrabajopais(jo.getString("s5"));
                    experienciaLaboral.setTrabajodepartamento(jo.getString("s6"));

                    experienciaLaborales.add(experienciaLaboral);

                }


            }catch (Exception e){e.printStackTrace();}

            mAdapterExperiencia.setDataset(experienciaLaborales);
            progressBarEdu.getDialog().hide();

        }

    }



}

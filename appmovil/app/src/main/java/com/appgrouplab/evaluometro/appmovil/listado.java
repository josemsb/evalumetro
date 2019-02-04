package com.appgrouplab.evaluometro.appmovil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatosAdapter;
import com.appgrouplab.evaluometro.appmovil.Datos.Candidato;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class listado extends AppCompatActivity {

    ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
    listadoCandidatosAdapter mAdapter;
    preferencias pPreferencia = new preferencias(this) ;
    public static String strTipoActivo;
    TextView txtCantidadParticipantes;
    private static CustomProgressBar progressBar;
    private Context context;
    RecyclerView recyclerView;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String strSubtitulo;

            mAdapter.limpiar();


                progressBar.mostrar();

                switch (item.getItemId()) {
                    case R.id.navigation_region:

                        strSubtitulo = "Región " + pPreferencia.obtenersRegion();
                        getSupportActionBar().setSubtitle(strSubtitulo);
                        strTipoActivo="R";

                        new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());

                        new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=R&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());

                        return true;
                    case R.id.navigation_provincia:

                        strSubtitulo = "Provincia de " + pPreferencia.obtenersProvincia();
                        getSupportActionBar().setSubtitle(strSubtitulo);
                        strTipoActivo="P";

                        new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerProvincia().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());

                        new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=P&i=" + pPreferencia.obtenerProvincia().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());

                        return true;
                    case R.id.navigation_distrito:


                        strSubtitulo = "Distrito de " + pPreferencia.obtenersDistrito();
                        getSupportActionBar().setSubtitle(strSubtitulo);

                        strTipoActivo = "D";
                        if(pPreferencia.obtenersProvincia().equals(pPreferencia.obtenersDistrito()))
                        {
                            new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" + strTipoActivo + "&i=" + pPreferencia.obtenerDistrito().toString() + "&p=" + pPreferencia.obtenerPeriodo().toString());
                            new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=D&i=" + pPreferencia.obtenerDistrito().toString() + "&p=" + pPreferencia.obtenerPeriodo().toString());

                            Toast mensaje = Toast.makeText(getApplicationContext(),"Votas en una Provincia, no podras acceder a la pestaña Distrito.",Toast.LENGTH_LONG);
                            mensaje.setGravity(Gravity.CENTER,0,0);
                            mensaje.show();
                        }
                        else {

                            new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" + strTipoActivo + "&i=" + pPreferencia.obtenerDistrito().toString() + "&p=" + pPreferencia.obtenerPeriodo().toString());
                            new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=D&i=" + pPreferencia.obtenerDistrito().toString() + "&p=" + pPreferencia.obtenerPeriodo().toString());
                        }
                        return true;
                }

            return false;
        }
    };

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
        setContentView(R.layout.activity_listado);


        txtCantidadParticipantes = findViewById(R.id.txtCantidadParticipantes);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView = (RecyclerView) findViewById(R.id.recycleCandidatos);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        mAdapter = new listadoCandidatosAdapter(this);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.getDialog().dismiss();
                Intent ventana = new Intent(getApplicationContext(),detalle.class);
                    ventana.putExtra("idCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getId());
                    ventana.putExtra("nombreCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                    ventana.putExtra("nombrePartido",candidatos.get(recyclerView.getChildAdapterPosition(view)).getPartido());
                    ventana.putExtra("fotoFondo",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoFondo());
                    ventana.putExtra("fotoPartido",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoPartido());
                    ventana.putExtra("fotoCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoCandidato());
                    ventana.putExtra("sigla",candidatos.get(recyclerView.getChildAdapterPosition(view)).getAbreviatura());
                    ventana.putExtra("TipoCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getTipoCandidatura());
                    ventana.putExtra("Postulacion","V");
                startActivity(ventana);
         }
        });


        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState==2){
                   // Log.d("addOnScrollListener", "SI");
                   LinearLayoutManager layoutmanager = (LinearLayoutManager) recyclerView.getLayoutManager();
                   if(layoutManager.findFirstCompletelyVisibleItemPosition()==0)
                   {
                       //Log.d("estado", ""+ layoutManager.findFirstCompletelyVisibleItemPosition());

                       progressBar.mostrar();

                           mAdapter.limpiar();

                           if (strTipoActivo.equals("R")) {
                               new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo +"&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                               new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                           }
                       if (strTipoActivo.equals("P")) {
                           new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo +"&i=" + pPreferencia.obtenerProvincia().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                           new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerProvincia().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                       }
                       if (strTipoActivo.equals("D")) {
                           new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo +"&i=" + pPreferencia.obtenerDistrito().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                           new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerDistrito().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
                       }


                   }

                }

            }

        });




        String strSubtitulo;
        strSubtitulo = "Región " + pPreferencia.obtenersRegion();
        getSupportActionBar().setSubtitle(strSubtitulo);

        strTipoActivo="R";

            new listado.cantidadParticipantes().execute("http://evaluometro.pe/services_movil/candidatos.php?c=CantidadVotosxCandidato&t=" +  strTipoActivo +"&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());
            new listado.cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" +  strTipoActivo + "&i=" + pPreferencia.obtenerRegion().toString() +"&p="+ pPreferencia.obtenerPeriodo().toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.navigation_acerca)
        {
            Intent ventana = new Intent(getApplicationContext(),acerca.class);
            startActivity(ventana);
        }
        if(id == R.id.navigation_configuracion)
        {
            Intent ventana = new Intent(getApplicationContext(),cerrar.class);
            startActivity(ventana);
        }
        if(id == R.id.navigation_visor)
        {
            Intent ventana = new Intent(getApplicationContext(),visorLogueado.class);
            startActivity(ventana);
        }

        return super.onOptionsItemSelected(item);
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

    public class cargarCandidatos extends AsyncTask<String, Void, String> {
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
            Log.d("onPostExecute", result);
            JSONArray ja = null;
            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    Candidato candidato = new Candidato();
                    candidato.setId(Integer.parseInt(jo.getString("ic")));
                    candidato.setNombre(jo.getString("nc"));
                    candidato.setPartido(jo.getString("np"));
                    candidato.setAbreviatura(jo.getString("s"));
                    candidato.setVotos(Integer.parseInt(jo.getString("v")));
                    candidato.setPorcentaje(Integer.parseInt(jo.getString("p")));

                    candidato.setFotoPartido(Integer.parseInt(jo.getString("ip")));
                    candidato.setFotoCandidato(Integer.parseInt(jo.getString("ic")));
                    candidato.setFotoFondo(Integer.parseInt(jo.getString("ic")));

                    candidato.setTipoCandidatura(strTipoActivo);
                    candidatos.add(candidato);

                }


            }catch (Exception e){e.printStackTrace();}


            mAdapter.setDataset(candidatos);

            progressBar.getDialog().hide();



        }

    }

    public class cantidadParticipantes extends AsyncTask<String, Void, String> {
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
            try{
                ja = new JSONArray(result);
                Integer intCanditad=0;
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);

                    intCanditad=jo.getInt("c");


                }

                if (intCanditad==0) { txtCantidadParticipantes.setText("Se el primero en dar tu prefencia!"); }
                else{txtCantidadParticipantes.setText( intCanditad.toString() +  " participante(s)");}


            }catch (Exception e){e.printStackTrace();}

        }

    }


}

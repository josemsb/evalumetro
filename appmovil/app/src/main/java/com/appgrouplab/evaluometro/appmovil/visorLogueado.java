package com.appgrouplab.evaluometro.appmovil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoCandidatosAdapter;
import com.appgrouplab.evaluometro.appmovil.Adapters.listarCandidatosAdapterVisor;
import com.appgrouplab.evaluometro.appmovil.Datos.Candidato;
import com.appgrouplab.evaluometro.appmovil.Datos.Distrito;
import com.appgrouplab.evaluometro.appmovil.Datos.Provincia;
import com.appgrouplab.evaluometro.appmovil.Datos.Region;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.Adapters.listadoUbicacionesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class visorLogueado extends AppCompatActivity {

    ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
    listarCandidatosAdapterVisor mAdapterVisor;
    private Context context;
    RecyclerView recyclerView;
    public static String strTipoActivoVisor;
    private static CustomProgressBar progressBar;

    ArrayList<Region> regiones = new ArrayList<Region>();
    ArrayList<Provincia> provincias = new ArrayList<Provincia>();
    ArrayList<Distrito> distritos = new ArrayList<Distrito>();
    Spinner sRegion,sProvincia,sDistrito,sEleccion ;
    TextView lProvincia,lDistrito;
    preferencias pPreferencia = new preferencias(this) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor);

            sEleccion = (Spinner) findViewById(R.id.cboEleccion);
            sRegion = (Spinner) findViewById(R.id.cboRegionVisor);
            sProvincia = (Spinner) findViewById(R.id.cboProvinciaVisor);
            sDistrito = (Spinner) findViewById(R.id.cboDistritoVisor);
            lProvincia = (TextView) findViewById(R.id.lblProvinciaVisor);
            lDistrito = (TextView) findViewById(R.id.lblDistritoVisor);


            recyclerView = (RecyclerView) findViewById(R.id.recycleCandidatosVisor);
            recyclerView.setHasFixedSize(true);

            final LinearLayoutManager layoutManagerVisor = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManagerVisor);


            mAdapterVisor = new listarCandidatosAdapterVisor(this);

            mAdapterVisor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Intent ventana = new Intent(getApplicationContext(),detalle.class);
                        ventana.putExtra("idCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getId());
                        ventana.putExtra("nombreCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                        ventana.putExtra("nombrePartido",candidatos.get(recyclerView.getChildAdapterPosition(view)).getPartido());
                        ventana.putExtra("fotoFondo",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoFondo());
                        ventana.putExtra("fotoPartido",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoPartido());
                        ventana.putExtra("fotoCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getFotoCandidato());
                        ventana.putExtra("sigla",candidatos.get(recyclerView.getChildAdapterPosition(view)).getAbreviatura());
                        ventana.putExtra("TipoCandidato",candidatos.get(recyclerView.getChildAdapterPosition(view)).getTipoCandidatura());

                    switch (candidatos.get(recyclerView.getChildAdapterPosition(view)).getTipoCandidatura()) {
                        case "R":
                            ventana.putExtra("Postulacion",sRegion.getItemAtPosition(sRegion.getSelectedItemPosition()).toString());
                            break;
                        case "P":
                            ventana.putExtra("Postulacion",sProvincia.getItemAtPosition(sProvincia.getSelectedItemPosition()).toString());
                            break;
                        case "D":
                            ventana.putExtra("Postulacion",sDistrito.getItemAtPosition(sDistrito.getSelectedItemPosition()).toString());
                            break;

                    }

                        startActivity(ventana);

                }
            });


            recyclerView.setAdapter(mAdapterVisor);


            sEleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                {

                    ArrayList<String> limpiarArray = new ArrayList<String>();

                    mAdapterVisor.limpiar();
                    regiones.clear();
                    provincias.clear();
                    distritos.clear();

                    sRegion.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));
                    sProvincia.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));
                    sDistrito.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));

                    String strTipo = sEleccion.getItemAtPosition(position).toString();
                    if (strTipo.equals("Regional"))
                    {
                        lProvincia.setVisibility(View.GONE);
                        lDistrito.setVisibility(View.GONE);
                        sProvincia.setVisibility(View.GONE);
                        sDistrito.setVisibility(View.GONE);
                        strTipoActivoVisor="R";
                    }
                    if (strTipo.equals("Municipal Provincial"))
                    {
                        lProvincia.setVisibility(View.VISIBLE);
                        lDistrito.setVisibility(View.GONE);
                        sProvincia.setVisibility(View.VISIBLE);
                        sDistrito.setVisibility(View.GONE);
                        strTipoActivoVisor="P";
                    }
                    if (strTipo.equals("Municipal Distrital"))
                    {
                        lProvincia.setVisibility(View.VISIBLE);
                        lDistrito.setVisibility(View.VISIBLE);
                        sProvincia.setVisibility(View.VISIBLE);
                        sDistrito.setVisibility(View.VISIBLE);
                        strTipoActivoVisor="D";
                    }

                    progressBar.mostrar();
                    new cargarRegion().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getRegionActivo&r=&p=");


                }
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                {

                    //Log.d("sRegion", "entro"+regiones.get(position).getId()) ;

                    ArrayList<String> limpiarArray = new ArrayList<String>();

                    mAdapterVisor.limpiar();
                    provincias.clear();
                    distritos.clear();

                    sProvincia.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));
                    sDistrito.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));

                    String strTipo = sEleccion.getSelectedItem().toString();

                    if (strTipo.equals("Regional")) {
                        if (regiones.get(position).getId()>0) {

                            progressBar.mostrar();
                            Integer idRegionSeleccionado = regiones.get(position).getId();
                            new cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" + strTipoActivoVisor + "&i=" + idRegionSeleccionado.toString() + "&p=1");
                        }
                    }
                    else
                    {

                        if (regiones.get(position).getId()>0) {

                            progressBar.mostrar();
                            Integer idRegionSeleccionado = regiones.get(position).getId();
                            new cargarProvincia().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getProvinciaActivo&r=" + idRegionSeleccionado.toString() + "&p=");
                        }
                    }



                }
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                {

                    //Log.d("sProvincia", "entro") ;

                    ArrayList<String> limpiarArray = new ArrayList<String>();
                    mAdapterVisor.limpiar();
                    distritos.clear();

                    sDistrito.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), limpiarArray));

                    String strTipo = sEleccion.getSelectedItem().toString();

                    if (strTipo.equals("Municipal Provincial")) {
                        if (provincias.get(position).getId()>0) {

                            progressBar.mostrar();
                            Integer idProvinciaSeleccionado = provincias.get(position).getId();
                            new cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" + strTipoActivoVisor + "&i=" + idProvinciaSeleccionado.toString() + "&p=1");
                        }
                    }
                    if (strTipo.equals("Municipal Distrital"))
                    {
                        if (provincias.get(position).getId()>0) {

                            progressBar.mostrar();
                            Integer idProvinciaSeleccionado = provincias.get(position).getId();
                            new cargarDistrito().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getDistritoActivo&r=&p=" + idProvinciaSeleccionado.toString());
                        }
                    }



                }
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sDistrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                {

                    //Log.d("sDistrito", "entro") ;


                    mAdapterVisor.limpiar();

                    String strTipo = sEleccion.getSelectedItem().toString();

                    if (strTipo.equals("Municipal Distrital")) {
                        if (distritos.get(position).getId()>0) {

                            progressBar.mostrar();
                            Integer idDistritoSeleccionado = distritos.get(position).getId();
                            new cargarCandidatos().execute("http://evaluometro.pe/services_movil/candidatos.php?c=getListado&t=" + strTipoActivoVisor + "&i=" + idDistritoSeleccionado.toString() + "&p=1");
                        }
                    }




                }
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



    }

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

    public void eleccion(String cadena){
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        alertbox.setMessage(cadena);
        //elegimos un positivo SI y creamos un Listener
        alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton Si
            public void onClick(DialogInterface arg0, int arg1) {
                //mensaje("Pulsado el botón SI");
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
            //Log.d("onPostExecute", result);
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

                    candidato.setTipoCandidatura(strTipoActivoVisor);
                    candidatos.add(candidato);

                }


            }catch (Exception e){e.printStackTrace();}


            mAdapterVisor.setDataset(candidatos);
            progressBar.getDialog().hide();

        }

    }

    public class cargarRegion extends AsyncTask<String, Void, String> {
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

            ArrayList<String> regionnombres = new ArrayList<String>();
            JSONArray ja = null;
            try{
                ja = new JSONArray(result);

                Region regionInicio = new Region();
                regionInicio.setId(0);
                regionInicio.setNombre("Selecciona Departamento");
                regiones.add(regionInicio);
                regionnombres.add("Selecciona Departamento");

                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    Region region = new Region();
                    region.setId(Integer.parseInt(jo.getString("i")));
                    region.setNombre(jo.getString("n"));
                    regiones.add(region);
                    regionnombres.add(jo.optString("n"));

                }


            }catch (Exception e){e.printStackTrace();}

            sRegion.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), regionnombres));
            progressBar.getDialog().hide();

        }

    }

    public class cargarProvincia extends AsyncTask<String, Void, String> {
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



            ArrayList<String> provincianombres = new ArrayList<String>();
            provincias.clear();

            JSONArray ja = null;

            Provincia proviciaVisor = new Provincia();
            proviciaVisor.setId(0);
            proviciaVisor.setNombre("Selecciona Provincia");
            provincias.add(proviciaVisor);
            provincianombres.add("Selecciona Provincia");

            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    Provincia provicia = new Provincia();
                    provicia.setId(Integer.parseInt(jo.getString("i")));
                    provicia.setNombre(jo.getString("n"));
                    provincias.add(provicia);
                    provincianombres.add(jo.optString("n"));

                }


            }catch (Exception e){e.printStackTrace();}


            sProvincia.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), provincianombres));
            progressBar.getDialog().hide();

        }

    }

    public class cargarDistrito extends AsyncTask<String, Void, String> {
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

            ArrayList<String> distritonombres = new ArrayList<String>();
            distritos.clear();
            JSONArray ja = null;

            Distrito distritoVisor = new Distrito();
            distritoVisor.setId(0);
            distritoVisor.setNombre("Seleeciona Distrito");
            distritos.add(distritoVisor);
            distritonombres.add("Seleeciona Distrito");

            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    Distrito distrito = new Distrito();
                    distrito.setId(Integer.parseInt(jo.getString("i")));
                    distrito.setNombre(jo.getString("n"));
                    distritos.add(distrito);
                    distritonombres.add(jo.optString("n"));

                }


            }catch (Exception e){e.printStackTrace();}

            sDistrito.setAdapter(new listadoUbicacionesAdapter(getApplicationContext(), distritonombres));
            progressBar.getDialog().hide();

        }

    }

}

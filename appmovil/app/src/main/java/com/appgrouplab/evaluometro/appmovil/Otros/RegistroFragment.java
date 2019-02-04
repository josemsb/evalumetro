package com.appgrouplab.evaluometro.appmovil.Otros;

import android.animation.ObjectAnimator;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;


import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Datos.Distrito;
import com.appgrouplab.evaluometro.appmovil.Datos.Provincia;
import com.appgrouplab.evaluometro.appmovil.Datos.Region;
import com.appgrouplab.evaluometro.appmovil.R;
import com.appgrouplab.evaluometro.appmovil.inicio;
import com.appgrouplab.evaluometro.appmovil.listado;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RegistroFragment extends Fragment  {

    ArrayList<Region> regiones = new ArrayList<Region>();
    ArrayList<Provincia> provincias = new ArrayList<Provincia>();
    ArrayList<Distrito> distritos = new ArrayList<Distrito>();
    Spinner sRegion,sProvincia,sDistrito ;
    Button btnRegistrar;
    ImageButton btnFecha;
    EditText txtCorreo, txtClave;
    TextView txtFecha;
    RadioButton rdbFemenino, rdbMasculino;
    Integer year,month, day;
    private static CustomProgressBar progressBar;

   public RegistroFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.activity_registro, container, false   );


        sRegion = (Spinner) v.findViewById(R.id.cboRegion);
        sProvincia = (Spinner) v.findViewById(R.id.cboProvincia);
        sDistrito = (Spinner) v.findViewById(R.id.cboDistrito);
        btnRegistrar = (Button) v.findViewById(R.id.btnRegistrar);
        btnFecha = (ImageButton) v.findViewById(R.id.btnFecha);
        txtCorreo = (EditText) v.findViewById(R.id.txtCorreo);
        txtClave = (EditText) v.findViewById(R.id.txtClave);
        txtFecha = (TextView) v.findViewById(R.id.txtFecha);
        rdbFemenino = (RadioButton) v.findViewById(R.id.rdbFemenino);
        rdbMasculino = (RadioButton) v.findViewById(R.id.rdbMasculino);


        progressBar = new CustomProgressBar();
        progressBar.show(getActivity(),"Cargando...");

        progressBar.mostrar();


        new RegistroFragment.cargarRegion().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getRegionActivo&r=&p=");


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Validar campos correctamente llenos

                if(validarCampos().equals("Correcto")) {

                    Integer idRegionSeleccionado = regiones.get(sRegion.getSelectedItemPosition()).getId();
                    Integer idProvinciaSeleccionado = provincias.get(sProvincia.getSelectedItemPosition()).getId();
                    Integer idDistritoSeleccionado = distritos.get(sDistrito.getSelectedItemPosition()).getId();
                    String sSexo = "";
                    if (rdbFemenino.isChecked()) {
                        sSexo = "F";
                    }
                    if (rdbMasculino.isChecked()) {
                        sSexo = "M";
                    }
                        progressBar.mostrar();
                        new RegistroFragment.guardarUsuario().execute("http://evaluometro.pe/services_movil/usuario.php?c=registrarUsuario&e=" + txtFecha.getText().toString() + "&r=" + idRegionSeleccionado.toString() + "&p=" + idProvinciaSeleccionado.toString() + "&d=" + idDistritoSeleccionado.toString() + "&cor=" + txtCorreo.getText().toString() + "&con=" + txtClave.getText().toString() + "&s=" + sSexo);

                    }
                else
                {
                    Toast mensaje = Toast.makeText(getActivity(),validarCampos(),Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER,0,0);
                    mensaje.show();
                }
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                FragmentManager fragmentManager = getFragmentManager();

                new DateDialog().show(fragmentManager,"DatePickerFragmenet");

            }
        });

        sRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                {

                        progressBar.mostrar();
                        Integer idRegionSeleccionado = regiones.get(position).getId();
                        new RegistroFragment.cargarProvincia().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getProvinciaActivo&r=" + idRegionSeleccionado.toString() + "&p=");

                }
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
        });

        sProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {

                    progressBar.mostrar();
                    Integer idProvinciaSeleccionado = provincias.get(position).getId();
                    new RegistroFragment.cargarDistrito().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getDistritoActivo&r=&p=" + idProvinciaSeleccionado.toString());


            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }


    public void setFecha(Integer pYear, Integer pMonthOfYear, Integer pDayOfMonth)
    {
        year = pYear;
        month = pMonthOfYear;
        day = pDayOfMonth;

        txtFecha.setText(pYear.toString() + "-" + pMonthOfYear.toString() + "-" + pDayOfMonth.toString());
    }


    private String validarCampos()
    {
        //Validar Correo
        String strMensaje="";
        String strMensajeError="";
        Boolean bolError=false;


        //Validar Contraseña
        if(txtFecha.getText().toString().trim().length()>7)
        {
            Date fechaActual = new Date();
            String fechaSeleccionada = year.toString() + "-" + month.toString() + "-" + day.toString();
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(fechaSeleccionada);

                long diferencia = fechaActual.getTime() - date.getTime();
                long segundos = 1000;
                long minutos = segundos * 60;
                long horas = minutos*60;
                long dias = horas*24;
                long anio = dias*365;

                long aniosTranscurridos = diferencia / anio;

                if(aniosTranscurridos>17){strMensaje= "Correcto";}
                else{strMensajeError= "Tienes que ser mayor de edad para registrarte"; bolError=true;}


            }catch (ParseException e){strMensajeError= "Error retornando fecha de nacimiento"; bolError=true;}

        }
        else { strMensajeError= "Fecha Incorrecto o vacia"; bolError=true;}


        if(txtClave.getText().toString().trim().length()>5){ strMensaje= "Correcto";}
        else { strMensajeError= "Contraseña Incorrecta o vacia"; bolError=true;}

        if(txtCorreo.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+") && txtCorreo.getText().toString().length()>0){ strMensaje= "Correcto";}
        else { strMensajeError= "Correo Incorrecto o vacio"; bolError=true;}


        if (regiones.get(sRegion.getSelectedItemPosition()).getId()!=0){ strMensaje= "Correcto";}
        else { strMensajeError= "Seleccione Región"; bolError=true;}

        if (provincias.get(sProvincia.getSelectedItemPosition()).getId()!=0){ strMensaje= "Correcto";}
        else { strMensajeError= "Seleccione Provincia"; bolError=true;}

        if (distritos.get(sDistrito.getSelectedItemPosition()).getId()!=0){ strMensaje= "Correcto";}
        else { strMensajeError= "Seleccione Distrito"; bolError=true;}

        if(bolError)
            return strMensajeError;
        else
            return strMensaje;
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


            sRegion.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regionnombres));
            //Integer idRegionSeleccionado = regiones.get(sRegion.getSelectedItemPosition()).getId();
            //new RegistroFragment.cargarProvincia().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getProvinciaActivo&r=" + idRegionSeleccionado.toString() + "&p=");
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


            sProvincia.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, provincianombres));

            //Integer idProvinciaSeleccionado = provincias.get(sProvincia.getSelectedItemPosition()).getId();
            //new RegistroFragment.cargarDistrito().execute("http://evaluometro.pe/services_movil/localizacion.php?c=getDistritoActivo&r=&p=" + idProvinciaSeleccionado.toString());

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


            sDistrito.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, distritonombres));
            progressBar.getDialog().hide();
        }

    }

    public class guardarUsuario extends AsyncTask<String, Void, String> {
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

            JSONArray ja = null;
            String strMensaje="";
            try{
                ja = new JSONArray(result);
                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    strMensaje=jo.getString("c");
                }

            }catch (Exception e){e.printStackTrace();}

            Log.d("Guardar", strMensaje);
            if(strMensaje.equals("Correo Existente"))
            {
                Toast mensaje = Toast.makeText(getActivity(),"Ummm... correo ya se encuentra registrado, intenta con otro.",Toast.LENGTH_SHORT);
                mensaje.setGravity(Gravity.CENTER,0,0);
                mensaje.show();
            }
            else
            {
                //Integer idRegionSeleccionado = regiones.get(sRegion.getSelectedItemPosition()).getId();
                //Integer idProvinciaSeleccionado = provincias.get(sProvincia.getSelectedItemPosition()).getId();
                //Integer idDistritoSeleccionado = distritos.get(sDistrito.getSelectedItemPosition()).getId();


                //preferencias pPreferencia = new preferencias(getActivity()) ;
                //pPreferencia.guardarPreferencias(txtCorreo.getText().toString(),idRegionSeleccionado,idProvinciaSeleccionado,idDistritoSeleccionado,regiones.get(sRegion.getSelectedItemPosition()).getNombre(),provincias.get(sProvincia.getSelectedItemPosition()).getNombre(),distritos.get(sDistrito.getSelectedItemPosition()).getNombre(),1);


                //Intent ventana = new Intent(getActivity(),listado.class);
                //startActivity(ventana);
                eleccion("Ahora revisa tu correo para activar la cuenta e iniciar.");

            }


        }


    }

    public void eleccion(String cadena){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());
        alertbox.setMessage(cadena);

        alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton Si
            public void onClick(DialogInterface arg0, int arg1) {
                progressBar.getDialog().dismiss();
                Intent ventana = new Intent(getActivity(),inicio.class);
                ventana.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ventana.putExtra("sCorre",txtCorreo.getText().toString());
                ventana.putExtra("sClave",txtClave.getText().toString());
                startActivity(ventana);
            }
        });

        alertbox.show();
    }

}

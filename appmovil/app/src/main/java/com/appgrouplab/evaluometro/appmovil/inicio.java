package com.appgrouplab.evaluometro.appmovil;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appgrouplab.evaluometro.appmovil.AccesoDatos.acceso;
import com.appgrouplab.evaluometro.appmovil.AccesoDatos.preferencias;
import com.appgrouplab.evaluometro.appmovil.Datos.Candidato;
import com.appgrouplab.evaluometro.appmovil.Datos.Distrito;
import com.appgrouplab.evaluometro.appmovil.Datos.Usuario;
import com.appgrouplab.evaluometro.appmovil.Otros.CustomProgressBar;
import com.appgrouplab.evaluometro.appmovil.Otros.RegistroFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class inicio extends AppCompatActivity {

    TextView lblVersion;
    EditText  txtCorreo, txtClave;
    Button btnIniciar, btnRegistrar;
    private static CustomProgressBar progressBar;

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
        setContentView(R.layout.activity_inicio);


        preferencias pPreferencia = new preferencias(this) ;

        String version = BuildConfig.VERSION_NAME;

        lblVersion = findViewById(R.id.lblVersion);
        lblVersion.setText("v " + version);

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtContrasena);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnRegistrar = findViewById(R.id.btnRegistro);


        //pPreferencia.limpiarPreferencias();
        //pPreferencia.guardarPreferencias("josemsb@gmail.com",10,1,1,"Lambayeque","Chiclayo","Chiclayo",1);

        String credencial = pPreferencia.obtenerCorreo();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            txtCorreo.setText(extras.getString("sCorreo"));
            txtClave.setText(extras.getString("sClave"));

        }


        if (credencial.isEmpty()){

        }
        else{

            Intent ventana = new Intent(getApplicationContext(),listado.class);
            ventana.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(ventana);
        }


        btnIniciar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                if(validarCampos().equals("Correcto")) {
                    if(isOnlineNet()){
                        progressBar.mostrar();
                        new validarusuario().execute("http://evaluometro.pe/services_movil/usuario.php?c=validarUsuario&e=&r=&p=&d=&cor=" + txtCorreo.getText().toString() + "&con=" + txtClave.getText().toString() + "&s=");
                    }
                    else
                    {
                        Toast mensaje = Toast.makeText(getApplicationContext(),"No tienes acceso a internet!!!.",Toast.LENGTH_SHORT);
                        mensaje.setGravity(Gravity.CENTER,0,0);
                        mensaje.show();
                    }
                   }
                else
                {
                    Toast mensaje = Toast.makeText(getApplicationContext(),validarCampos(),Toast.LENGTH_SHORT);
                    mensaje.setGravity(Gravity.CENTER,0,0);
                    mensaje.show();
                }

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                Intent ventana = new Intent(getApplicationContext(),registro.class);
                startActivity(ventana);
            }
        });
    }

    private String validarCampos()
    {
        //Validar Correo
        String strMensaje="";
        String strMensajeError="";
        Boolean bolError=false;


        if(txtClave.getText().toString().trim().length()>0){ strMensaje= "Correcto";}
        else { strMensajeError= "Te olvidaste la contraseña"; bolError=true;}

        if(txtCorreo.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+") && txtCorreo.getText().toString().length()>0){ strMensaje= "Correcto";}
        else { strMensajeError= "Parece que no has ingresado correo o es inválido"; bolError=true;}



        if(bolError)
            return strMensajeError;
        else
            return strMensaje;
    }


    public class validarusuario extends AsyncTask<String, Void, String> {
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
            String strMensaje="";

            Usuario usuario= new Usuario();

            try{
                ja = new JSONArray(result);

                if(ja.length()>0)
                {
                    for(int i=0; i<ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        strMensaje = jo.getString("c");
                        usuario.setCorreo(jo.getString("c"));

                        usuario.setIdRegion(Integer.parseInt(jo.getString("ir")));
                        usuario.setIdProvincia(Integer.parseInt(jo.getString("ip")));
                        usuario.setIdDistrito(Integer.parseInt(jo.getString("id")));

                        usuario.setStrRegion(jo.getString("rn"));
                        usuario.setStrProvincia(jo.getString("pn"));
                        usuario.setStrDistrito(jo.getString("dn"));

                    }

                    Log.d("Distrito", usuario.getStrDistrito());

                    preferencias pPreferencia = new preferencias(getApplicationContext()) ;
                    pPreferencia.guardarPreferencias(usuario.getCorreo()  ,usuario.getIdRegion(),usuario.getIdProvincia(),usuario.getIdDistrito(),usuario.getStrRegion(),usuario.getStrProvincia(),usuario.getStrDistrito(),1);

                    progressBar.getDialog().hide();
                    Intent ventana = new Intent(getApplicationContext(),listado.class);
                    ventana.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ventana);
                }
                else
                {
                    progressBar.getDialog().hide();
                    Toast mensaje = Toast.makeText(getApplicationContext(),"Que raro... correo o contraseña invalida, intentalo nuevamente. Tal vez te falto activar la cuenta en tu correo.",Toast.LENGTH_LONG);
                    mensaje.setGravity(Gravity.CENTER,0,0);
                    mensaje.show();

                }


            }catch ( Exception e){e.printStackTrace();}


        }




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

}

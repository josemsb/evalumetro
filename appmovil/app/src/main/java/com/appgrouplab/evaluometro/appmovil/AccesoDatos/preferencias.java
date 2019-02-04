package com.appgrouplab.evaluometro.appmovil.AccesoDatos;

import android.content.Context;
import android.content.SharedPreferences;

public class preferencias {
    private Context mContext;

    public preferencias(Context context){
        this.mContext = context;
    }


    public String obtenerCorreo(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getString("correo", "");
    }

    public Integer obtenerDistrito(){
       SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
       return spreferencias.getInt("distrito", 0);
    }

    public Integer obtenerProvincia(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getInt("provincia",0);
    }

    public Integer obtenerRegion(){
       SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
       return spreferencias.getInt("region",0);
    }

    public String obtenersRegion(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getString("sRegion", "");
    }

    public String obtenersProvincia(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getString("sProvincia", "");
    }

    public String obtenersDistrito(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getString("sDistrito", "");
    }

    public Integer obtenerPeriodo(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return spreferencias.getInt("periodo", 0);
    }


    public void guardarPreferencias(String v_correo, Integer v_region, Integer v_Provincia, Integer v_Distrito, String v_sRegion, String v_sProvincia, String v_sDsitrito, Integer v_periodo){
       SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spreferencias.edit();
        editor.putString("correo",v_correo);
        editor.putInt("distrito",v_Distrito);
        editor.putInt("provincia",v_Provincia);
        editor.putInt("region",v_region);

        editor.putString("sRegion",v_sRegion);
        editor.putString("sProvincia",v_sProvincia);
        editor.putString("sDistrito",v_sDsitrito);

        editor.putInt("periodo",v_periodo);
        editor.apply();
    }

    public void limpiarPreferencias(){
        SharedPreferences spreferencias = mContext.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spreferencias.edit();
        editor.putString("correo",null);

        editor.putString("sRegion",null);
        editor.putString("sProvincia",null);
        editor.putString("sDistrito",null);

        editor.putInt("distrito",0);
        editor.putInt("provincia",0);
        editor.putInt("region",0);


        editor.putInt("periodo",0);

        editor.apply();
    }


}

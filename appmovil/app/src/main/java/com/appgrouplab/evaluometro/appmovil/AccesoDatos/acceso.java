package com.appgrouplab.evaluometro.appmovil.AccesoDatos;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.Reader;


public class acceso {

    public String downloadUrl(String myurl) throws IOException{
        Log.d("URL", myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        int len=0;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000 /* milliseconds */);
            conn.setConnectTimeout(5500);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //Empeza el query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            len = conn.getContentLength();

            //Convertir InputStream en string

            String contenAsString = readIt(is,len);
            Log.d("respuesta",contenAsString);
            return contenAsString;
        } finally {
            if(is !=null){is.close();}
        }
    }

    private String readIt(InputStream stream, int len) throws IOException, UnsupportedOperationException{
        //Log.d("Tamaño", ""+len);
        Reader reader = null;
        reader = new InputStreamReader(stream,"UTF-8");
        //char[] buffer = new char[len];
        //reader.read(buffer);
        //return new String(buffer);


        int nextCharacter; // read() returns an int, we cast it to char later
        String responseData = "";
        while(true){ // Infinite loop, can only be stopped by a "break" statement
            nextCharacter = reader.read(); // read() without parameters returns one character
            if(nextCharacter == -1) // A return value of -1 means that we reached the end
                break;
            responseData += (char) nextCharacter; // The += operator appends the character to the end of the string
        }
        return responseData;
    }



}

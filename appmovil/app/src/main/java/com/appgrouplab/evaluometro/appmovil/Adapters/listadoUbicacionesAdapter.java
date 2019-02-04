package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgesys
 */

//ArrayList<String> regionnombres = new ArrayList<String>();

public class listadoUbicacionesAdapter extends BaseAdapter {
    List<String> values;
    Context context;

    public listadoUbicacionesAdapter(Context context, List<String> values){
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Object getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);

        TextView txv=(TextView)view.findViewById(android.R.id.text1);

        //txv.setBackgroundColor(Color.parseColor("#ffffff"));
        txv.setTextColor(Color.parseColor("#000000")); //Texto color Azul
        txv.setPadding(5,10,5,10);

        txv.setText(values.get(pos));
        return view;
    }

}
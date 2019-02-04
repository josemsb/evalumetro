package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.SentenciaNoObligatoria;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_SentenciaNoObligatoria extends RecyclerView.Adapter<listadoCandidatos_SentenciaNoObligatoria.ViewHolder>  {

    private ArrayList<SentenciaNoObligatoria> mDataSet;
    private Context context;

    public listadoCandidatos_SentenciaNoObligatoria(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<SentenciaNoObligatoria> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_SentenciaNoObligatoria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_sentencianoobligatoria,null,false);

        return new listadoCandidatos_SentenciaNoObligatoria.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_SentenciaNoObligatoria.ViewHolder holder, int position) {

        SentenciaNoObligatoria sentenciaNoObligatoria = mDataSet.get(position);

        holder.txtexpedienteobliga.setText("Exp. " + sentenciaNoObligatoria.getExpedienteobliga());
        holder.txtparammateriasentencia.setText(sentenciaNoObligatoria.getParammateriasentencia());

        holder.txtorganojuridicionalobliga.setText(sentenciaNoObligatoria.getOrganojuridicionalobliga());
        holder.txtfalloobliga.setText(sentenciaNoObligatoria.getFalloobliga());

    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtexpedienteobliga, txtparammateriasentencia, txtorganojuridicionalobliga, txtfalloobliga;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtexpedienteobliga = (TextView) v.findViewById(R.id.txtexpedienteobliga);
            txtparammateriasentencia = (TextView) v.findViewById(R.id.txtparammateriasentencia);
            txtorganojuridicionalobliga = (TextView) v.findViewById(R.id.txtorganojuridicionalobliga);
            txtfalloobliga = (TextView) v.findViewById(R.id.txtfalloobliga);
        }

    }
}

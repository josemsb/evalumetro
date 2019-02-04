package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.SentenciaPenal;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_SentenciaPenal extends RecyclerView.Adapter<listadoCandidatos_SentenciaPenal.ViewHolder> {
    private ArrayList<SentenciaPenal> mDataSet;
    private Context context;

    public listadoCandidatos_SentenciaPenal(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<SentenciaPenal> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_SentenciaPenal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_sentencipenal,null,false);

        return new listadoCandidatos_SentenciaPenal.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_SentenciaPenal.ViewHolder holder, int position) {

        SentenciaPenal sentenciaPenal = mDataSet.get(position);

        holder.txtexpedientepenal.setText("Exp. " + sentenciaPenal.getExpedientepenal());
        holder.txtorganojudipenal.setText(sentenciaPenal.getOrganojudipenal());

        holder.txtdelitopenal.setText(sentenciaPenal.getDelitopenal());
        holder.txtfallopenal.setText(sentenciaPenal.getFallopenal());
        holder.txtparammodalidad.setText(sentenciaPenal.getParammodalidad());

        holder.txtotramodalidad.setText(" - " + sentenciaPenal.getOtramodalidad());
        holder.txtparamcuplefallo.setText(sentenciaPenal.getParamcuplefallo());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtexpedientepenal, txtorganojudipenal, txtdelitopenal, txtfallopenal,txtparammodalidad,txtotramodalidad,txtparamcuplefallo;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtexpedientepenal = (TextView) v.findViewById(R.id.txtexpedientepenal);
            txtorganojudipenal = (TextView) v.findViewById(R.id.txtorganojudipenal);
            txtdelitopenal = (TextView) v.findViewById(R.id.txtdelitopenal);
            txtfallopenal = (TextView) v.findViewById(R.id.txtfallopenal);

            txtparammodalidad = (TextView) v.findViewById(R.id.txtparammodalidad);
            txtotramodalidad = (TextView) v.findViewById(R.id.txtotramodalidad);
            txtotramodalidad = (TextView) v.findViewById(R.id.txtotramodalidad);
            txtparamcuplefallo = (TextView) v.findViewById(R.id.txtparamcuplefallo);

        }

    }

}

package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Candidato;
import com.appgrouplab.evaluometro.appmovil.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class listarCandidatosAdapterVisor extends RecyclerView.Adapter<listarCandidatosAdapterVisor.ViewHolder> implements  View.OnClickListener {
    private ArrayList<Candidato> mDataSet;
    private Context context;
    private View.OnClickListener listener;


    public listarCandidatosAdapterVisor(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<Candidato> dataset){
        //int oldItems = mDataSet.size();
        mDataSet = dataset;
        notifyDataSetChanged();
        //notifyItemRangeRemoved(0,oldItems);
        Log.d("setDataset", "" + mDataSet.size());
        //notifyItemRangeInserted(0,mDataSet.size());
    }



    @Override
    public listarCandidatosAdapterVisor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listadocandidatos,null,false);

        v.setOnClickListener(this);
        return new listarCandidatosAdapterVisor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listarCandidatosAdapterVisor.ViewHolder holder, int position) {

        Candidato candidato = mDataSet.get(position);
        holder.txtCandidato.setText(candidato.getNombre());
        holder.txtPartido.setText(candidato.getPartido());
        Picasso.with(context).load(candidato.getFotoPartido())
                .fit()
                .into(holder.imgPartido);
        Picasso.with(context).load(candidato.getFotoCandidato())
                .fit()
                .into(holder.imgCanditado);

        holder.idCandidato = candidato.getId();
        holder.strRutaFondo = candidato.getFotoFondo();
        holder.strRutaPartido = candidato.getFotoPartido();
        holder.strSigla = candidato.getAbreviatura();
        holder.strTipoCandidato = candidato.getTipoCandidatura();
        holder.strRutaCandidato = candidato.getFotoCandidato();
        holder.txtVotos.setText(candidato.getVotos().toString());
        holder.txtPorcentaje.setText(candidato.getPorcentaje().toString() + " %");


        //holder.setOnClickListener();


    }




    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;

    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void limpiar(){
        //notifyItemRangeRemoved(0,mDataSet.size());
        mDataSet.clear();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtCandidato, txtPartido, txtVotos, txtPorcentaje;
        public ImageView imgCanditado, imgPartido;
        //public ImageButton btnSiguiente;
        private Context vcontext;
        private Integer idCandidato;
        private String strRutaFondo, strRutaCandidato, strRutaPartido, strSigla, strTipoCandidato;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtCandidato = (TextView) v.findViewById(R.id.txtCandidato);
            txtPartido = (TextView) v.findViewById(R.id.txtPartido);
            txtVotos = (TextView) v.findViewById(R.id.txtVotos);
            txtPorcentaje = (TextView) v.findViewById(R.id.txtPorcentaje);
            imgCanditado = (ImageView) v.findViewById(R.id.imgCandidato);
            imgPartido = (ImageView) v.findViewById(R.id.imgPartido);

        }

    }



}


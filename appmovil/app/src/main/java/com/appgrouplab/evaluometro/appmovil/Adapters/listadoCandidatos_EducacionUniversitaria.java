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
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionUniversitaria;
import com.appgrouplab.evaluometro.appmovil.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class listadoCandidatos_EducacionUniversitaria extends RecyclerView.Adapter<listadoCandidatos_EducacionUniversitaria.ViewHolder>  {

    private ArrayList<EducacionUniversitaria> mDataSet;
    private Context context;

    public listadoCandidatos_EducacionUniversitaria(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<EducacionUniversitaria> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_EducacionUniversitaria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_estudios,null,false);

        return new listadoCandidatos_EducacionUniversitaria.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_EducacionUniversitaria.ViewHolder holder, int position) {

        EducacionUniversitaria educacionUniversitaria = mDataSet.get(position);

        holder.txtuniversidad.setText(educacionUniversitaria.getUniversidad());
        holder.txtcarrera.setText(educacionUniversitaria.getCarrerauni());


        holder.txtconcluidoeduuni.setText(educacionUniversitaria.getConcluidoeduuni());
        holder.txtegresadoeduuni.setText(educacionUniversitaria.getEgresadoeduuni());
        holder.txtbachillereduuni.setText(educacionUniversitaria.getBachillereduuni());

        holder.txtaniobachiller.setText(" - " + educacionUniversitaria.getAniobachiller().toString());
        holder.txttitulouni.setText(educacionUniversitaria.getTitulouni());
        holder.txtaniotitulo.setText(" - " + educacionUniversitaria.getAniotitulo().toString());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtuniversidad, txtcarrera, txtconcluidoeduuni, txtegresadoeduuni,txtbachillereduuni,txtaniobachiller,txttitulouni,txtaniotitulo;
        //public ImageView imgCanditado, imgPartido;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtuniversidad = (TextView) v.findViewById(R.id.txtuniversidad);
            txtcarrera = (TextView) v.findViewById(R.id.txtcarrera);
            txtconcluidoeduuni = (TextView) v.findViewById(R.id.txtconcluidoeduuni);
            txtegresadoeduuni = (TextView) v.findViewById(R.id.txtegresadoeduuni);

            txtbachillereduuni = (TextView) v.findViewById(R.id.txtbachillereduuni);
            txtaniobachiller = (TextView) v.findViewById(R.id.txtaniobachiller);
            txttitulouni = (TextView) v.findViewById(R.id.txttitulouni);
            txtaniotitulo = (TextView) v.findViewById(R.id.txtaniotitulo);
            //imgCanditado = (ImageView) v.findViewById(R.id.imgCandidato);
            //imgPartido = (ImageView) v.findViewById(R.id.imgPartido);

        }

    }
}

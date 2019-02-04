package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionNoUniversitaria;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_EducacionNoUniversitaria extends RecyclerView.Adapter<listadoCandidatos_EducacionNoUniversitaria.ViewHolder> {

    private ArrayList<EducacionNoUniversitaria> mDataSet;
    private Context context;

    public listadoCandidatos_EducacionNoUniversitaria(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<EducacionNoUniversitaria> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_EducacionNoUniversitaria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_estudiosnouniversitarios,null,false);

        return new listadoCandidatos_EducacionNoUniversitaria.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_EducacionNoUniversitaria.ViewHolder holder, int position) {

        EducacionNoUniversitaria educacionnonoUniversitaria = mDataSet.get(position);

        holder.txtcentroestudionouni.setText(educacionnonoUniversitaria.getCentroestudiosnouni());
        holder.txtcarreranouni.setText(educacionnonoUniversitaria.getCarreranouni());
        holder.txtconcluidonouni.setText(educacionnonoUniversitaria.getConcluidonouni());


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtcentroestudionouni, txtcarreranouni, txtconcluidonouni;
        //public ImageView imgCanditado, imgPartido;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtcentroestudionouni = (TextView) v.findViewById(R.id.txtcentroestudionouni);
            txtcarreranouni = (TextView) v.findViewById(R.id.txtcarreranouni);
            txtconcluidonouni = (TextView) v.findViewById(R.id.txtconcluidonouni);

        }

    }

}

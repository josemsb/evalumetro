package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.EducacionTecnica;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_EducacionTecnica extends RecyclerView.Adapter<listadoCandidatos_EducacionTecnica.ViewHolder> {

    private ArrayList<EducacionTecnica> mDataSet;
    private Context context;

    public listadoCandidatos_EducacionTecnica(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<EducacionTecnica> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_EducacionTecnica.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_estudiostecnicos,null,false);

        return new listadoCandidatos_EducacionTecnica.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_EducacionTecnica.ViewHolder holder, int position) {

        EducacionTecnica educacionTecnica = mDataSet.get(position);

        holder.txtcentroestudiotecnico.setText(educacionTecnica.getCenestudiotecnico());
        holder.txtcarreratecnica.setText(educacionTecnica.getCarreratecnica());
        holder.txtconcluidotecnica.setText(educacionTecnica.getConcluidoedutecnico());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtcentroestudiotecnico, txtcarreratecnica, txtconcluidotecnica;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtcentroestudiotecnico = (TextView) v.findViewById(R.id.txtcentroestudiotecnico);
            txtcarreratecnica = (TextView) v.findViewById(R.id.txtcarreratecnica);
            txtconcluidotecnica = (TextView) v.findViewById(R.id.txtconcluidotecnica);


        }

    }
}

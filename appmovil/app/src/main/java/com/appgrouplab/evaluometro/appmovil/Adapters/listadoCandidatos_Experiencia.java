package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.ExperienciaLaboral;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_Experiencia extends RecyclerView.Adapter<listadoCandidatos_Experiencia.ViewHolder>  {

    private ArrayList<ExperienciaLaboral> mDataSet;
    private Context context;

    public listadoCandidatos_Experiencia(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<ExperienciaLaboral> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_Experiencia.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_trabajos,null,false);

        return new listadoCandidatos_Experiencia.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_Experiencia.ViewHolder holder, int position) {

        ExperienciaLaboral experienciaLaboral = mDataSet.get(position);

        holder.txtcentrotrabajo.setText(experienciaLaboral.getCentrotrabajo());
        holder.txtocupacionprofesion.setText(experienciaLaboral.getOcupacionprofesional());


        holder.txtaniotrabajodesde.setText(experienciaLaboral.getAniotrabajodesde());
        holder.txtaniotrabajohasta.setText(experienciaLaboral.getAniotrabajohasta());
        holder.txttrabajopais.setText(experienciaLaboral.getTrabajopais());

        holder.txttrabajodepartamento.setText(experienciaLaboral.getTrabajodepartamento());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtcentrotrabajo, txtocupacionprofesion, txtaniotrabajodesde, txtaniotrabajohasta,txttrabajopais,txttrabajodepartamento;
        //public ImageView imgCanditado, imgPartido;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtcentrotrabajo = (TextView) v.findViewById(R.id.txtcentrotrabajo);
            txtocupacionprofesion = (TextView) v.findViewById(R.id.txtocupacionprofesion);
            txtaniotrabajodesde = (TextView) v.findViewById(R.id.txtaniotrabajodesde);
            txtaniotrabajohasta = (TextView) v.findViewById(R.id.txtaniotrabajohasta);

            txttrabajopais = (TextView) v.findViewById(R.id.txttrabajopais);
            txttrabajodepartamento = (TextView) v.findViewById(R.id.txttrabajodepartamento);

        }

    }
}

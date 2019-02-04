package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.CargoEleccionPopular;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_CargoEleccionPopular extends RecyclerView.Adapter<listadoCandidatos_CargoEleccionPopular.ViewHolder> {

    private ArrayList<CargoEleccionPopular> mDataSet;
    private Context context;

    public listadoCandidatos_CargoEleccionPopular(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<CargoEleccionPopular> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }
    @Override
    public listadoCandidatos_CargoEleccionPopular.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_cargoeleccionpopular,null,false);

        return new listadoCandidatos_CargoEleccionPopular.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_CargoEleccionPopular.ViewHolder holder, int position) {

        CargoEleccionPopular cargoEleccionPopular = mDataSet.get(position);

        holder.txtorgpolcargoelec.setText(cargoEleccionPopular.getOrgpolcargoelec());
        holder.txtcargoeleccion.setText(cargoEleccionPopular.getCargoeleccion());


        holder.txtaniocargoelecdesde.setText(cargoEleccionPopular.getAniocargoelecdesde());
        holder.txtaniocargoelechasta.setText(cargoEleccionPopular.getAniocargoelechasta());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtorgpolcargoelec, txtcargoeleccion, txtaniocargoelecdesde, txtaniocargoelechasta;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtorgpolcargoelec = (TextView) v.findViewById(R.id.txtorgpolcargoelec);
            txtcargoeleccion = (TextView) v.findViewById(R.id.txtcargoeleccion);
            txtaniocargoelecdesde = (TextView) v.findViewById(R.id.txtaniocargoelecdesde);
            txtaniocargoelechasta = (TextView) v.findViewById(R.id.txtaniocargoelechasta);

        }

    }
}

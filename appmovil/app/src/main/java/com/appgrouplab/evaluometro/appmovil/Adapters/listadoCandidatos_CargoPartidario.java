package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.CargoPartidario;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_CargoPartidario extends RecyclerView.Adapter<listadoCandidatos_CargoPartidario.ViewHolder> {

    private ArrayList<CargoPartidario> mDataSet;
    private Context context;

    public listadoCandidatos_CargoPartidario(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<CargoPartidario> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }
    @Override
    public listadoCandidatos_CargoPartidario.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_cargopartidario,null,false);

        return new listadoCandidatos_CargoPartidario.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_CargoPartidario.ViewHolder holder, int position) {

        CargoPartidario cargoPartidario = mDataSet.get(position);

        holder.txtorgpolcargopartidario.setText(cargoPartidario.getOrgpolcargopartidario());
        holder.txtcargopartidario.setText(cargoPartidario.getCargopartidario());
        holder.txtaniocargopartidesde.setText(cargoPartidario.getAniocargopartidesde());
        holder.txtaniocargopartihasta.setText(cargoPartidario.getAniocargopartihasta());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtorgpolcargopartidario, txtcargopartidario, txtaniocargopartidesde, txtaniocargopartihasta;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtorgpolcargopartidario = (TextView) v.findViewById(R.id.txtorgpolcargopartidario);
            txtcargopartidario = (TextView) v.findViewById(R.id.txtcargopartidario);
            txtaniocargopartidesde = (TextView) v.findViewById(R.id.txtaniocargopartidesde);
            txtaniocargopartihasta = (TextView) v.findViewById(R.id.txtaniocargopartihasta);

        }

    }
}

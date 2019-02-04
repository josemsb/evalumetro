package com.appgrouplab.evaluometro.appmovil.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.CargoEleccionPopular;
import com.appgrouplab.evaluometro.appmovil.Datos.Informacion.Renuncias;
import com.appgrouplab.evaluometro.appmovil.R;

import java.util.ArrayList;

public class listadoCandidatos_Renuncias extends RecyclerView.Adapter<listadoCandidatos_Renuncias.ViewHolder> {

    private ArrayList<Renuncias> mDataSet;
    private Context context;

    public listadoCandidatos_Renuncias(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<Renuncias> dataset){
        mDataSet = dataset;
        notifyDataSetChanged();

    }

    @Override
    public listadoCandidatos_Renuncias.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_candidato_renuncia,null,false);

        return new listadoCandidatos_Renuncias.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(listadoCandidatos_Renuncias.ViewHolder holder, int position) {

        Renuncias renuncias = mDataSet.get(position);
        holder.txtorgpolrenunciaop.setText(renuncias.getOrgpolrenunciaop());
        holder.txtaniorenunciaop.setText(renuncias.getAniorenunciaop());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtorgpolrenunciaop, txtaniorenunciaop;
        private Context vcontext;


        public ViewHolder(View v){
            super(v);
            vcontext = v.getContext();
            txtorgpolrenunciaop = (TextView) v.findViewById(R.id.txtorgpolrenunciaop);
            txtaniorenunciaop = (TextView) v.findViewById(R.id.txtaniorenunciaop);
        }

    }

}

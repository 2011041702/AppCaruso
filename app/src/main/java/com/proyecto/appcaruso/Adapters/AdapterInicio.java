package com.proyecto.appcaruso.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.proyecto.appcaruso.Class.Productos;
import com.proyecto.appcaruso.R;

import java.util.List;

public class AdapterInicio extends RecyclerView.Adapter<AdapterInicio.ViewHolder> {

    private Context context;
    private List<Productos> productoses;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imgee;

        public ViewHolder(View itemView) {
            super(itemView);
            imgee = (ImageView) itemView.findViewById(R.id.ImageInicio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }


    public AdapterInicio(List<Productos> productoses, Context context, EscuchaEventosClick escucha) {
        this.productoses = productoses;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return productoses.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_inicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Productos prom = productoses.get(position);

        Glide.with(context)
                .load("http://192.168.0.16:9000"+ prom.getImagen())
                .crossFade()
                .centerCrop()

                .into(holder.imgee);
    }

}

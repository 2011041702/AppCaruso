package com.proyecto.appcaruso.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.proyecto.appcaruso.Class.Pedidos;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.R;

import java.util.List;


public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolder> {

    private Context context;
    private List<Pedidos> pedidoses;
    private AdapterPedidos.EscuchaEventosClick escucha;
    private Config config;

    public interface EscuchaEventosClick {
        void onItemClick(AdapterPedidos.ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ImagePedidosImagen;
        public TextView TextPedidoTitulo,TextPedidoPrecio,TextPedidoCantidad;

        public ViewHolder(View itemView) {
            super(itemView);
            ImagePedidosImagen = (ImageView) itemView.findViewById(R.id.ImagePedidosImagen);
            TextPedidoTitulo = (TextView) itemView.findViewById(R.id.TextPedidoTitulo);
            TextPedidoPrecio = (TextView) itemView.findViewById(R.id.TextPedidoPrecio);
            TextPedidoCantidad = (TextView) itemView.findViewById(R.id.TextPedidoCantidad);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }


    public AdapterPedidos(List<Pedidos> pedidoses, Context context, AdapterPedidos.EscuchaEventosClick escucha) {
        this.pedidoses = pedidoses;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return pedidoses.size();
    }

    @Override
    public AdapterPedidos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_pedidos, parent, false);
        return new AdapterPedidos.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterPedidos.ViewHolder holder, int position) {
        Pedidos prom = pedidoses.get(position);

        Glide.with(context)
                .load( Config.IP + prom.getImagen())
                .crossFade()
                .centerCrop()
                .into(holder.ImagePedidosImagen);
        holder.TextPedidoTitulo.setText(prom.getProducto());
        holder.TextPedidoCantidad.setText(prom.getCantidad());
        holder.TextPedidoPrecio.setText(prom.getSubtotal());
    }
}

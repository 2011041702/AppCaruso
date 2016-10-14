package com.proyecto.appcaruso.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyecto.appcaruso.Class.Categorias;
import com.proyecto.appcaruso.R;

import java.util.List;


public class AdapterCategorias extends RecyclerView.Adapter<AdapterCategorias.ViewHolder> {

    private Context context;
    private List<Categorias> categoriases;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textcategoria);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }


    public AdapterCategorias(List<Categorias> categoriases, Context context, EscuchaEventosClick escucha) {
        this.categoriases = categoriases;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return categoriases.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_categorias, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categorias prom = categoriases.get(position);

        holder.textView.setText(prom.getNomb());
    }
}

package com.proyecto.appcaruso.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.proyecto.appcaruso.Adapters.AdapterCategorias;
import com.proyecto.appcaruso.Class.Categorias;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 01/10/2016.
 */

public class Fragment_Categorias extends Fragment implements AdapterCategorias.EscuchaEventosClick {

    private List<Categorias> categoriasList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;

    public Fragment_Categorias() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_categorias, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_categorias);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        categoriasList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        ObtenerDatos();
        adapter = new AdapterCategorias(categoriasList, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest ObtenerDatosServidor(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.CATEGORIAS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AnalaizarDatos(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No se ha podido entablar conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        return jsonArrayRequest;
    }
    private void ObtenerDatos() {
        requestQueue.add(ObtenerDatosServidor(requestCount));
        requestCount++;
    }
    private void AnalaizarDatos(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Categorias prom = new Categorias();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                prom.setId(json.getString(Config.TAG_ID));
                prom.setNomb(json.getString(Config.TAG_NOMBRE));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            categoriasList.add(prom);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterCategorias.ViewHolder holder, int posicion) {

    }
}

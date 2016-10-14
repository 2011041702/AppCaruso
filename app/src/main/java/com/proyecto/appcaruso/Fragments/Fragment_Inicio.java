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
import com.proyecto.appcaruso.Adapters.AdapterInicio;
import com.proyecto.appcaruso.Class.Productos;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Inicio extends Fragment implements AdapterInicio.EscuchaEventosClick{


    private List<Productos> productosList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;


    public Fragment_Inicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_inicio);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        productosList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        ObtenerDatos();
        adapter = new AdapterInicio(productosList, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest ObtenerDatosServidor(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.PRODUCTOS,
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
            Productos prom = new Productos();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                prom.setId(json.getString(Config.TAG_ID));
                prom.setNombre(json.getString(Config.TAG_NOMBRE));
                prom.setImagen(json.getString(Config.TAG_IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            productosList.add(prom);
        }
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(AdapterInicio.ViewHolder holder, int posicion) {

    }
}

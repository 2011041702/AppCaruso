package com.proyecto.appcaruso.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
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
import com.proyecto.appcaruso.Adapters.AdapterCategoriaProducto;
import com.proyecto.appcaruso.Class.Productos;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_CategoriaProductos extends Fragment implements AdapterCategoriaProducto.EscuchaEventosClick{


    private List<Productos> productosList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;

    private String extra;
    private static final String EXTRA_ID = "IDMETA";

    public Fragment_CategoriaProductos() {
    }

    public static Fragment_CategoriaProductos createInstance(String id) {
        Fragment_CategoriaProductos detailFragment = new Fragment_CategoriaProductos();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_categoria_productos, container, false);

        extra = getArguments().getString(EXTRA_ID);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_categoria_producto);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);

        productosList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        ObtenerDatos();
        adapter = new AdapterCategoriaProducto(productosList, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest ObtenerDatosServidor(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.PRODUCTOS_CATEGORIA + extra,
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
    public void onItemClick(AdapterCategoriaProducto.ViewHolder holder, int posicion) {
        String id = productosList.get(posicion).getId();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, Fragment_DetalleProducto_Categoria.createInstance(id), "Fragment_DetalleProducto_Categoria").addToBackStack("tag").commit();
    }
}

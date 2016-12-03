package com.proyecto.appcaruso.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.proyecto.appcaruso.Adapters.AdapterPedidos;
import com.proyecto.appcaruso.Class.Pedidos;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Pedidos extends Fragment implements AdapterPedidos.EscuchaEventosClick, View.OnClickListener{


    private List<Pedidos> mis_pedidosList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private String email;
    private Button btnComprar;
    private TextView txtPedidoTotal;
    int a;
    String ad;

    public Fragment_Pedidos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        boolean loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        if (loggedIn){
            View v = inflater.inflate(R.layout.fragment_pedidos, container, false);
            recyclerView = (RecyclerView)v.findViewById(R.id.recycler_pedidos);
            txtPedidoTotal = (TextView)v.findViewById(R.id.txtPedidoTotal);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            mis_pedidosList = new ArrayList<>();
            requestQueue = Volley.newRequestQueue(getActivity());
            ObtenerDatos();
            obtenerTotal();
            adapter = new AdapterPedidos(mis_pedidosList, getActivity(), this);
            recyclerView.setAdapter(adapter);
            btnComprar = (Button)v.findViewById(R.id.btncarrito);
            btnComprar.setOnClickListener(this);
            return v;
        }else{
            View v = inflater.inflate(R.layout.fragment_pedidos_off, container, false);
            return v;
        }
    }
    private JsonArrayRequest ObtenerDatosServidor(int requestCount) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.MIS_PEDIDOS + email,
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
            Pedidos prom = new Pedidos();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                prom.setCliente(json.getString(Config.KEY_EMP_CLI));
                prom.setProducto(json.getString(Config.KEY_EMP_PROD));
                prom.setCantidad(json.getString(Config.KEY_EMP_CANT));
                prom.setPrecio(json.getString(Config.KEY_EMP_PREC));
                prom.setSubtotal(json.getString(Config.KEY_EMP_SUB));
                prom.setImagen(json.getString(Config.KEY_EMP_IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mis_pedidosList.add(prom);
        }
        adapter.notifyDataSetChanged();
    }


    private void obtenerTotal() {
        class ObtenerTotal extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MostrarTotal(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.TOTAL,email);
                return s;
            }
        }
        ObtenerTotal ge = new ObtenerTotal();
        ge.execute();
    }

    private void MostrarTotal(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String total = c.getString(Config.TAG_TOTAL);

            if(total.equals("null")){
                txtPedidoTotal.setText("Total: S/. 0");
            }else{
                txtPedidoTotal.setText("Total: S/. "+ total);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterPedidos.ViewHolder holder, int posicion) {
        
    }

    @Override
    public void onClick(View view) {
        if (view == btnComprar){
            FragmentManager fragmentManager = getFragmentManager();
            Fragment_Compras fragment_compras = new Fragment_Compras();

            String total = txtPedidoTotal.getText().toString().trim();
            //fragmentManager.beginTransaction().replace(R.id.content_main, fragment_compras).addToBackStack("tag").commit();
            fragmentManager.beginTransaction().replace(R.id.content_main, Fragment_Compras.createInstance(total), "Fragment_Compras").addToBackStack("tag").commit();
        }
    }
}

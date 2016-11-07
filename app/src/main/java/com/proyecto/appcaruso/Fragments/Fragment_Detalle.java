package com.proyecto.appcaruso.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_Detalle extends Fragment {

    private ImageView imageDetalle;
    private TextView TextNombDetalle, TextCatDetalle,TextDescripcion;
    private Spinner spinner;

    private String extra;
    private static final String EXTRA_ID = "IDMETA";

    public Fragment_Detalle() {
    }

    public static Fragment_Detalle createInstance(String id) {
        Fragment_Detalle detailFragment = new Fragment_Detalle();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detalle, container, false);
        extra = getArguments().getString(EXTRA_ID);

        imageDetalle = (ImageView)v.findViewById(R.id.imageDetalle);
        TextNombDetalle = (TextView)v.findViewById(R.id.TextNombDetalle);
        TextCatDetalle = (TextView)v.findViewById(R.id.TextCatDetalle);
        TextDescripcion = (TextView)v.findViewById(R.id.TextDescripcion);
        spinner = (Spinner)v.findViewById(R.id.spinner2);

        final ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.cantidad,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        obtenerDatos();
        return v;
    }

    private void obtenerDatos() {
        class ObtenerDatos extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MostrarDatos(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.PROD_DETALLE,extra);
                return s;
            }
        }
        ObtenerDatos ge = new ObtenerDatos();
        ge.execute();
    }

    private void MostrarDatos(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String name__ = c.getString(Config.TAG_NAME);
            String categoria__ = c.getString(Config.TAG_CATEGORIA);
            String imagen__ = c.getString(Config.TAG_IMG);
            String descripcion__ = c.getString(Config.TAG_DESCRIPCION);


            TextNombDetalle.setText(name__);
            TextCatDetalle.setText(categoria__);

            Glide.with(getActivity())
                    .load("http://192.168.0.10:9000" + imagen__)
                    .crossFade()
                    .centerCrop()
                    .into(imageDetalle);

            TextDescripcion.setText(descripcion__);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

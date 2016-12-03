package com.proyecto.appcaruso.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class Fragment_DetalleProducto_Categoria extends Fragment implements View.OnClickListener{


    private ImageView imageDetalle;
    private TextView TextNombDetalle, TextCatDetalle,TextDescripcion,TextPrecio;
    private Spinner spinner;
    private boolean loggedIn = false;
    private String asd="aa";
    private Button carrito;

    private String extra;
    private static final String EXTRA_ID = "IDMETA";


    public Fragment_DetalleProducto_Categoria() {
    }


    public static Fragment_DetalleProducto_Categoria createInstance(String id) {
        Fragment_DetalleProducto_Categoria detailFragment = new Fragment_DetalleProducto_Categoria();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detalle_producto_categoria, container, false);
        extra = getArguments().getString(EXTRA_ID);

        imageDetalle = (ImageView)v.findViewById(R.id.imageDetalle);
        TextNombDetalle = (TextView)v.findViewById(R.id.TextNombDetalle);
        TextCatDetalle = (TextView)v.findViewById(R.id.TextCatDetalle);
        TextDescripcion = (TextView)v.findViewById(R.id.TextDescripcion);
        TextPrecio = (TextView)v.findViewById(R.id.TextPrecio);
        carrito = (Button)v.findViewById(R.id.btncarrito);
        spinner = (Spinner)v.findViewById(R.id.spinner_cantidad);


        carrito.setOnClickListener(this);
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
            String precio__ = c.getString(Config.TAG_PRE);


            TextNombDetalle.setText(name__);
            TextCatDetalle.setText(categoria__);

            Glide.with(getActivity())
                    .load(Config.IP + imagen__)
                    .crossFade()
                    .centerCrop()
                    .into(imageDetalle);

            TextDescripcion.setText(descripcion__);
            TextPrecio.setText(precio__);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == carrito){
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
            if(loggedIn){
                AñadirCarrito();
            }else{
                Dialogo();
            }
        }
    }

    private void AñadirCarrito() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        String S_producto = TextNombDetalle.getText().toString();
        int Sub_precio = Integer.parseInt(TextPrecio.getText().toString());
        String subprecio = TextPrecio.getText().toString();
        String cantidad = spinner.getSelectedItem().toString();
        int sub_total = Sub_precio * Integer.parseInt(cantidad);
        String subtotal = String.valueOf(sub_total);
        AgregarPedidos(email,S_producto,cantidad,subprecio,subtotal);
    }

    private void AgregarPedidos(final String email, final String producto, final String cantidad, final String precio, final String subtotal) {

        class AgregarPedidos extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_CLI,email);
                params.put(Config.KEY_EMP_PROD,producto);
                params.put(Config.KEY_EMP_CANT,cantidad);
                params.put(Config.KEY_EMP_PREC,precio);
                params.put(Config.KEY_EMP_SUB,subtotal);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.PEDIDOS, params);
                return res;
            }
        }
        AgregarPedidos ap = new AgregarPedidos();
        ap.execute();
    }

    private void Dialogo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Antes de poder añadir algun producto a su carrito, inicie sesión primero");
        alertDialogBuilder.setPositiveButton("Ir a Sesión",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Fragment_Login frag = new Fragment_Login();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, frag)
                                .commit();
                    }
                });

        alertDialogBuilder.setNegativeButton("No gracias",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

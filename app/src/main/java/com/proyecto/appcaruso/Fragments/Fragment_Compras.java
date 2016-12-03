package com.proyecto.appcaruso.Fragments;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Fragment_Compras extends Fragment {

    private String extra;
    private static final String EXTRA_ID = "IDMETA";
    private Button btnConfirmar;
    private int total;
    private String email,distrito_elegido;
    private Spinner combo_distritos;
    ArrayList<String> DistritosHabiles = new ArrayList<>();
    private EditText txtObservaciones;
    private EditText nombre_confirmar_pedido, telefono_confirmar_pedido, direccion_confirmar_pedido, NroLte_confirmar_pedido, urbanizacion_confirmar_pedido, referencia_confirmar_pedido;

    public Fragment_Compras() {
    }

    public static Fragment_Compras createInstance(String total) {
        Fragment_Compras detailFragment = new Fragment_Compras();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, total);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_confirmar_pedido, container, false);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
            nombre_confirmar_pedido = (EditText)v.findViewById(R.id.nombre_confirmar_pedido);
            telefono_confirmar_pedido = (EditText)v.findViewById(R.id.telefono_confirmar_pedido);
            direccion_confirmar_pedido = (EditText)v.findViewById(R.id.direccion_confirmar_pedido);
            referencia_confirmar_pedido = (EditText)v.findViewById(R.id.referencia_confirmar_pedido);
            txtObservaciones = (EditText)v.findViewById(R.id.txtobservaciones);
            combo_distritos = (Spinner)v.findViewById(R.id.spinner_distritos);

            DistritosHabiles.add("Seleccionar Distrito");
            DistritosHabiles.add("Ciudad Nueva");
            DistritosHabiles.add("Tacna");
            DistritosHabiles.add("Gregorio Albarracin");
            DistritosHabiles.add("Pocollay");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, DistritosHabiles);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            combo_distritos.setAdapter(dataAdapter);

            combo_distritos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String distr = DistritosHabiles.get(i);
                    distrito_elegido = distr;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnConfirmar = (Button)v.findViewById(R.id.btncontinuar);
            extra = getArguments().getString(EXTRA_ID);
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (extra.equals("0")){
                        Toast.makeText(getActivity(), "No tiene ningun producto en lista para enviar, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }else{
                        Dialogo();
                    }
                }
            });
            return v;
    }

    private void AgregarEnvio(){

        final String nombre = nombre_confirmar_pedido.getText().toString().trim();
        final String telefono = telefono_confirmar_pedido.getText().toString().trim();
        final String direccion = direccion_confirmar_pedido.getText().toString().trim();
        final String refe = referencia_confirmar_pedido.getText().toString().trim();
        final String distrito = distrito_elegido;
        final String observaciones = txtObservaciones.getText().toString().trim();


        class agregarEnvio extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_ENV_nombre,nombre);
                params.put(Config.KEY_ENV_telefono,telefono);
                params.put(Config.KEY_ENV_direccion,direccion);
                params.put(Config.KEY_ENV_refe,refe);
                params.put(Config.KEY_ENV_distrito,distrito);
                params.put(Config.KEY_ENV_observaciones,observaciones);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.ENVIOS, params);
                return res;
            }
        }
        agregarEnvio ae = new agregarEnvio();
        ae.execute();
    }

    private void mtdModificarDatos(){

        final String cliente = email;

        class ActualizarDatos extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EST_CLIENTE,cliente);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.ESTADO,hashMap);
                return s;
            }
        }
        ActualizarDatos ue = new ActualizarDatos();
        ue.execute();
    }

    private void Dialogo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("El envio se procesara segun sus datos ingresados");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        AgregarEnvio();
                        mtdModificarDatos();
                        Fragment_Inicio frag = new Fragment_Inicio();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, frag)
                                .commit();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

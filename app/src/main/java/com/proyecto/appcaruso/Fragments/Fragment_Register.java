package com.proyecto.appcaruso.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;

import java.util.HashMap;


public class Fragment_Register extends Fragment implements View.OnClickListener {

    private EditText nombres,apellidos,dni,correo,contrasena,telefono;
    private Button registrar;

    public Fragment_Register() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registro, container, false);

        nombres = (EditText)v.findViewById(R.id.nombre_input);
        apellidos = (EditText)v.findViewById(R.id.apellido_input);
        dni = (EditText)v.findViewById(R.id.dni_input);
        correo = (EditText)v.findViewById(R.id.email_input_reg);
        contrasena = (EditText)v.findViewById(R.id.contrasena_imput_reg);
        telefono = (EditText)v.findViewById(R.id.telefono_input);

        registrar = (Button)v.findViewById(R.id.crear_cuenta);

        registrar.setOnClickListener(this);

        return v;
    }

    private void addEmployee(){

        final String nomb = nombres.getText().toString().toLowerCase();
        final String apel = apellidos.getText().toString().toLowerCase();
        final String dni_ = dni.getText().toString().toLowerCase();
        final String email = correo.getText().toString().toLowerCase();
        final String pass = contrasena.getText().toString().toLowerCase();
        final String telf = telefono.getText().toString().toLowerCase();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Registrando","Espere...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_USU_NOMBRE,nomb);
                params.put(Config.KEY_USU_APELLIDO,apel);
                params.put(Config.KEY_USU_DNI,dni_);
                params.put(Config.KEY_USU_TELEFONO,telf);
                params.put(Config.KEY_USU_EMAIL,email);
                params.put(Config.KEY_USU_PASS,pass);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void ClearAll(){
        nombres.setText("");
        apellidos.setText("");
        dni.setText("");
        telefono.setText("");
        correo.setText("");
        contrasena.setText("");
    }

    @Override
    public void onClick(View view) {
        addEmployee();
        ClearAll();
    }
}

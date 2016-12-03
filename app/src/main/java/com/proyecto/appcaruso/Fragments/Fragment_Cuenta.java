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
import android.widget.Button;
import android.widget.EditText;

import com.proyecto.appcaruso.Conecction.Config;
import com.proyecto.appcaruso.Conecction.RequestHandler;
import com.proyecto.appcaruso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_Cuenta extends Fragment implements View.OnClickListener{

    private EditText nombres,apellidos,dni,correo,contrasena,telefono;
    private Button Modificar, Logout;
    private String email;

    public Fragment_Cuenta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cuenta, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        nombres = (EditText)v.findViewById(R.id.nombre_mostrar);
        apellidos = (EditText)v.findViewById(R.id.apellido_mostrar);
        dni = (EditText)v.findViewById(R.id.dni_mostrar);
        correo = (EditText)v.findViewById(R.id.email_mostrar);
        contrasena = (EditText)v.findViewById(R.id.contrasena_mostrar);
        telefono = (EditText)v.findViewById(R.id.telefono_mostrar);

        Logout = (Button)v.findViewById(R.id.btnlogout);

        Logout.setOnClickListener(this);

        getEmployee();

        return v;
    }


    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.USUARIO,email);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String name__ = c.getString(Config.TAG_NAME);
            String apellido__ = c.getString(Config.TAG_APELLIDO);
            String dni__ = c.getString(Config.TAG_DNI);
            String telefono____ = c.getString(Config.TAG_TELEFONO);
            String email__ = c.getString(Config.TAG_EMAIL);
            String password__ = c.getString(Config.TAG_PASSWORD);


            nombres.setText(name__);
            apellidos.setText(apellido__);
            dni.setText(dni__);
            correo.setText(email__);
            contrasena.setText(password__);
            telefono.setText(telefono____);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Esta usted seguro de cerrar sesión?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences preferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        editor.apply();

                        FragmentPager frag = new FragmentPager();

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

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onClick(View view) {
        if (view == Logout){
            logout();
        }
    }
}

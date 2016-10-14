package com.proyecto.appcaruso.Conecction;

/**
 * Created by Christian on 07/10/2016.
 */

public class Config {

    private static final String IP = "http://192.168.0.16";
    public static final String PRODUCTOS = IP + ":9000/Caruso/WebServices/Productos.php";
    public static final String CATEGORIAS = IP + ":9000/Caruso/WebServices/Categorias.php";
    public static final String LOGIN_URL = IP + ":9000/Caruso/WebServices/Login.php";
    public static final String USUARIO = IP + ":9000/Caruso/WebServices/Usuario.php?email=";

    public static final String URL_ADD= IP + ":9000/Caruso/WebServices/UsuarioRegistro.php";


    public static final String TAG_ID = "id";
    public static final String TAG_NOMBRE = "nombre";
    public static final String TAG_PRE = "precio";
    public static final String TAG_IMG = "imagen";


    public static final String TAG_NAME = "nombre";
    public static final String TAG_APELLIDO = "apellido";
    public static final String TAG_DNI = "dni";
    public static final String TAG_TELEFONO = "telefono";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PASSWORD = "password";


    public static final String KEY_USU_NOMBRE = "nomb";
    public static final String KEY_USU_APELLIDO = "apell";
    public static final String KEY_USU_DNI = "dni";
    public static final String KEY_USU_TELEFONO = "telefono";
    public static final String KEY_USU_EMAIL = "email";
    public static final String KEY_USU_PASS = "password";


    public static final String TAG_JSON_ARRAY="result";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}

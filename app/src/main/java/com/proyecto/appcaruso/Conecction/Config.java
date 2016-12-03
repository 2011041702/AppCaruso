package com.proyecto.appcaruso.Conecction;



public class Config {

    public static String IP = "http://192.168.1.8:9000";

    public static String PRODUCTOS = IP + "/Caruso/WebServices/Productos.php";
    public static String CATEGORIAS = IP + "/Caruso/WebServices/Categorias.php";
    public static String LOGIN_URL = IP + "/Caruso/WebServices/Login.php";
    public static String USUARIO = IP + "/Caruso/WebServices/Usuario.php?email=";
    public static String URL_ADD= IP + "/Caruso/WebServices/UsuarioRegistro.php";
    public static String PROD_DETALLE= IP + "/Caruso/WebServices/ProductoDetalle.php?id=";
    public static String PEDIDOS= IP + "/Caruso/WebServices/AgregarPedidos.php";
    public static String MIS_PEDIDOS= IP + "/Caruso/WebServices/Pedidos.php?cliente=";
    public static String TOTAL= IP + "/Caruso/WebServices/Total.php?cliente=";
    public static String ENVIOS = IP + "/Caruso/WebServices/Envios.php";
    public static String ESTADO = IP + "/Caruso/WebServices/Estados.php";


    public static String PRODUCTOS_CATEGORIA = IP + "/Caruso/WebServices/ListarProductos_Categoria.php?categoria=";

    

    public static final String TAG_ID = "id";
    public static final String TAG_NOMBRE = "nombre";
    public static final String TAG_PRE = "precio";
    public static final String TAG_IMG = "imagen";
    public static final String TAG_DESCRIPCION = "descripcion";


    public static final String TAG_NAME = "nombre";
    public static final String TAG_APELLIDO = "apellido";
    public static final String TAG_DNI = "dni";
    public static final String TAG_TELEFONO = "telefono";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_CATEGORIA = "categoria";
    public static final String TAG_TOTAL = "total";



    public static final String KEY_USU_NOMBRE = "nomb";
    public static final String KEY_USU_APELLIDO = "apell";
    public static final String KEY_USU_DNI = "dni";
    public static final String KEY_USU_TELEFONO = "telefono";
    public static final String KEY_USU_EMAIL = "email";
    public static final String KEY_USU_PASS = "password";

    public static final String KEY_EMP_CLI = "cliente";
    public static final String KEY_EMP_PROD = "producto";
    public static final String KEY_EMP_CANT = "cantidad";
    public static final String KEY_EMP_PREC = "precio";
    public static final String KEY_EMP_SUB = "subtotal";
    public static final String KEY_EMP_IMG = "imagen";


    public static final String KEY_ENV_nombre = "nombre";
    public static final String KEY_ENV_telefono = "telefono";
    public static final String KEY_ENV_direccion = "direccion";
    public static final String KEY_ENV_refe = "refe";
    public static final String KEY_ENV_distrito = "distrito";
    public static final String KEY_ENV_observaciones = "observaciones";

    public static final String KEY_EST_CLIENTE = "cliente";


    public static final String TAG_JSON_ARRAY="result";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public static final String LOGIN_SUCCESS = "success";

    public static final String SHARED_PREF_NAME = "myloginapp";

    public static final String EMAIL_SHARED_PREF = "email";

    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}

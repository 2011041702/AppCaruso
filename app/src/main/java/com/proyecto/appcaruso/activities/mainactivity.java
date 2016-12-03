package com.proyecto.appcaruso.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.appcaruso.Fragments.FragmentPager;
import com.proyecto.appcaruso.Fragments.Fragment_Categorias;
import com.proyecto.appcaruso.Fragments.Fragment_Inicio;
import com.proyecto.appcaruso.Fragments.Fragment_Pedidos;
import com.proyecto.appcaruso.R;

public class mainactivity extends AppCompatActivity {

    DrawerLayout drawer;
    public TextView ddd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ddd = (TextView) findViewById(R.id.idToolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }
    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });

    }
    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_Inicio:
                fragmentManager.popBackStack();
                ddd.setText("Inicio");
                fragmentoGenerico = new Fragment_Inicio();
                break;
            case R.id.nav_Categorias:
                fragmentManager.popBackStack();
                ddd.setText("Categoria");
                fragmentoGenerico = new Fragment_Categorias();
                break;
            case R.id.nav_cuenta:
                fragmentManager.popBackStack();
                ddd.setText("Mi Cuenta");
                fragmentoGenerico = new FragmentPager();
                break;
            case R.id.nav_mis_pedidos:
                fragmentManager.popBackStack();
                ddd.setText("Mis Pedidos");
                fragmentoGenerico = new Fragment_Pedidos();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        //setTitle(itemDrawer.getTitle());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.s
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialogo();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void Dialogo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("¿Esta seguro de quere salir de la aplicación?");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);
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
}

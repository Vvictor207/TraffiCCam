package com.example.trafficcam;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.trafficcam.BD.DBHelper;
import com.example.trafficcam.BD.DBManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trafficcam.databinding.ActivityPantallaPrincipalBinding;

public class PantallaPrincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPantallaPrincipalBinding binding;
    DBManager db = new DBManager(this);



    Repositorio rp = new Repositorio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPantallaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Aquí aseguramos que la base de datos se cree


        db.open(); // Esto crea la base de datos si no existe
        setSupportActionBar(binding.appBarPantallaPrincipal.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.map, R.id.nav_favorites, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pantalla_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.cerrarSesion) {
                    // Maneja manualmente el ítem de cerrar sesión
                    mostrarDialogCerrarSesion();
                    return true; // Indica que el ítem fue manejado
                }

                // Deja que NavigationUI maneje otros ítems
                return NavigationUI.onNavDestinationSelected(item,
                        Navigation.findNavController(PantallaPrincipal.this, R.id.nav_host_fragment_content_pantalla_principal));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pantalla_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.cerrarSesion){
            mostrarDialogCerrarSesion();

            return true;
        }


        return super.onOptionsItemSelected(item);

    }



    public void mostrarDialogCerrarSesion(){
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Cerrar sesion")
                .setMessage("¿Seguro que quieres cerrar la sesion?")

                .setPositiveButton("Si",(dialog, witch) -> {

                   
                    finishAffinity();
                })
                .setNegativeButton("No", (dialog, wich)-> {
                    dialog.dismiss();

                })
                .show();


    }



}
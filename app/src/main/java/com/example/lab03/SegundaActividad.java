package com.example.lab03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class SegundaActividad extends AppCompatActivity {

    protected static final int TIMER_RUNTIME = 4000;
    protected boolean nbActivo;
    protected ProgressBar nProgressBar;
    protected ScrollView contentLayout; // ScrollView para el contenido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_actividad);

        // Inicializa barra de progreso y oculta el contenido principal
        nProgressBar = findViewById(R.id.progressBar);
        contentLayout = findViewById(R.id.contenidoSegunda);
        contentLayout.setVisibility(View.GONE);

        // Toolbar y Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_main) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_conversor) {
                startActivity(new Intent(this, ConversorMonedaActivity.class));
            } else if (id == R.id.nav_segunda) {
                drawerLayout.closeDrawers(); // ya estás aquí
            }
            return true;
        });

        // Hilo para simular carga con barra de progreso
        final Thread timerThread = new Thread(() -> {
            nbActivo = true;
            try {
                int espera1 = 0;
                while (nbActivo && espera1 < TIMER_RUNTIME) {
                    Thread.sleep(200);
                    if (nbActivo) {
                        espera1 += 200;
                        actualizarProgress(espera1);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(this::onContinuar); // Asegura que se ejecuta en el hilo principal
            }
        });
        timerThread.start();
    }

    public void actualizarProgress(final int timePassed) {
        if (nProgressBar != null) {
            final int progress = nProgressBar.getMax() * timePassed / TIMER_RUNTIME;
            nProgressBar.setProgress(progress);
        }
    }

    public void onContinuar() {
        Log.d("mensajeFinal", "Su barra de progreso acaba de cargar");
        if (nProgressBar != null) nProgressBar.setVisibility(View.GONE);
        if (contentLayout != null) contentLayout.setVisibility(View.VISIBLE);
    }
}

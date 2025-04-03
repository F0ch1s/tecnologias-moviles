package com.example.lab03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    String eti = "CicloDeVida";

    private ProgressBar progressBar;
    private static final int TIMER_RUNTIME = 3000;
    private boolean enCarga = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(eti, "En el evento onCreate()");

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pantalla Principal");

        // ProgressBar
        progressBar = findViewById(R.id.progressBar);
        simularCarga();

        // Botón a Segunda Actividad
        Button btnMostrar = findViewById(R.id.btnMostrar);
        btnMostrar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
            startActivity(intent);
        });

        // Botón a Conversor de Moneda
        Button btnConvertirMoneda = findViewById(R.id.btnConvertirMoneda);
        btnConvertirMoneda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConversorMonedaActivity.class);
            startActivity(intent);
        });
    }
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
        startActivity(intent);
    }

    private void simularCarga() {
        new Thread(() -> {
            enCarga = true;
            int progreso = 0;
            while (enCarga && progreso < TIMER_RUNTIME) {
                try {
                    Thread.sleep(200);
                    progreso += 200;
                    int finalProgreso = progreso;

                    runOnUiThread(() -> {
                        int percent = progressBar.getMax() * finalProgreso / TIMER_RUNTIME;
                        progressBar.setProgress(percent);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() -> {
                enCarga = false;
                Toast.makeText(MainActivity.this, "Carga completa", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // Ciclo de vida
    @Override public void onStart() { super.onStart(); Log.d(eti, "En el evento onStart()"); }
    @Override public void onRestart() { super.onRestart(); Log.d(eti, "En el evento onRestart()"); }
    @Override public void onResume() { super.onResume(); Log.d(eti, "En el evento onResume()"); }
    @Override public void onPause() { super.onPause(); Log.d(eti, "En el evento onPause()"); }
    @Override public void onStop() { super.onStop(); Log.d(eti, "En el evento onStop()"); }
    @Override public void onDestroy() { super.onDestroy(); Log.d(eti, "En el evento onDestroy()"); }
}

package com.example.lab03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ConversorMonedaActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;

    private EditText etMonto;
    private Spinner spnOrigen, spnDestino;
    private TextView tvResultado;

    private final String[] monedas = {
            "USD", "PEN", "EUR", "GBP", "INR", "BRL", "MXN", "CNY", "JPY"
    };

    private final double[][] tasas = {
            {1, 3.8, 0.92, 0.79, 83, 5.1, 17, 7.1, 150},
            {0.26, 1, 0.24, 0.21, 21.8, 1.3, 4.5, 1.8, 39.5},
            {1.09, 4.1, 1, 0.86, 90, 5.6, 18.5, 7.8, 163},
            {1.26, 4.9, 1.16, 1, 104, 6.5, 21.2, 9.2, 188},
            {0.012, 0.046, 0.011, 0.0096, 1, 0.062, 0.20, 0.088, 1.8},
            {0.20, 0.77, 0.18, 0.15, 16, 1, 3.3, 1.4, 29},
            {0.059, 0.22, 0.054, 0.047, 5.1, 0.30, 1, 0.43, 9.1},
            {0.14, 0.55, 0.13, 0.11, 11.3, 0.72, 2.3, 1, 21},
            {0.0067, 0.025, 0.0061, 0.0053, 0.56, 0.034, 0.11, 0.048, 1}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor_moneda);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ProgressBar y contenido
        progressBar = findViewById(R.id.progressBar);
        ScrollView contentLayout = findViewById(R.id.contenidoConversor);
        contentLayout.setVisibility(View.GONE); // Oculta contenido inicialmente

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            int id = item.getItemId();
            if (id == R.id.nav_main) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_segunda) {
                startActivity(new Intent(this, SegundaActividad.class));
            }
            return true;
        });

        // Simula carga durante 3 segundos
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
            inicializarComponentes();
        }, 3000);
    }

    private void inicializarComponentes() {
        etMonto = findViewById(R.id.etMonto);
        spnOrigen = findViewById(R.id.spnOrigen);
        spnDestino = findViewById(R.id.spnDestino);
        tvResultado = findViewById(R.id.tvResultado);

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monedas);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigen.setAdapter(adaptador);
        spnDestino.setAdapter(adaptador);

        Button btnConvertir = findViewById(R.id.btnConvertir);
        btnConvertir.setOnClickListener(v -> convertirMoneda());

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Moneda: " + monedas[position], Toast.LENGTH_SHORT).show());
    }

    private void convertirMoneda() {
        String montoStr = etMonto.getText().toString();
        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        int origen = spnOrigen.getSelectedItemPosition();
        int destino = spnDestino.getSelectedItemPosition();
        double monto = Double.parseDouble(montoStr);
        double resultado = monto * tasas[origen][destino];
        tvResultado.setText("Resultado: " + String.format("%.2f", resultado));
    }

    public class ImageAdapter extends BaseAdapter {
        private final Context contexto;
        private final Integer[] listaImgs = {
                R.drawable.usd, R.drawable.pen, R.drawable.eur,
                R.drawable.gbp, R.drawable.inr, R.drawable.brl,
                R.drawable.mxn, R.drawable.cny, R.drawable.jpy
        };

        public ImageAdapter(Context c) {
            contexto = c;
        }

        @Override
        public int getCount() {
            return listaImgs.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            ImageView img = new ImageView(contexto);
            img.setImageResource(listaImgs[pos]);
            img.setLayoutParams(new GridView.LayoutParams(250, 250));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return img;
        }
    }
}

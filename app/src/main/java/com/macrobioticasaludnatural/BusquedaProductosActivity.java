package com.macrobioticasaludnatural;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class BusquedaProductosActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.busqueda);
    }

    public void btnBuscarOnClick(View view)throws Exception
    {
        /*ArrayList<String> productos = getSintomasPresentes();
        if(sintomasPresentes.size() > 0)
        {
            ArrayList<String> diagnostico = requester.getEnfermedad(sintomasPresentes);
            Intent nextActivity = new Intent(this, DiagnosticoActivity.class);
            nextActivity.putExtra("enfermedades",diagnostico);
            startActivity(nextActivity);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Por favor seleccione al menos un sintoma presente antes de continuar",
                    Toast.LENGTH_SHORT).show();
        }*/
        EditText campoBusqueda = (EditText)findViewById(R.id.txbBuscar);
        String criterioBusqueda = campoBusqueda.getText().toString();
        Toast.makeText(getApplicationContext(), criterioBusqueda,
                Toast.LENGTH_SHORT).show();
    }
}

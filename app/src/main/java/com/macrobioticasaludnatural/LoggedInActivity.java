package com.macrobioticasaludnatural;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LoggedInActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.logged_in_menu);
    }

    public void btnBuscarProductoOnClick (View view)
    {
        //this.finish();
        startActivity(new Intent("com.macrobioticasaludnatural.BusquedaProductos"));
    }

    public void btnConsultaMedicaOnClick (View view)
    {
        //this.finish();
        startActivity(new Intent("com.macrobioticasaludnatural.ConsultaMedica"));
    }

    public void btnReservarCitaOnClick (View view)
    {
        //this.finish();
        startActivity(new Intent("com.macrobioticasaludnatural.ReservacionCita"));
    }

}

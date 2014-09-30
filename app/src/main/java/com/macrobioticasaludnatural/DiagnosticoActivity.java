package com.macrobioticasaludnatural;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.net.URLDecoder;
import java.util.ArrayList;

import Controller.Requester;

public class DiagnosticoActivity extends Activity{

    ArrayList<String> enfermedadesObtenidas;
    TableLayout tbEnfermedades;
    Requester requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar

        Bundle bundle = getIntent().getExtras();
        enfermedadesObtenidas = bundle.getStringArrayList("enfermedades");

        setContentView(R.layout.diagnostico);

        requester = Requester.getInstance();
        tbEnfermedades = (TableLayout)findViewById(R.id.tbEnfermedades);

        try {
            updateEnfermedades();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error al cargar enfermedades diagnosticadas",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEnfermedades ()throws Exception
    {
        TableRow auxRow;
        TextView auxSintoma;
        EspecialButton auxButton;

        for (String enfermedad : enfermedadesObtenidas)
        {
            auxRow = new TableRow(this);
            auxSintoma = new TextView(this);
            auxButton = new EspecialButton(this,enfermedad);

            auxButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    EspecialButton button = (EspecialButton)v;
                    TableLayout tbTratamiento = (TableLayout)findViewById(R.id.tbTratamiento);

                    //clean table tratamientos
                    for(int count = tbTratamiento.getChildCount()-1; count >= 0; count--)
                    {
                        System.out.println(count);
                        tbTratamiento.removeViewAt(count);
                    }

                    try {
                        ArrayList<ArrayList<String>> tratamiento = requester.getTratamiento(button.getOwner());
                        TableRow auxRow;
                        TextView auxId;
                        TextView auxNombre;
                        EspecialButton btnVer;
                        for (ArrayList<String> producto : tratamiento)
                        {
                            auxRow = new TableRow(button.getContext());
                            auxId = new TextView(button.getContext());
                            auxNombre = new TextView(button.getContext());
                            btnVer = new EspecialButton(button.getContext(),producto.get(0));

                            auxId.setText(producto.get(0));
                            auxNombre.setText(producto.get(1));
                            auxRow.addView(auxId);
                            auxRow.addView(auxNombre);
                            auxRow.addView(btnVer);

                            tbTratamiento.addView(auxRow);
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Error al cargar el tratamiento",
                                Toast.LENGTH_SHORT).show();
                    }
                }});

            auxSintoma.setText(enfermedad);
            auxRow.addView(auxSintoma);
            auxRow.addView(auxButton);

            tbEnfermedades.addView(auxRow);
        }
    }
}

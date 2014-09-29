package com.macrobioticasaludnatural;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import Controller.Requester;
import DataBase.Connection;

import java.util.ArrayList;

public class ConsultaMedicaActivity extends Activity{

    private TableLayout tbSintomas;
    private Requester requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.consulta_medica);

        requester = Requester.getInstance();
        tbSintomas = (TableLayout)findViewById(R.id.tbSintomas);

        try {
            updateSintomas();
        }
        catch (Exception e){
            System.out.println("Error al actualizar la lista de sintomas");
        }
    }


    private void updateSintomas ()throws Exception
    {
        ArrayList<String> sintomas;
        TableRow auxRow;
        TextView auxSintoma;
        CheckBox isPresente;

        sintomas = requester.getSintomas();


        for (String sintoma : sintomas)
        {
            auxRow = new TableRow(this);
            auxSintoma = new TextView(this);
            isPresente = new CheckBox(this);

            auxSintoma.setText(sintoma);
            auxRow.addView(auxSintoma);
            auxRow.addView(isPresente);

            tbSintomas.addView(auxRow);
        }
    }

    private void cleanTable()
    {
        for(int count = tbSintomas.getChildCount()-1; count >= 0; count--)
        {
            System.out.println(count);
            tbSintomas.removeViewAt(count);
        }

    }

    private ArrayList<String> getSintomasPresentes()
    {
        ArrayList<String> sintomasPresentes = new ArrayList<String>();
        TableRow auxRow = null;
        TextView auxTex = null;
        CheckBox auxCheck = null;

        for(int index = 0; index < tbSintomas.getChildCount(); index ++)
        {
            auxRow = (TableRow)tbSintomas.getChildAt(index);
            auxCheck = (CheckBox) auxRow.getChildAt(1);
            if(auxCheck.isChecked())
            {
                auxTex = (TextView) auxRow.getChildAt(0);
                sintomasPresentes.add(auxTex.getText().toString());
            }
        }

        return  sintomasPresentes;
    }

    public void btnConsultaOnClick(View view)throws Exception
    {
        ArrayList<String> sintomasPresentes = getSintomasPresentes();
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
        }
    }


}

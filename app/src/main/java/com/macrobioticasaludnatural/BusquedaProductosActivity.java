package com.macrobioticasaludnatural;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.Requester;

public class BusquedaProductosActivity extends Activity{

    Requester requester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.busqueda);

        requester = Requester.getInstance();
    }

    private void cleanTable(TableLayout pTable)
    {
        for(int count = pTable.getChildCount()-1; count >= 0; count--)
        {
            System.out.println(count);
            pTable.removeViewAt(count);
        }

    }
    public void btnBuscarOnClick(View view)throws Exception
    {
        TableRow auxRow;
        TextView auxText;
        EspecialButton ver;
        TableLayout auxTable = (TableLayout)findViewById(R.id.tbProductosBusqueda);
        EditText campoBusqueda = (EditText)findViewById(R.id.txbBuscar);
        String criterioBusqueda = campoBusqueda.getText().toString();

        ArrayList<ArrayList<String>> resultado = requester.getResultadosBusqueda(criterioBusqueda);

        cleanTable(auxTable);

        for (ArrayList<String> producto : resultado)
        {
            auxRow = new TableRow(this);

            auxText = new TextView(this);  // add ID
            auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,getResources().getDisplayMetrics()));
            auxText.setGravity(Gravity.CENTER);
            auxText.setText(producto.get(0));
            auxRow.addView(auxText);

            auxText = new TextView(this); //add nombre
            auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,180,getResources().getDisplayMetrics()));
            auxText.setGravity(Gravity.CENTER);
            auxText.setText(producto.get(1));
            auxRow.addView(auxText);

            auxText = new TextView(this); //add cantidad
            auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics()));
            auxText.setGravity(Gravity.CENTER);
            auxText.setText(producto.get(2));
            auxRow.addView(auxText);

            ver = new EspecialButton(this,producto.get(0));
            ver.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15,getResources().getDisplayMetrics()));
            ver.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15,getResources().getDisplayMetrics()));
            ver.setGravity(Gravity.CENTER);

            //add evento
            ver.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    EspecialButton button = (EspecialButton)v;
                    Intent nextActivity = new Intent(v.getContext(), DetalleProductoActivity.class);
                    nextActivity.putExtra("id",button.getOwner());
                    startActivity(nextActivity);
                }});

            auxRow.addView(ver); //add button

            auxTable.addView(auxRow);
        }


    }
}

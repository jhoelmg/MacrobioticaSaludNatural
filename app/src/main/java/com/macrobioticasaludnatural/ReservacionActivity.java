package com.macrobioticasaludnatural;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.Requester;
import Controller.Usuario;

public class ReservacionActivity extends Activity{

    private Usuario usuario;
    private ArrayList<ArrayList<String>> productos;
    private Requester requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.reserva);

        usuario = Usuario.getInstance();
        requester = Requester.getInstance();
        productos = new ArrayList<ArrayList<String>>();

        try{
            updateProductos();
            updateTable();
            updateSucursales();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error al cargar los productos",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProductos() throws Exception
    {
        productos = requester.getResultadosBusqueda("all");
    }

    private void cleanTable(TableLayout pTable)
    {
        for(int count = pTable.getChildCount()-1; count >= 0; count--)
        {
            System.out.println(count);
            pTable.removeViewAt(count);
        }
    }

    private boolean containProduct (ArrayList<String> pArray, String pElemento)
    {
        boolean contain = false;
        for(String elemento : pArray)
        {
            if(elemento.equals(pElemento))
            {
                contain = true;
                break;
            }
        }
        return contain;
    }
    
    private int getProductoCantidad(String pElemento)
    {
        int cantidad = 0;
        for(String elemento : usuario.getReservas())
        {
            if(elemento.equals(pElemento))
            {
                cantidad++;
            }
        }
        return cantidad;
    }

    private void updateSucursales()throws Exception
    {
        ArrayList<ArrayList<String>> sucursales = requester.getSucursales();
        String auxSucursal;
        ArrayList<String> auxSucursales = new ArrayList<String>();
        for (ArrayList<String> sucursal : sucursales)
        {
            auxSucursal = sucursal.get(0)+","+sucursal.get(2);
            auxSucursales.add(auxSucursal);
        }

        Spinner auxSpinner = (Spinner)findViewById(R.id.spSucursalesReserva);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,auxSucursales);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auxSpinner.setAdapter(adp);
    }

    private void updateTable()
    {
        ArrayList<String> productosMostrados = new ArrayList<String>();
        TableLayout auxTable = (TableLayout)findViewById(R.id.tbProductosReservados);

        TableRow auxRow = new TableRow(this);
        TextView auxText;
        EspecialButton ver;

        cleanTable(auxTable);

        for (ArrayList<String> producto : productos) {
            if((containProduct(usuario.getReservas(), producto.get(0))) && !(containProduct(productosMostrados,producto.get(0))))
            {
                productosMostrados.add(producto.get(0)); //agregar producto ya desplegado
                auxRow = new TableRow(this);

                auxText = new TextView(this);  // add ID
                auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
                auxText.setGravity(Gravity.CENTER);
                auxText.setText(producto.get(0));
                auxRow.addView(auxText);

                auxText = new TextView(this); //add nombre
                auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics()));
                auxText.setGravity(Gravity.CENTER);
                auxText.setText(producto.get(1));
                auxRow.addView(auxText);

                auxText = new TextView(this); //add cantidad
                auxText.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
                auxText.setGravity(Gravity.CENTER);
                auxText.setText(Integer.toString(getProductoCantidad(producto.get(0))));
                auxRow.addView(auxText);

                ver = new EspecialButton(this, producto.get(0));
                ver.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
                ver.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
                ver.setGravity(Gravity.CENTER);

                //add evento
                ver.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        EspecialButton button = (EspecialButton)v;
                        ArrayList<String> auxArray = usuario.getReservas();
                        for (int index = 0; index < auxArray.size(); index++)
                        {
                            if(auxArray.get(index).equals(button.getOwner()))
                            {
                                auxArray.remove(index);
                                Toast.makeText(getApplicationContext(), "Producto eliminado de la reservacion",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                    }});

                auxRow.addView(ver); //add button

                auxTable.addView(auxRow);
            }
        }

    }

    public String getSelectedSucursal()
    {
        Spinner auxSpinner = (Spinner)findViewById(R.id.spSucursalesReserva);
        String aux = auxSpinner.getSelectedItem().toString();
        String idSucursal = aux.replaceAll("\\,.*","");
        return idSucursal;

    }
    public void btnReservarProductosOnClick (View view)
    {
        String selectedSucursal = getSelectedSucursal();


    }
}

package com.macrobioticasaludnatural;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.Requester;
import Controller.Usuario;

public class DetalleProductoActivity extends Activity{

    private String idProducto;
    private Requester requester;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar

        Bundle bundle = getIntent().getExtras();
        idProducto = bundle.getString("id");

        setContentView(R.layout.detalle_producto);

        requester = Requester.getInstance();
        usuario = Usuario.getInstance();

        try
        {
            updateInformacion();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), R.string.error_cargar_productos,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateInformacion () throws Exception
    {
        TextView auxText;
        ImageView auxImage;
        Bitmap image;

        ArrayList<String> productoInfo = requester.getProducto(idProducto);
        auxText = (TextView)findViewById(R.id.tvNombreProducto);
        auxText.setText(productoInfo.get(0));
        auxText = (TextView)findViewById(R.id.tvDescripcionProducto);
        auxText.setText(productoInfo.get(1));
        auxText = (TextView)findViewById(R.id.tvPrecioProducto);
        auxText.setText(productoInfo.get(2)+" colones");

        auxImage = (ImageView)findViewById(R.id.ivImagenProducto);
        image = requester.getImage(productoInfo.get(3));
        auxImage.setImageBitmap(image);
    }

    public void btnReservarProductoOnClick (View view)
    {
        if(usuario.getIdUsuario() != null) {
            usuario.getReservas().add(idProducto);
            Toast.makeText(getApplicationContext(), R.string.agregado_carretillo,
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.error_reservar_producto_facebook,
                    Toast.LENGTH_SHORT).show();
        }
    }
}

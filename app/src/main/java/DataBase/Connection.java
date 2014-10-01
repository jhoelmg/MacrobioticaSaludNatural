package DataBase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.*;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.widget.Toast;

public class Connection {
    private static Connection connection;

    private Connection(){};

    public static Connection getInstance()
    {
        if(connection == null)
        {
            connection = new Connection();
        }
        return connection;
    }

    public JSONObject getObject(String pUrl) throws Exception
    {
        JSONObject obj = new AsyncGetRequest(pUrl).execute().get();
        return obj;
    }

    public Bitmap getImage(String imageURL) throws Exception
    {
        Bitmap image = new AsyncDownloadImage(imageURL).execute().get();
        return image;
    }


    public static void post() throws Exception{
        //String urlParameters = "entregado=0&pacienteId=1&sucursalId=1";
        //String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=7";

        String urlParameters = "productoId=1&cantidad=4";
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=8";

        URL url = new URL(request);
        URLConnection conn = url.openConnection();

        conn.setDoOutput(true);

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        writer.write(urlParameters);
        writer.flush();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((line = reader.readLine()) != null) {
            System.out.println(line);

            JSONObject obj = new JSONObject(line);
            int estado = obj.getInt("estado");
            String info = obj.getString("info");

            System.out.println(estado);
            System.out.println(info);
        }
        writer.close();
        reader.close();
    }

    public void setProducto(){
        ArrayList<ArrayList<String>> productosReservar = new ArrayList<ArrayList<String>>();
        new AsyncSetReservacion(productosReservar).execute();
    }


    //Async Tasks To get things from internet


    private class AsyncSetReservacion extends AsyncTask<Void, Void, Void>{

        ArrayList<ArrayList<String>> productosReservar;



        public AsyncSetReservacion(ArrayList<ArrayList<String>> pProductosReservar) {
            productosReservar = pProductosReservar;
        }

        @Override
        public Void doInBackground(Void... params) {

            try {

                String urlParameters = "entregado=0&pacienteId=1&sucursalId=1";
                String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=8";

                URL url = new URL(request);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(urlParameters);
                writer.flush();

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);

                    JSONObject obj = new JSONObject(line);
                    int estado = obj.getInt("estado");
                    String info = obj.getString("info");

                    System.out.println(estado);
                    System.out.println(info);
                }
                writer.close();
                reader.close();
            }
            catch(Exception e){
                System.out.println("ERROR AL CARGAR PRODUCTO");
            }

            return null;
        }

    }

    private class AsyncGetRequest extends AsyncTask<Void, Void, JSONObject> {

        private String urlDirection;
        public AsyncGetRequest(String pUrlDirection) {
            urlDirection = pUrlDirection;
        }

        @Override
        public JSONObject doInBackground(Void... params) {
            // do work here
            URL url;
            URLConnection conn;
            BufferedReader reader;
            JSONObject obj = null;
            try {
                url = new URL(urlDirection);
                conn = url.openConnection();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;

                while((line = reader.readLine()) != null)
                {
                    System.out.println(line);
                    obj = new JSONObject(line);
                }
                reader.close();
                return obj;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    private class AsyncDownloadImage extends AsyncTask<Void, Void, Bitmap> {

        private String urlDirection;
        public AsyncDownloadImage(String pUrlDirection) {
            urlDirection = pUrlDirection;
        }

        @Override
        public Bitmap doInBackground(Void... params) {
            // do work here
            Bitmap image = null;
            InputStream in;
            try {
                in = new java.net.URL(urlDirection).openStream();
                image = BitmapFactory.decodeStream(in);
                return image;
            }
            catch(Exception e) {
                return null;
            }
        }
    }
}

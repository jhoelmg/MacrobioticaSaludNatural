package DataBase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.*;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.graphics.Bitmap;

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


    public Bitmap get() throws Exception{
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=3"
                + "&producto=all";

        JSONObject obj = new AsyncGetRequest(request).execute().get();
        String imageURL = "";
        Bitmap image = null;

        int estado = obj.getInt("estado");
        System.out.println(estado);
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                int producto = info.getJSONObject(i).getInt("idProducto");
                String nombre = info.getJSONObject(i).getString("nombre");
                String descripcion = info.getJSONObject(i).getString("descripcion");
                double precio = info.getJSONObject(i).getDouble("precio");
                imageURL = info.getJSONObject(i).getString("imagen");

                System.out.println("ID producto : " + producto);
                System.out.println("Nombre : " + nombre);
                System.out.println("Descripción : " + descripcion);
                System.out.println("Precio : " + precio);
                System.out.println("URL imagen : " + imageURL);
            }

            image = new AsyncDownloadImage(imageURL).execute().get();

            if(image == null) {
                System.out.println("ERROOOOOOOOOOOOOOOOOOOOOOOOOOOR");
                return null;
            }
            else {
                System.out.println("I DID ITTTTTTTTTTTTTTT!!!");
                return image;
            }
        }else{
            String info = obj.getString("info");
            System.out.println(info);
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

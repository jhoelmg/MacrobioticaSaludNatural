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

    public Bitmap getImage(String imageURL) throws Exception
    {
        Bitmap image = new AsyncDownloadImage(imageURL).execute().get();
        return image;
    }



    //Async Tasks To get things from internet

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

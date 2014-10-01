package Controller;


import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import DataBase.Connection;

public class Requester {

    private static Requester requester;
    private static Connection connection;

    private Requester()
    {
        connection = Connection.getInstance();
    }

    public static Requester getInstance()
    {
        if(requester == null)
        {
            requester = new Requester();
        }
        return requester;
    }

    public ArrayList<String> getSintomas() throws Exception
    {
        String nombre;
        ArrayList<String> listaSintomas = new ArrayList<String>();
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=1"
                +"&sintoma=all";
        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        System.out.println(estado);
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                nombre = info.getJSONObject(i).getString("nombre");
                listaSintomas.add(nombre);
            }
            return listaSintomas;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }

    public ArrayList<String> getEnfermedad(ArrayList<String> pSintomas) throws Exception
    {
        String auxSintoma = null;
        String nombre = null;
        ArrayList<String> listaEnfermedades = new ArrayList<String>();
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=2"
                +"&enfermedad=";


        for(int index = 0; index < pSintomas.size(); index++)
        {
            auxSintoma = pSintomas.get(index).replace(" ","+");
            //Arreglo temporal para obtener las palabras separadas por el +
            String[] tmpArray = auxSintoma.split("\\+");
            for (int i = 0; i < tmpArray.length; i++) {
                if (i == tmpArray.length - 1)
                    request += URLEncoder.encode(new String(tmpArray[i]), "utf-8");
                else
                    request += URLEncoder.encode(new String(tmpArray[i]), "utf-8") + "+";
            }

            if (index < pSintomas.size()-1)
                request += ",";
        }

        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                nombre = info.getJSONObject(i).getString("nombre");
                listaEnfermedades.add(nombre);
            }
            return listaEnfermedades;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getTratamiento(String pEnfermedad) throws Exception
    {
        String auxEnfermedad = null;
        String nombre = null;
        String id = null;
        ArrayList<ArrayList<String>> tratamiento = new ArrayList<ArrayList<String>>();
        ArrayList<String> producto;
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=3" +
                "&tratamiento=";

        auxEnfermedad = pEnfermedad.replace(" ", "+");
        //Arreglo temporal para obtener las palabras separadas por el +
        String[] tmpArray = auxEnfermedad.split("\\+");
        for (int i = 0; i < tmpArray.length; i++) {
            if (i == tmpArray.length - 1)
                request += URLEncoder.encode(new String(tmpArray[i]), "utf-8");
            else
                request += URLEncoder.encode(new String(tmpArray[i]), "utf-8") + "+";
        }

        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                id = info.getJSONObject(i).getString("idProducto");
                nombre = info.getJSONObject(i).getString("nombre");
                producto = new ArrayList<String>();
                producto.add(id);
                producto.add(nombre);
                tratamiento.add(producto);
            }
            return tratamiento;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getSucursales() throws Exception
    {
        String id = null;
        String provincia = null;
        String canton = null;

        ArrayList<ArrayList<String>> sucursales = new ArrayList<ArrayList<String>>();
        ArrayList<String> sucursal;
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=5" +
                "&sucursal=all";

        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                id = info.getJSONObject(i).getString("codigo");
                provincia = info.getJSONObject(i).getString("provincia");
                canton = info.getJSONObject(i).getString("canton");
                sucursal = new ArrayList<String>();
                sucursal.add(id);
                sucursal.add(provincia);
                sucursal.add(canton);
                sucursales.add(sucursal);
            }
            return sucursales;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }


    public void reservarProductos() throws Exception
    {
        connection.setProducto();
    }

    public ArrayList<String> getProducto(String pIdProducto) throws Exception
    {
        String nombre;
        String descripcion;
        String precio;
        String imageURL;

        ArrayList<String> producto = new ArrayList<String>();
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=4" +
                "&producto="+pIdProducto;

        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                nombre = info.getJSONObject(i).getString("nombre");
                descripcion = info.getJSONObject(i).getString("descripcion");
                precio = info.getJSONObject(i).getString("precio");
                imageURL = info.getJSONObject(i).getString("imagen");
                producto = new ArrayList<String>();
                producto.add(nombre);
                producto.add(descripcion);
                producto.add(precio);
                producto.add(imageURL);
            }
            return producto;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }

    public Bitmap getImage (String pImageURL) throws Exception
    {
        Bitmap image =  connection.getImage(pImageURL);
        return image;
    }

    public ArrayList<ArrayList<String>> getResultadosBusqueda (String pCriterio) throws Exception
    {
        String id;
        String nombre;
        String cantidad;

        ArrayList<ArrayList<String>> resultados = new ArrayList<ArrayList<String>>();
        ArrayList<String> producto = new ArrayList<String>();
        String request = "http://macrobioticasaludnatural.uphero.com/WebService/main.php?consulta=4" +
                "&producto="+pCriterio;

        JSONObject obj = connection.getObject(request);

        int estado = obj.getInt("estado");
        if(estado == 1){
            JSONArray info = obj.getJSONArray("info");
            for (int i = 0; i < info.length(); i++){
                id = info.getJSONObject(i).getString("idProducto");
                nombre = info.getJSONObject(i).getString("nombre");
                cantidad = "20";//info.getJSONObject(i).getString("precio");
                producto = new ArrayList<String>();
                producto.add(id);
                producto.add(nombre);
                producto.add(cantidad);
                resultados.add(producto);
            }
            return resultados;
        }else{
            String info = obj.getString("info");
            System.out.println(info);
            return null;
        }
    }
}

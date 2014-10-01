package Controller;

import java.util.ArrayList;

public class Usuario {

    private static Usuario usuario;
    private String idUsuario;
    private ArrayList<String> reservas;

    private Usuario ()
    {
        idUsuario = null;//"11111";
        reservas = new ArrayList<String>();
    }

    public static Usuario getInstance()
    {
        if(usuario == null)
        {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setIdUsuario(String pId)
    {
        idUsuario = pId;
    }

    public ArrayList<String> getReservas()
    {
        return reservas;
    }

    public String getIdUsuario()
    {
        return idUsuario;
    }
}

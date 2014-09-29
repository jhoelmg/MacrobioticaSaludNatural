package com.macrobioticasaludnatural;


import android.content.Context;
import android.widget.Button;

public class EspecialButton extends Button{

    private String owner;
    public EspecialButton (Context context,String pOwner)
    {
        super(context);
        owner = pOwner;
    }

    public String getOwner ()
    {
        return owner;
    }
}

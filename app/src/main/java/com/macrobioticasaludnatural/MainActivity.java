package com.macrobioticasaludnatural;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;

import Controller.Usuario;


public class MainActivity extends ActionBarActivity {

    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //No Status Bar
        setContentView(R.layout.main_menu);
        usuario = Usuario.getInstance();
        /*Session.openActiveSession(this, true, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                System.out.println("Bievenido " + user.getId() + "!");
                            }
                        }
                    }).executeAsync();
                }
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        if(isLoggedIn()) {
            startActivity(new Intent("com.macrobioticasaludnatural.LoggedInMenu"));
            /*Session.openActiveSession(this, true, new Session.StatusCallback() {

            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                usuario.setIdUsuario(user.getId());
                            }
                        }
                    }).executeAsync();
                }
            }
        });*/

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnBuscarProductoOnClick (View view)
    {
        //this.finish();
        startActivity(new Intent("com.macrobioticasaludnatural.BusquedaProductos"));
    }

    public boolean isLoggedIn() {
        Session session = Session.getActiveSession();
        return (session != null && session.isOpened());
    }

    private void publishFeedDialog() {
        Bundle params = new Bundle();
        params.putString("Name", "Producto Macrobiótica Salud Natural");
        params.putString("caption", "Comparte con tus amigos nuestros productos al mejor precio.");
        params.putString("description", "Con Macrobiótica Salud Natural siempre tendrás al alcance todos los productos que tú y tu familia necesitan.");
        //params.putString("link", "http://macrobioticasaludnatural.uphero.com");
        //params.putString("picture", "http://macrobioticasaludnatural.uphero.com/images/calamina.png");

        WebDialog feedDialog = (
                new WebDialog.FeedDialogBuilder(MainActivity.this,
                        Session.getActiveSession(),
                        params)).build();
        feedDialog.show();
    }
}

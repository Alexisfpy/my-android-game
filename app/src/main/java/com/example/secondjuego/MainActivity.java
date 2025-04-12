package com.example.secondjuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.secondjuego.Pantallas.PantallaJuego;
import com.example.secondjuego.Pantallas.PantallaMenu;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * Enlazamos el surfaceview del menú principal
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Juego pantalla = new Juego(this);
        pantalla.setKeepScreenOn(true);
        setContentView(pantalla);


        //En el manifest por si peta ->>>    android:usesCleartextTraffic="true"
    }

    /**
     * Control de versiones de android
     * Pantalla completa y se olculta la barra de navegación actionbar
     */
    @Override
    protected void onResume() {
        super.onResume();
        // versiones iguales o superiores a Jelly Bean
        final int flags= View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Oculta la barra de navegación
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Oculta la barra de navegación
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(flags);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(flags);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).hide(); // se oculta la barra de ActionBar
    }

    /**
     * En el estado de stop de la aplicaión , paramos la música
     */
    @Override
    protected void onStop() { // La actividad ya no es visible y ahora está "parada"
        super.onStop();
        if (PantallaJuego.mediaPlayerMusic !=null){
            PantallaJuego.mediaPlayerMusic.pause();
        }

        if (PantallaMenu.mPMenu !=null){
            PantallaMenu.mPMenu.pause();
        }


    }

}
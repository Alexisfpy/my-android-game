package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;


public class Pantalla {
    /**
     * Control de la pantalla
     */
    public int numEscena;
    /**
     * Contexto del juego
     */
    Context context;
    /**
     * Ancho de la pantalla  móvil
     */
    public static int anchoPantalla;

    /**
     * Alto de la pantalla  móvil
     */
    public static int altoPantalla;

    /**
     * Inicio de la pantalla prinicipal
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public Pantalla(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        this.numEscena = numEscena;
        this.context = context;
        Pantalla.anchoPantalla = anchoPantalla;
        Pantalla.altoPantalla = altoPantalla;
    }

    /**
     * Dibujar se implementa las demás pantallas
     * @param c
     */
    public void dibujar(Canvas c) {

    }

    /**
     * Actualizar se implememta en las demás pantallas
     */
    public void actualizarFisica() {

    }

    /**
     * Control del cambio de pantalla
     * @param event
     * @return número de escena
     */
    public int onTouch(MotionEvent event) {

        return numEscena;
    }
}

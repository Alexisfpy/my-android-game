package com.example.secondjuego;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Fondo {
    /**
     * Posicion del fondo
     */
    public PointF posicion;
    /**
     * Bitmap de imagen del fondo
     */
    public Bitmap imagen;

    private int aceleracionFondo=0;

    /**
     * Inicializamos la imagen y el punto x con y=0
     * @param imagen
     * @param  x
     */
    public Fondo(Bitmap imagen, float x) { // Constructores
        this.imagen = imagen;
        this.posicion = new PointF(x, 0);
    }

    /**
     * Bitman para el fondo
     * @param  imagen
     */
    public Fondo(Bitmap imagen) {

        this(imagen, 0);
    }

    /**
     * Gestión de la velocidad del fondo al moverse
     * @param velocidad
     */
    public void mover(int velocidad) { // Desplazamiento

        posicion.x += velocidad;
    }

    /**
     * Dependiendo de cuanto avance el progreso del juego, aumenta la velocidad del fondo
     * @return aceleracionFondo
     */
    public int aceleracionFondo(){
        if (Vida.progeso%1500==0){
            aceleracionFondo--;
        }
        return aceleracionFondo;
    }

}

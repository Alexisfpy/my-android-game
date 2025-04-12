package com.example.secondjuego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

public class Vida {

    /**
     * Muestra la barra de vida que nos queda
     */
    public int numVidas;
    /**
     * Ancho de la Pantalla móvil
     */
    int anchoPantalla;
    /**
     * Alto de la Pantalla móvil
     */
    int altoPantalla;
    /**
     * En un futuro este atributo si el progreso en metros del mapa tenga un límite y pase
     * al siguiente
     */
    public int win;
    /**
     * Los metros o la distancia que lleva el jugador en el juego
     */
    public static int progeso;
    /**
     * Para pintar y mostrar tanto el progreso como numvidas
     */
    private final Paint p;
    /**
     * Contexto del juego
     */
    Context context;
    /**
     * Tipo de fuente
     */
    Typeface type;
    /**
     * Nombre extraído del archivo string
     */
    String appVida;


    /**
     * Controlaremos lo valores iniciales de vida e incializamos el paint
     * Inicializamos también nuestro tipo de fuente para el texto
     * @param numVidas
     * @param win
     * @param anchoPantalla
     * @param altoPantalla
     * @param context
     */
    public Vida (int numVidas, int win, int anchoPantalla, int altoPantalla, Context context){
        this.numVidas = numVidas;
        this.win=win;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        progeso =0;

        appVida =context.getString(R.string.app_vida);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.patrik);
        p= new Paint();
        p.setTypeface(typeface);
        p.setColor(Color.WHITE);
        p.setTextSize((float) altoPantalla /20);
        p.setTextAlign(Paint.Align.CENTER);
        type = ResourcesCompat.getFont(context, R.font.patrik);

    }

    /**
     * Dibujamos nuestro progreso en metros y la cantidad de vida que nos queda después de las
     * colisiones
     * @param c
     */
    public void dibujar (Canvas c){
        c.drawText(appVida+" :"+ numVidas, (float) (anchoPantalla * 8) /10, (float) altoPantalla /8,p);
        c.drawText(progeso +" m", (float) anchoPantalla /3, (float) altoPantalla /8,p);
    }

}

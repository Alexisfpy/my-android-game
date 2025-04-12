package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import androidx.core.content.res.ResourcesCompat;

import com.example.secondjuego.Constantes;
//import com.example.secondjuego.R;

public class PantallaRecords extends Pantalla {
    /**
     * Control de la pantalla
     */
    int numEscena;

    /**
     * Contexto del juego
     */
    Context context;
    /**
     * Ancho de la pantalla  móvil
     */
    int anchoPantalla;
    /**
     * Alto de la pantalla  móvil
     */
    int altoPantalla;
    /**
     * Paint de récords
     */
    private Paint p;
    /**
     * Imagen del fondo récords
     */
    Bitmap bitmapMenu;
    /**
     * imagen del botón back
     */
    Bitmap bitmapBack;
    /**
     * Control del rect del back
     */
    Rect gameOver;

    /**
     * Imagen texto record
     */
    Bitmap bitRecord;

    /**
     * Inicializamos tanto el paint como las imágenes y el rect del back
     * En el paint implementamos la fuente no nativa
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaRecords(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        Typeface typeface = ResourcesCompat.getFont(context, com.example.secondjuego.R.font.patrik);
        p= new Paint();
        p.setTypeface(typeface);
        p.setColor(Color.WHITE);
        p.setTextSize(altoPantalla/6);
        p.setTextAlign(Paint.Align.CENTER);
        bitmapMenu=escala(com.example.secondjuego.R.drawable.fondomenu2,anchoPantalla,altoPantalla);
        bitmapBack = escala(com.example.secondjuego.R.drawable.backdef,anchoPantalla/6, altoPantalla/7);

        gameOver = new Rect(0,altoPantalla-altoPantalla/6,anchoPantalla/5, altoPantalla);
        bitRecord= escala(com.example.secondjuego.R.drawable.recorddef, anchoPantalla/3, altoPantalla/3);

    }

    /**
     * Escalado de cualquier imagen introducida
     * @param res
     * @param nuevoAncho
     * @param nuevoAlto
     * @return bitmap con las dimensiones puestas acorde al ancho y alto pantalla
     */
    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto){
        Bitmap bitmapAux= BitmapFactory.decodeResource(context.getResources(), res);
        return bitmapAux.createScaledBitmap(bitmapAux,nuevoAncho, nuevoAlto, true);
    }

    /**
     * Dibujamos el botón back para volver al menú principal y el récord más alto obtenido en todo
     * el juego hasta el momento
     * @param c
     */
    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(bitmapMenu, 0, 0, null);
        c.drawBitmap(bitRecord, anchoPantalla/3, altoPantalla/5,null);
        c.drawText(String.valueOf(cargarPreferencias()), anchoPantalla/2, altoPantalla-altoPantalla/3,p);
        c.drawBitmap(bitmapBack,0,altoPantalla-altoPantalla/6,null);
    }

    /**
     * Al tocar al rect de back vuelve al menú principal
     * @param event
     * @return pantalla constante
     */
    public int onTouch (MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (gameOver.contains(x, y)) {
                if (PantallaMenu.mPMenu !=null){
                    PantallaMenu.mPMenu.stop();
                }
                return Constantes.PMENU;
            }
        }
        return 18;
    }

    /**
     * Recuperar el récord más alto guardado
     * @return número long más alto guardado
     */
    public long  cargarPreferencias(){
        SharedPreferences preferences = context.getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        long progresoRecord = preferences.getInt("Record",0);
        return progresoRecord;
    }
}

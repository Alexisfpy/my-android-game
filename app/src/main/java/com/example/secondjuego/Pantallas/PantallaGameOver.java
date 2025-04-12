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
import com.example.secondjuego.Vida;

public class PantallaGameOver extends  Pantalla{
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
     * Imagen del fondo de la pnatalla
     */
    Bitmap bitmapFondo;
    /**
     * Imagen game over
     */
    Bitmap bitmapGOver;
    /**
     * imagen del botón back
     */
    Bitmap bitmapBack;
    /**
     * Paint de game over
     */
    Paint pGOver;
    /**
     * Control del rect del botón back
     */
    Rect gameOver;

    /**
     * Inicializamos las imágenes, tanto el fondo como el del botón back
     * El rect del botón back y el paint
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaGameOver(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        //Fondo
        bitmapFondo=escala(com.example.secondjuego.R.drawable.fondo2,anchoPantalla, altoPantalla);
        //bitmapGOver = BitmapFactory.decodeResource(context.getResources(),R.drawable.gameover);
        bitmapGOver = escala(com.example.secondjuego.R.drawable.gameover, anchoPantalla/3, altoPantalla/5);
        bitmapBack = escala(com.example.secondjuego.R.drawable.backdef,anchoPantalla/6, altoPantalla/7);

        Typeface typeface = ResourcesCompat.getFont(context, com.example.secondjuego.R.font.patrik);
        pGOver= new Paint();
        pGOver.setTypeface(typeface);
        pGOver.setColor(Color.WHITE);
        pGOver.setTextSize((float) altoPantalla /5);
        pGOver.setTextAlign(Paint.Align.CENTER);
        gameOver = new Rect(0,altoPantalla-altoPantalla/6,anchoPantalla/5, altoPantalla);


    }

    /**
     * Escalado de cualquier imagen introducida
     * @return bitmap con las dimensiones puestas acorde al ancho y alto pantalla
     * @param res
     * @param nuevoAncho
     * @param nuevoAlto
     */
    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto){
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        return Bitmap.createScaledBitmap(bitmapAux,nuevoAncho, nuevoAlto, true);
    }

    /**
     * Dibujamos el botón para volver al menú principal, el fondo de pantalla, el texto game over con
     * la puntuación obtenida en el juego
     * @param c
     */
    @Override
    public void dibujar(Canvas c) {
        //c.drawColor(Color.MAGENTA);
        c.drawBitmap(bitmapFondo,0,0,null);
        c.drawBitmap(bitmapGOver, (float) anchoPantalla /3, (float) altoPantalla /4,null);
        c.drawBitmap(bitmapBack,0,altoPantalla- (float) altoPantalla /6,null);
        c.drawText(String.valueOf(Vida.progeso), (float) anchoPantalla /2,
                altoPantalla- (float) altoPantalla /4,pGOver);
        //c.drawRect(gameOver, pGOver);
    }
    /**
     * Al tocar al rect de back vuelve al menú principal y llama a la función y comprueba si la
     * puntuación obtenida en el juego es mayor o igual a la puntuación máxima guardada
     * @param  event
     * @return Constantes.PMENU
     */
    public int onTouch (MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (gameOver.contains(x, y)) {
                guardarPreferencias();
                return Constantes.PMENU;
            }
        }
    return 18;
    }

    /**
     * Parámetro preferencias para crear las credenciales
     * editor para poder editarlo
     * progresoRecord, extraemos el récord más alto y comparamos con la puntuación obtenida en el juego
     * Si la puntuación obtenida en el juego es mayor o igual, se guarda, sino se descarta
     */
    public void guardarPreferencias(){
        SharedPreferences preferences= context.getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        long progresoRecord = preferences.getInt("Record",0);
        if((long) Vida.progeso >=progresoRecord){
            editor.putInt("Record", Vida.progeso);
        }
        editor.apply();
    }


}

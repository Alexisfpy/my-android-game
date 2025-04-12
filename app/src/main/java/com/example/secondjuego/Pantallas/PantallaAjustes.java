package com.example.secondjuego.Pantallas;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.secondjuego.Constantes;
//import com.example.secondjuego.R;


public class PantallaAjustes extends Pantalla{
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
     * Paint de ajustes
     */
    Paint p;
    /**
     * Imagen del fondo ajustes
     */
    Bitmap bitmapMenu;
    /**
     * imagen del botón back
     */
    Bitmap bitmapBack;

    /**
     * Control del rect del botón back
     */
    Rect gameOver;
    /**
     * Rect de boton de la música
     */
    Rect rectMusica;
    /**
     * Rect de vibración
     */
    Rect rectVibracion;
    /**
     * Paint de rect vibración
     */
    Paint pVib;
    /**
     * Estado de la música y el cambio de imagen si música on o off
     */
    static  boolean control=true;
    /**
     * Imagen de musica on
     */
    Bitmap bitmapMusicOn;
    /**
     * Imagen de musica off
     */
    Bitmap bitmapMusicOff;

    static boolean controlVibracion =true;
    Bitmap bitmapVibrationOn;
    Bitmap bitmapVibrationOff;


    /**
     * Inicializamos tanto el paint como las imágenes y el rect del back
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaAjustes(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        p= new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize((float) altoPantalla /4);
        p.setTextAlign(Paint.Align.CENTER);

        pVib = new Paint();
        pVib.setColor(Color.WHITE);

        bitmapMenu=escala(com.example.secondjuego.R.drawable.fondomenu2,anchoPantalla,altoPantalla);
        bitmapBack = escala(com.example.secondjuego.R.drawable.backdef,anchoPantalla/6, altoPantalla/7);
        gameOver = new Rect(0,altoPantalla-altoPantalla/6,anchoPantalla/5, altoPantalla);

        // casi bueno rectMusica = new Rect(anchoPantalla-anchoPantalla/3,altoPantalla/4, anchoPantalla/3, altoPantalla/6 );
        //rectMusica = new Rect(anchoPantalla/6, 0+altoPantalla/20, altoPantalla/2, altoPantalla/8+altoPantalla/20);
        rectMusica = new Rect(anchoPantalla/3, altoPantalla / 7, anchoPantalla/2, altoPantalla/4+altoPantalla/20);
        rectVibracion =new Rect(anchoPantalla/3, altoPantalla-altoPantalla/4-altoPantalla/6,anchoPantalla/2,altoPantalla-altoPantalla/4);
        bitmapMusicOn = escala(com.example.secondjuego.R.drawable.musicdef, anchoPantalla/4-anchoPantalla/16, altoPantalla/4-altoPantalla/16);
        bitmapMusicOff = escala(com.example.secondjuego.R.drawable.musicoffdef,anchoPantalla/4-anchoPantalla/16, altoPantalla/4-altoPantalla/16);
        bitmapVibrationOn = escala(com.example.secondjuego.R.drawable.vibrationdef,anchoPantalla/4, altoPantalla/4-altoPantalla/30);
        bitmapVibrationOff = escala(com.example.secondjuego.R.drawable.vibrationoffdef,anchoPantalla/4, altoPantalla/4-altoPantalla/30);
    }

    /**
     * Escalado de cualquier imagen introducida
     * @return bitmap con las dimensiones puestas acorde al ancho y alto pantalla
     * @param res
     * @param nuevoAncho
     * @param nuevoAlto
     */
    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto){
        Bitmap bitmapAux= BitmapFactory.decodeResource(context.getResources(), res);
        return Bitmap.createScaledBitmap(bitmapAux,nuevoAncho, nuevoAlto, true);
    }

    /**
     * Dibujamos el botón para volver al menú principal, el fondo de pantalla y las distintas opciones de ajustes
     * Con el control de música on o off
     * @param c
     */
    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(bitmapMenu, 0, 0, null);
        //c.drawText("AJUSTES", anchoPantalla/2,altoPantalla/3,p);
        c.drawBitmap(bitmapBack,0,altoPantalla- (float) altoPantalla /6,null);
        if(control && music()){
            c.drawBitmap(bitmapMusicOn,0+ (float) anchoPantalla /3,
                    (float) altoPantalla /7- (float) altoPantalla /30,null);
        }else{
            c.drawBitmap(bitmapMusicOff,0+ (float) anchoPantalla /3,
                    (float) altoPantalla /7- (float) altoPantalla /30,null);

        }
        if(controlVibracion && vibracion()){
            c.drawBitmap(bitmapVibrationOn, 0+ (float) anchoPantalla /3- (float) anchoPantalla /29,
                    altoPantalla- (float) altoPantalla /2+ (float) altoPantalla /25,null);

        }else{
            c.drawBitmap(bitmapVibrationOff, 0+ (float) anchoPantalla /3- (float) anchoPantalla /29,
                    altoPantalla- (float) altoPantalla /2+ (float) altoPantalla /25,null);

        }

    }

    /**
     * Al tocar al rect de back vuelve al menú principal y gestionamos la música y vibración
     * @param event
     * @return 18
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
            }else if (rectMusica.contains(x,y)){
                Log.i("TAG", "toco??");
                if (control){
                    cambiarMusica(false);
                    if (PantallaMenu.mPMenu !=null){
                        PantallaMenu.mPMenu.stop();
                    }
                    control=false;
                }else {
                    cambiarMusica(true);
                    if (PantallaMenu.mPMenu !=null){
                        PantallaMenu.mPMenu.start();
                    }
                    control=true;
                }

            }else if (rectVibracion.contains(x,y)){
                if (controlVibracion){
                    cambiarVibracion(false);
                    controlVibracion=false;
                }else{
                    cambiarVibracion(true);
                    controlVibracion=true;
                }
            }
        }
        return 18;
    }

    /**
     * Cambiamos el estado de la música guardándolo como preferencias
     * @param  bol
     */
    public void cambiarMusica( boolean bol){
        SharedPreferences preferencesBool = context.getSharedPreferences("booleanas",Context.MODE_PRIVATE );
        SharedPreferences.Editor editorBool =  preferencesBool.edit();
        editorBool.putBoolean("booleanas",bol);
        //editorBool.commit();
        editorBool.apply();
    }
    /**
     * En el preference del guardado de ajustes activamos o no la música
     * @return music
     */
    public boolean music(){
        SharedPreferences preferencesBool = context.getSharedPreferences("booleanas",Context.MODE_PRIVATE );
        //MiRA ESTO @aperez "booleanas" or " boolean"
        return preferencesBool.getBoolean("booleanas",true);
    }

    /**
     * Cambiamos el estado de la vibración guardándolo como preferencias
     * @param bol bol
     */
    public void cambiarVibracion( boolean bol){
        SharedPreferences preferencesBool = context.getSharedPreferences("boolVibracion",Context.MODE_PRIVATE );
        SharedPreferences.Editor editorBool =  preferencesBool.edit();
        editorBool.putBoolean("boolVibracion",bol);
        editorBool.apply();
    }

    /**
     * En el preference del guardado de ajustes activamos o no la vibración
     * @return vibracion
     */
    public boolean vibracion(){
        SharedPreferences preferencesBool = context.getSharedPreferences("boolVibracion",Context.MODE_PRIVATE );
        return preferencesBool.getBoolean("boolVibracion",true);
    }


}

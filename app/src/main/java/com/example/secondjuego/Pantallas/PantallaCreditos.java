package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;


import com.example.secondjuego.Constantes;
//import com.example.secondjuego.R;


public class PantallaCreditos extends Pantalla implements SensorEventListener  {
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
     * Paint de créditos
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
     * Imagen de texto de créditos
     */
    Bitmap bitmapCreditos;

    /**
     * Sensor de Manager
     */
    private SensorManager sensorManager;
    /**
     * Sensor del acelerómetro
     */
    private Sensor sensor;
    /**
     * Contador para el giro de izquierda y derecha del móvil
     */
    private int mov=0;



    /**
     * Inicializamos tanto el paint como las imágenes y el rect del back
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaCreditos(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        p= new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize((float) altoPantalla /4);
        p.setTextAlign(Paint.Align.CENTER);
        bitmapMenu=escala(com.example.secondjuego.R.drawable.fondomenu2,anchoPantalla,altoPantalla);
        bitmapBack = escala(com.example.secondjuego.R.drawable.backdef,anchoPantalla/6, altoPantalla/7);
        gameOver = new Rect(0,altoPantalla-altoPantalla/6,anchoPantalla/5, altoPantalla);
        bitmapCreditos = escala(com.example.secondjuego.R.drawable.creditosdef, anchoPantalla/2, altoPantalla/2);

        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

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
     * Dibujamos el botón para volver al menú principal, el fondo de pantalla y la imagen de texto de créditos
     * @param c
     */
    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(bitmapMenu, 0, 0, null);
        c.drawBitmap(bitmapCreditos, (float) anchoPantalla /4, (float) altoPantalla /4, null);
        c.drawBitmap(bitmapBack,0,altoPantalla- (float) altoPantalla /6,null);


    }
    /**
     * Al tocar al rect de back vuelve al menú principal y para la música
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
            }
        }
        return 18;
    }

    /**
     * Controlamos el evento del sensor, si agitamos el móvil, cambia el fondo de
     * la pantalla de créditos
     * @param  sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0]<-4 && mov==0){

            mov++;
        }else{
            if(sensorEvent.values[0]>4 && mov==1){
                mov++;
            }
        }

        if(mov==2){
            mov=0;
            bitmapMenu=escala(com.example.secondjuego.R.drawable.city2,anchoPantalla,altoPantalla);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}

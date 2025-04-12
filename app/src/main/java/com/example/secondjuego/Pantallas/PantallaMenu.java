package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.secondjuego.Constantes;
import com.example.secondjuego.Heroe;
import com.example.secondjuego.Pantallas.Pantalla;
//import com.example.secondjuego.R;

public class PantallaMenu extends Pantalla implements SensorEventListener {
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
     * Paint del botón back
     */
    Paint pBack;
    /**
     * Rect tanto de ayuda, juego, opciones, créditos y records para controlar sus pulsaciones
     * para el cambio de pantalla
     */
    Rect ayuda, juego, opciones, creditos, records;
    /**
     * Imagen del fondo de pantalla del menú
     */
    Bitmap bitmapMenu;
    /**
     * Imagen del icono ayuda
     */
    Bitmap bitmapHelp;
    /**
     * Imagen del icono ajustes
     */
    Bitmap bitmapSettings;
    /**
     * Imagen de empezar juego
     */
    Bitmap bitmapStart;
    /**
     * Imagen del icono récords
     */
    Bitmap bitmapRecords;
    /**
     * Imagen del icono créditos
     */
    Bitmap bitmapCredits;
    /**
     * MediaPlayer del menú principal
     */
    public static MediaPlayer mPMenu;
    /**
     * Audio del menú princinpal, la música
     */
    public static AudioManager audioMenu;

    /**
     * Imagen muerte al agitar el móvil
     */
    Bitmap bitmapMuerte;
    /**
     * Boleana de control para que aparezca
     */
    boolean boolMuerte;
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
     * Constructor, inicializamos imagen fondo, los rect con sus iconos para el cambio de pantallas
     * Inicializamos el media player y el audio manager
     * Activamos e inicializamos el control de sensor del acelerómetro
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaMenu(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        //Fondo e imagenes
        bitmapMenu=escala(com.example.secondjuego.R.drawable.fondomenu2,anchoPantalla,altoPantalla);
        //bitmapHelp = BitmapFactory.decodeResource(context.getResources(),R.drawable.help);
        bitmapHelp = escala(com.example.secondjuego.R.drawable.help,anchoPantalla/12, altoPantalla/9);
        bitmapSettings= escala(com.example.secondjuego.R.drawable.settings,anchoPantalla/13, altoPantalla/9);
        bitmapStart= escala(com.example.secondjuego.R.drawable.start,anchoPantalla/10, altoPantalla/3);
        bitmapRecords= escala(com.example.secondjuego.R.drawable.records,anchoPantalla/10, altoPantalla/7);
        bitmapCredits = escala(com.example.secondjuego.R.drawable.creditsmod,anchoPantalla/8, altoPantalla/8);
        bitmapMuerte = escala(com.example.secondjuego.R.drawable.muertedef2, anchoPantalla/2-anchoPantalla/5, altoPantalla/2+altoPantalla/3);

        p= new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(altoPantalla/4);
        p.setTextAlign(Paint.Align.CENTER);

        pBack= new Paint();
        pBack.setColor(Color.GREEN);
        pBack.setTextSize(altoPantalla/4);
        pBack.setTextAlign(Paint.Align.CENTER);

        juego = new Rect(anchoPantalla-anchoPantalla/8, altoPantalla-altoPantalla/4,anchoPantalla,altoPantalla-altoPantalla/17);
        opciones = new Rect(anchoPantalla/22,0+altoPantalla/20,anchoPantalla/8, altoPantalla/8);
        ayuda = new Rect(anchoPantalla/6, 0+altoPantalla/20, altoPantalla/2, altoPantalla/8+altoPantalla/20);
        creditos = new Rect(anchoPantalla/3, 0+altoPantalla/20, anchoPantalla/2, altoPantalla/9+altoPantalla/20);
        records = new Rect(anchoPantalla-anchoPantalla/8,0+altoPantalla/20, anchoPantalla, altoPantalla/8+altoPantalla/20);
        //musica
        audioMenu=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mPMenu= MediaPlayer.create(context,com.example.secondjuego.R.raw.nostopkult);
        int v= audioMenu.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (music()){
            if (!mPMenu.isPlaying()){
                mPMenu.setVolume(v/2, v/2);
                mPMenu.start();
                mPMenu.setLooping(true);
            }
        }
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        boolMuerte=true;

    }
    /**
     * Escalado de cualquier imagen introducida
     * @param res
     * @param nuevoAncho
     * @param nuevoAlto
     * @return bitmap con las dimensiones puestas acorde al ancho y alto pantalla
     */
    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto){
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        return bitmapAux.createScaledBitmap(bitmapAux,nuevoAncho, nuevoAlto, true);
    }

    /**
     * Dibujamos tanto el fondo como los iconos de ajustes, ayuda, records, créditos y start de partida
     * Si agitamos el móvila apaarece una imagen
     * @param c
     */
    public void dibujar (Canvas c){
        c.drawBitmap(bitmapMenu, 0, 0, null);
        //c.drawText("Soy menú", anchoPantalla/2,altoPantalla/3,p);
        //c.drawRect(juego, p);
        //c.drawRect(ayuda, p);
        //c.drawRect(opciones,pBack);
        //c.drawRect(records,p);
        //c.drawRect(creditos,pBack);
        if (!boolMuerte){
            c.drawBitmap(bitmapMuerte,anchoPantalla/3, altoPantalla/6 , null);
        }

        c.drawBitmap(bitmapSettings,anchoPantalla/22,0+altoPantalla/20,null );
        c.drawBitmap(bitmapHelp,anchoPantalla/6,0+altoPantalla/20,null);
        c.drawBitmap(bitmapStart,anchoPantalla-anchoPantalla/8,altoPantalla-altoPantalla/3, null);
        c.drawBitmap(bitmapRecords,anchoPantalla-anchoPantalla/8,0+altoPantalla/20,null );
        c.drawBitmap(bitmapCredits,anchoPantalla/3,0+altoPantalla/25,null);




    }
    public void actualizarFisica(){

    }

    /**
     * Gestiona el cambio de pantallas en el menú: ajustes, ayuda, record, créditos y start
     * @param event
     * @return costante de pantalla
     */
    public int onTouch (MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP){
            int x = (int) event.getX();
            int y = (int) event.getY();
            if(juego.contains(x,y)){
                mPMenu.stop();
                return  Constantes.PJUEGO;
            }else if (ayuda.contains(x,y)){
                return  Constantes.PAYUDA;
            } else if (opciones.contains(x,y)){
                return Constantes.PAJUSTES;
            } else if (records.contains(x,y)){
                return Constantes.PRECORDS;
            } else if (creditos.contains(x,y)){
                return  Constantes.PCREDITOS;
            }
        }
        return numEscena;
    }
    /**
     * En el preference del guardado de ajustes activamos o no la música
     * @return music
     */
    public boolean music(){
        SharedPreferences preferencesBool = context.getSharedPreferences("booleanas",Context.MODE_PRIVATE );
        boolean music = preferencesBool.getBoolean("booleanas",true);
        return music;
    }

    /**
     * Control del evento, si agitamos el móvil, aparece una imagen
     * @param sensorEvent
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
            boolMuerte=false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

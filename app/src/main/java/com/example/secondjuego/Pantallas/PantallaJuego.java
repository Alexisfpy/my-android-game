package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;

import com.example.secondjuego.Constantes;
import com.example.secondjuego.Enemigo;
import com.example.secondjuego.EnemigoFly;
import com.example.secondjuego.Fondo;
import com.example.secondjuego.Heroe;
//import com.example.secondjuego.R;
import com.example.secondjuego.Vida;

import java.util.ArrayList;

public class PantallaJuego extends Pantalla {

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
     * Clase heroe
     */
    Heroe heroe;

    /**
     * Rect del Heroe
     */
    Rect rectHeroe;

    /**
     * Bitmpa del horoe
     */
    Bitmap bitmapHeroeGhost;
    Bitmap bitmapHeroeGhost2;

    /**
     *
     */
    int contadorHereo;

    //Enemigos
    /**
     * Enemigos tipo 1
     */
    ArrayList<Enemigo> enemigos= new ArrayList<>();
    /**
     * Imagen del enemigo 1
     */
    Bitmap bitmapEnemigRun;

    /**
     * Enemigos tipo 2
     */
    ArrayList<EnemigoFly> enemigosFly = new ArrayList<>();
    /**
     * Imagen enemigo 2
     */
    Bitmap bitmapEnemig1b;

    //Fondo
    /**
     * Fondo del juego
     */
    private final Bitmap bitmapFondo; // Imagen de fondo
    /**
     * Fondo para el efecto parallax
     */
    private final Fondo[]fondo = new Fondo[2];
    /**
     * Control del fondo
     */
    private boolean esTitulo=true;
    /**
     * Contador del fondo
     */
    int cont =0;

    //Vida
    /**
     * Clase Vida
     */
    Vida vida;

    /**
     * En un futuro si quiero que tenga límites el progreso del juego
     */
    int vidaGanar;

    /**
     * Contador de colisión
     */
    int choqueCont=-4;

    /**
     * Vibración de la colisión
     */
    Vibrator vibrator;

    /**
     * Control de la música
     */
    public static MediaPlayer mediaPlayerMusic;
    /**
     * Control del audio
     */
    public static  AudioManager audioManagerMusic;
    /**
     * Audio de colisión con los enenmigos
     */
    public  AudioManager aMColision;

    /**
     * Agrupación de sonidos de efecto
     */
    private final SoundPool efectos;

    /**
     * Los sonidos de efecto
     */
    private final int sonidoColision;
    private final int sonidoGameOver;
    /**
     * Cuantos pueedn reproducirse a la vez
     */
    final private int maxSonidosSimultaneos=2;

    /**
     * Control de volumen
     */
    int v;

    /**
     * Contador para el sonido de colisión
     */
    int contador=0;

    /**
     * Finaliza el juego
     */
    public boolean pararJuego;


    /**
     * Inicializamos heroes, enemigos , fondo, vida, vibración, la música y los efectos de sonido
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaJuego(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.vidaGanar =10000;
        pararJuego = false;
        //hereo
        bitmapHeroeGhost =escala(com.example.secondjuego.R.drawable.ghosthalo1, anchoPantalla/12-anchoPantalla/30, altoPantalla/8);
        bitmapHeroeGhost2 =escala(com.example.secondjuego.R.drawable.ghosthalo4, anchoPantalla/12-anchoPantalla/30, altoPantalla/8);

        rectHeroe=new Rect(300,350,600,500);
        heroe = new Heroe(context,bitmapHeroeGhost, (float) anchoPantalla /10, (float) altoPantalla /5,anchoPantalla/12-anchoPantalla/30,altoPantalla/10 );

        //Enemigos
        int parteX=anchoPantalla/20;
        int parteY=altoPantalla/10;

        bitmapEnemig1b =escala(com.example.secondjuego.R.drawable.enem1b,anchoPantalla/10,altoPantalla/5);
        bitmapEnemigRun = escala(com.example.secondjuego.R.drawable.enem2a,anchoPantalla/10, altoPantalla/5);

        enemigosFly.add(new EnemigoFly(anchoPantalla, parteY, anchoPantalla/10,altoPantalla/6,parteX/10,Color.BLUE,bitmapEnemig1b, context));
        enemigosFly.add(new EnemigoFly(anchoPantalla,parteY*4,anchoPantalla/10,altoPantalla/6,parteX/8,Color.BLACK,bitmapEnemig1b, context));
        enemigos.add(new Enemigo(anchoPantalla,parteY*7,anchoPantalla/10,altoPantalla/5,parteX/15,Color.MAGENTA,bitmapEnemigRun, context));
        enemigos.add(new Enemigo(anchoPantalla,parteY*8,anchoPantalla/10,altoPantalla/5,parteX/12,Color.GRAY,bitmapEnemigRun, context));
        //enemigos.add(new Enemigo(anchoPantalla,parteY*5,anchoPantalla/10,altoPantalla/6,parteX/10,Color.YELLOW,bitmapEnemig1b));

        //Fondo
        bitmapFondo=escalaAltura(com.example.secondjuego.R.drawable.city,altoPantalla);
        fondo[0] = new Fondo(bitmapFondo);
        fondo[1] = new Fondo(bitmapFondo,fondo[0].imagen.getWidth());

        //Vida
        vida = new Vida(100,500, anchoPantalla, altoPantalla, context);

        //Vibración

        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        //Musica
        audioManagerMusic =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayerMusic = MediaPlayer.create(context,com.example.secondjuego.R.raw.duki);
         v= audioManagerMusic.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (music()){
            if (!mediaPlayerMusic.isPlaying()){
                mediaPlayerMusic.setVolume((float) v /2, (float) v /2);
                mediaPlayerMusic.start();
                mediaPlayerMusic.setLooping(true);
            }
        }


       aMColision=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        spb.setMaxStreams(maxSonidosSimultaneos);
        this.efectos=spb.build();
        sonidoColision = efectos.load(context,com.example.secondjuego.R.raw.sfxgrito,1);
        sonidoGameOver = efectos.load(context,com.example.secondjuego.R.raw.sfxderrorta,1);
        //sonidoColision = efectos.load(context,R.raw.);
    }

    /**
     *Escalamos el tamaño que queramos acorde al ancho y alto de la pantalla móvil
     * @param res
     * @param nuevoAncho
     * @param nuevoAlto
     * @return bitmap con la imagen escalada
     */
    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto){
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        return Bitmap.createScaledBitmap(bitmapAux,nuevoAncho, nuevoAlto, true);
    }

    /**
     * Escalamos acorde con la altura
     * @param res
     * @param nuevoAlto
     * @return bitmap con la imagen escalada
     */
    public Bitmap escalaAltura(int res, int nuevoAlto ) {
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAlto==bitmapAux.getHeight()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
    }

    /**
     * Dibujamos el fondo , los distintos enemigos, el heroe y la vida restante
     * con su progreso
     * @param c
     */
    public void dibujar(Canvas c) {
        contadorHereo++;
        try {
            if (esTitulo){
                c.drawBitmap(bitmapFondo, 0, 0, null); // Dibujamos el fondo
                esTitulo=false;
            }
            else {
                c.drawBitmap(fondo[0].imagen, fondo[0].posicion.x, 0, null);
                c.drawBitmap(fondo[1].imagen, fondo[1].posicion.x, 0, null);
                cont++;
            }

            for(Enemigo e:enemigos){
                e.mover();
                e.dibujar(c);
            }
            for(EnemigoFly eFly:enemigosFly){
                eFly.mover();
                eFly.dibujar(c);
            }
            vida.dibujar(c);
            heroe.dibujar(c);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     *  Actualizamos tanto el fondo , heroe y enemigos, donde controlamos sus
     *  colisiones , efectos de sonido del choque, efecto de sonido cuando se pierde y la vibración.
     *  Cuando la vida llega a 0 o menos, finaliza el juego
     *  En el fondo comprobamos que si sobrepasa la pantalla lo reiniciamos
     *
     */
    public void actualizarFisica(){

        if (!pararJuego){
            Vida.progeso++;

            fondo[0].mover(-5+fondo[0].aceleracionFondo());
            fondo[1].mover(-5+fondo[1].aceleracionFondo());
            if (fondo[0].posicion.x+fondo[0].imagen.getWidth()<0 ) {
                fondo[0].posicion.x = fondo[1].posicion.x + fondo[1].imagen.getWidth();
            }

            if (fondo[1].posicion.x+fondo[1].imagen.getWidth()<0 ) {
                fondo[1].posicion.x = fondo[0].posicion.x + fondo[0].imagen.getWidth();
            }

            heroe.actualizarFisica();
            for(EnemigoFly eFly:enemigosFly) {

                for (Enemigo e : enemigos) {
                    if (e.hitbox.intersect(heroe.hitboxHereo) || eFly.hitbox.intersect(heroe.hitboxHereo)) {
                        //log.i("colision", "choque: " + choqueCont);
                        if(contador%15==0){
                            int v2= aMColision.getStreamVolume(AudioManager.STREAM_MUSIC);
                            efectos.play(sonidoColision,v2,v2,1,0,1);
                        }

                        if (vibracion()){
                            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                        }

                        Enemigo.boolColision = true;
                        EnemigoFly.boolColision = true;
                        vida.numVidas--;
                        if (vida.numVidas == 0 || vida.win == Vida.progeso) {
                            mediaPlayerMusic.stop();
                            pararJuego = true;
                            int v3 = aMColision.getStreamVolume(AudioManager.STREAM_MUSIC);
                            efectos.play(sonidoGameOver,v3,v3,1,0,1);
                        }
                        contador++;
                    } else {
                        Enemigo.boolColision = false;
                        EnemigoFly.boolColision = false;
                    }
                }
            }
        }
    }

    /**
     * La mitad de la dereche el heroe sube y la otra mitad restante baja
     * Si pierde ,al tocar , no devuelve a la pantalla game over
     * @param  event
     * @return número de pantalla game over cuando pierde
     */
    public int onTouch (MotionEvent event){
        if(!pararJuego){
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    if (event.getX()< (float) anchoPantalla /2){
                        heroe.saltoHeroe(altoPantalla,anchoPantalla,5);
                    }else{
                        heroe.caidaHeroe(altoPantalla, anchoPantalla, 5);
                    }
                    break;
            }
        }else{
            return Constantes.PGAMEOVER;
        }
        return 18;
    }

    /**
     * En el preference del guardado de ajustes activamos o no la música
     * @return estado si está activado o no la música
     */
    public boolean music(){
        SharedPreferences preferencesBool = context.getSharedPreferences("booleanas",Context.MODE_PRIVATE );
        return preferencesBool.getBoolean("booleanas",true);
    }

    /**
     * En el preference del guardado de ajustes activamos o no la vibración
     * @return estado si está activado o no la vibración
     */
    public boolean vibracion(){
        SharedPreferences preferencesBool = context.getSharedPreferences("boolVibracion",Context.MODE_PRIVATE );
        return preferencesBool.getBoolean("boolVibracion",true);
    }

}

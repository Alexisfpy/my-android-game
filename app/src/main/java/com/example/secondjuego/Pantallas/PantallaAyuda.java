package com.example.secondjuego.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.secondjuego.Constantes;
import com.example.secondjuego.Heroe;
//import com.example.secondjuego.R;

public class PantallaAyuda extends Pantalla {

    /**
     * Control de la pantalla
     */
    int numEscena;

    /**
     * Ancho de la pantalla  móvil
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
     * Paint de ayuda
     */
    Paint p;
    /**
     * Imagen del fondo ayuda
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
     * Clase Heroe
     */
    Heroe heroe;
    /**
     * Imagen del Heroe
     */
    Bitmap bitmapHeroeGhost;

    Bitmap bitUp;
    Bitmap bitDown;


    /**
     * Inicializamos tanto el paint como las imágenes
     * @param numEscena
     * @param context
     * @param anchoPantalla
     * @param altoPantalla
     */
    public PantallaAyuda(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
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
        bitmapHeroeGhost =escala(com.example.secondjuego.R.drawable.ghosthalo1, anchoPantalla/12-anchoPantalla/30, altoPantalla/8);

        heroe = new Heroe(context,bitmapHeroeGhost, (float) anchoPantalla /10,
                (float) altoPantalla /5,anchoPantalla/12-anchoPantalla/30,altoPantalla/10 );
        bitUp = escala(com.example.secondjuego.R.drawable.pushupdef, anchoPantalla/2, altoPantalla/2);
        bitDown = escala(com.example.secondjuego.R.drawable.pushdowndef, anchoPantalla/2, altoPantalla/2);



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
     * Dibujamos el botón para volver al menú principal, el fondo de pantalla ayuda
     * @param c
     */
    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(bitmapMenu, 0, 0, null);
        c.drawBitmap(bitUp, (float) anchoPantalla /6, (float) altoPantalla /3- (float) altoPantalla /10, null);
        c.drawBitmap(bitDown,anchoPantalla-bitDown.getWidth()+ (float) anchoPantalla /10, (float) altoPantalla /3+ (float) altoPantalla /8, null);
        c.drawBitmap(bitmapBack,0,altoPantalla- (float) altoPantalla /6,null);
        heroe.dibujar(c);


    }

    /**
     * Actualizamos la subida y bajada del héroe
     */
    public void actualizarFisica(){
        heroe.actualizarFisica();

    }
    /**
     * Al tocar al rect de back vuelve al menú principal y para la música
     * Por otra parte si tocamos otro lado distinto del rect, la mitad de la pantalla de la izquierda
     * sube, y la otra mitad restante baja el heroe
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
            if (event.getX()< (float) anchoPantalla /2){
                heroe.saltoHeroe(altoPantalla,anchoPantalla,5);
            }else{
                heroe.caidaHeroe(altoPantalla, anchoPantalla, 5);
            }

        }
        return 18;
    }
}

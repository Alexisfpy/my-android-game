package com.example.secondjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;



public class Heroe {
    /**
     * Posición del heroe
     */
    public PointF posicion;
    /**
     * Imagen bitmap del heroe
     */
    public Bitmap imagen;

    /**
     * Rect para el heroe para controlar la hitbox
     */
    public Rect hitboxHereo;
    /**
     * Pintar el rect del heroe
     */
    public Paint paintHeroe;
    /**
     * Conversión de PointF , extraemos la parte x
     */
    private final int x1;
    /**
     * Conversión de PointF, extraemos la parte y
     */
    private int y2;
    /**
     * El ancho del rect
     */
    private final int sx;
    /**
     * El alto de rect
     */
    private final int sy;
    /**
     * Control de sprites para ir mostrándolas en orden
     */
    int contador;

    /**
     * Contexto del juego
     */
    private final Context context;
    /**
     * Vector de bitmaps para controlar la animación de sprites
     */
    Bitmap[] heroe;
    /**
     * Control de frames, para que la animación de los sprites sea mas lenta
     */
    int nuevoFrame;

    /**
     * Iniciamos el vector de bitmap con las imágnes escaladas
     * El paintHeroe y la hitbox del heroe
     * @param context
     * @param imagen
     * @param x
     * @param y
     * @param sx
     * @param sy
     */
    public Heroe(Context context, Bitmap imagen, float x, float y, int sx, int sy) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.paintHeroe = new Paint();
        this.paintHeroe.setColor(Color.WHITE);
        this.paintHeroe.setStyle(Paint.Style.STROKE);
        this.paintHeroe.setStrokeWidth(10);
         x1 = (int)x;
         y2=Juego.altoPantalla/2;
         this.sx =sx;
         this.sy=sy;
        hitboxHereo = new Rect(x1,y2,x1+sx,y2+sy);
        this.context=context;
        Bitmap bitmapHeroe1 =escala(R.drawable.ghosthalo1, Juego.anchoPantalla/12-Juego.anchoPantalla/30, Juego.altoPantalla/8);
        Bitmap bitmapHeroe2 =escala(R.drawable.ghosthalo2, Juego.anchoPantalla/12-Juego.anchoPantalla/30, Juego.altoPantalla/8);
        Bitmap bitmapHeroe3 =escala(R.drawable.ghosthalo3, Juego.anchoPantalla/12-Juego.anchoPantalla/30, Juego.altoPantalla/8);
        Bitmap bitmapHeroe4 =escala(R.drawable.ghosthalo4, Juego.anchoPantalla/12-Juego.anchoPantalla/30, Juego.altoPantalla/8);
        heroe = new Bitmap[]{
                bitmapHeroe1,
                bitmapHeroe2,
                bitmapHeroe3,
                bitmapHeroe4,
        };
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
     * Dibujamos al heroe con la animación sprites
     * @param c
     */
    public void dibujar (Canvas c){
        c.drawBitmap(imagen, x1, y2,null);
        //c.drawRect(hitboxHereo,paintHeroe);
        if (nuevoFrame%15==0){
            sprites();
        }
        nuevoFrame++;
    }

    /**
     * Con su hitbox acorde a la posición de la imagen del heroe
     */
    public void actulizarHitbox(){
        hitboxHereo = new Rect(x1,y2,x1+sx,y2+sy);
    }

    /**
     * Actualizamos la posición del heroe
     */
    public void actualizarFisica(){
        this.posicion = new PointF(x1, y2);
        new Heroe(context, imagen,(float) x1, (float) y2, sx, sy);
    }
//@aperez Ver con actualizaciones la jugabilida de pulsar y que suba en vez de tocar constantemente
    /**
     * Gestionamos el movimiento de subida del heroe con la actulizacion de su hitbox
     * @param alto
     * @param ancho
     * @param velocidad
     */
    public void saltoHeroe(int alto, int ancho, int velocidad){
        if(y2>alto/25){
            y2 = y2-(alto/16);
            actualizarFisica();
            actulizarHitbox();

        }
    }

    /**
     * Gestuionamos el movimiento de bajda del heroe con la actulizacion de su hitbox
     * @param alto
     * @param ancho
     * @param velocidad
     */
    public void caidaHeroe(int alto, int ancho, int velocidad ){
        if(y2<alto*3/4){
            y2 = y2 +(alto/16);
            actualizarFisica();
            actulizarHitbox();
        }
    }

    /**
     * Control de la animación, de forma ordenada
     */
    public void sprites(){
        if (contador<heroe.length){
            this.imagen = heroe[contador];
            contador++;

        }else{
            contador=0;
        }
    }
}

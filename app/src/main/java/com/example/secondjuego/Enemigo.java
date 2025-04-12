package com.example.secondjuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemigo {
    /**
     * Posición del enemigo
     */
    private int x, y;

    /**
     * Rect de la hitbox del enemigo
     */
    public Rect hitbox;

    /**
     * Color del enemigo para su identificación en la hitbox
     */
    int color;
    /**
     * Velocidad del enemigo
     */
    int velocidadx;

    /**
     * Paint del enemigo
     */
    Paint paint;

    /**
     * Ancho y altura del enemigo
     */
    private final int sx;
    private final int sy;

    /**
     * Contexto del juego
     */
    Context context;

    /**
     * Bitmap imagen del enemigo
     */
    Bitmap imagen;

    /**
     * Aceleración del movimiento enemigo
     */
    public  int aceleracionEnemigo;

    /**
     * Control de colisión con el heroe
     */
    public static boolean boolColision=false;

    /**
     * Vector para controlar los sprites
     */
    Bitmap[] imagenesRun;

    /**
     * Control de las imagenes del bitmap vector
     */
    int cont=0;

    /**
     * Realentización de la animación
     */
    int nuevoFrame=0;

    /**
     * Instanciamos los parámetros mostrados , con paint, el vector de sprites
     * y la hitbox del enemigo
     * @param x
     * @param y
     * @param sx
     * @param sy
     * @param velocidadx
     * @param color
     * @param imagen
     * @param context
     */
    public Enemigo(int x, int y, int sx, int sy, int velocidadx, int color, Bitmap imagen, Context context) {
        this.y = y;
        this.color=color;
        this.velocidadx=velocidadx;
        this.sx=sx;
        this.sy=sy;
        this.imagen=imagen;
        this.context=context;

        this.paint=new Paint();
        this.paint.setColor(color);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10);
        hitbox=new Rect(x+Juego.anchoPantalla/17,y+Juego.altoPantalla/20,x+sx-Juego.anchoPantalla/17,y+sy-Juego.altoPantalla/20);
        aceleracionEnemigo=0;
        Bitmap bitmapEnemigRun1 = escala(R.drawable.enem1a,Juego.anchoPantalla/10, Juego.altoPantalla/5);
        Bitmap bitmapEnemigRun2 = escala(R.drawable.enem2a,Juego.anchoPantalla/10, Juego.altoPantalla/5);
        Bitmap bitmapEnemigRun3 = escala(R.drawable.enem3a,Juego.anchoPantalla/10, Juego.altoPantalla/5);
        Bitmap bitmapEnemigRun4 = escala(R.drawable.enem4a,Juego.anchoPantalla/10, Juego.altoPantalla/5);

        imagenesRun = new Bitmap[]{
                bitmapEnemigRun1,
                bitmapEnemigRun2,
                bitmapEnemigRun3,
                bitmapEnemigRun4,
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
     * Dibujamos el bitmap del heroe con la actualizacion de su sprites con un control de frame
     * @param c
     */
    public  void dibujar(Canvas c){
        c.drawBitmap(imagen,x,y,null);
         //c.drawRect(hitbox,paint);
         if(nuevoFrame%10==0){
             sprites();
         }
         nuevoFrame++;
    }

    /**
     * Actualizar los valores de la hitbox del enemigo
     */
    public void actualizaHitbox(){
        hitbox=new Rect(x+20,y+25,x+sx-20,y+sy-15);
    }

    /**
     * Gestionamos el movimiento del emenigo, con una posición y aleatoria manteniendo los rangos
     * de la pantalla
     */
    public void mover(){
        this.x -= velocidadx+aceleracionEnemigo();
        if (x< -sx){
            x=Juego.anchoPantalla;
            y=(int)(Math.random()*(Juego.altoPantalla-sy));
        }
        actualizaHitbox();
    }

    /**
     * Cade cierto progreso que lleve el jugador en el juego, aumenta la velocidad de movimiento de los enemigos
     * @return aceleracionEnemigo
     */
    public int aceleracionEnemigo(){
        if (Vida.progeso%500==0){
            aceleracionEnemigo++;
        }
        return aceleracionEnemigo;
    }
    /**
     * Insertar en la colección las imágenes bitmap para la animación de forma ordenada
     */
    public void sprites(){

        if (cont<imagenesRun.length){
            this.imagen = imagenesRun[cont];
            cont++;

        }else{
            cont=0;
        }

    }

}


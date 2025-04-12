package com.example.secondjuego;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.secondjuego.Pantallas.Pantalla;
import com.example.secondjuego.Pantallas.PantallaAjustes;
import com.example.secondjuego.Pantallas.PantallaAyuda;
import com.example.secondjuego.Pantallas.PantallaCreditos;
import com.example.secondjuego.Pantallas.PantallaJuego;
import com.example.secondjuego.Pantallas.PantallaMenu;
import com.example.secondjuego.Pantallas.PantallaGameOver;
import com.example.secondjuego.Pantallas.PantallaRecords;




public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Interfaz abstracta para manejar la superficie de dibujado
     */
    private final SurfaceHolder surfaceHolder;
    /**
     * Contexto de la aplicación
     */
    private final Context context;
    /**
     * Ancho de la pantalla, su valor se actualiza en el método surfaceChanged
     */
    static int anchoPantalla;
    /**
     * Alto de la pantalla, su valor se actualiza en el método surfaceChanged
     */
    static int  altoPantalla=1;
    /**
     * Hilo encargado de dibujar y actualizar la física
     */
    private Hilo hilo;
    /**
     * Control del hilo
     */
    private boolean funcionando = false;
    /**
     * Accedemos a las propiedades de la clase Pantalla
     */
    public Pantalla pantalla;

    /**
     * Se obtiene el holder y se indica donde van las funciones callback
     * Obtenemos el contexto
     * Inicializamos el hilo
     * Aseguramos que reciba eventos de toque
     * @param context
     */
    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        hilo = new Hilo();
        setFocusable(true);
    }

    /**
     * Contralamos el cambio de pantallas por constantes finales y creamos la pantalla correspondiente
     * @param  nuevaEscena
     */
    public void cambioEscena(int nuevaEscena){
        if(pantalla.numEscena != nuevaEscena){
            switch (nuevaEscena){
                case Constantes.PMENU:
                    pantalla = new PantallaMenu(nuevaEscena,context,anchoPantalla, altoPantalla);
                    break;
                case Constantes.PAYUDA:
                    pantalla = new PantallaAyuda(nuevaEscena,context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PJUEGO:
                    pantalla = new PantallaJuego(nuevaEscena, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PGAMEOVER:
                    pantalla = new PantallaGameOver(nuevaEscena, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PAJUSTES:
                    pantalla = new PantallaAjustes(nuevaEscena, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PRECORDS:
                    pantalla = new PantallaRecords(nuevaEscena, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PCREDITOS:
                    pantalla = new PantallaCreditos(nuevaEscena, context, anchoPantalla, altoPantalla);
                    break;
            }
        }
    }

    /**
     * En cuanto se crea el SurfaceView se lanze el hilo
     * @param holder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }


    /**
     * Si hay algún cambio en la superficie de dibujo (normalmente su tamaño) obtenemos el nuevo tamaño
     * Mostramos la pantalla menú principal
     * @param  holder
     * @param  format
     * @param  width
     * @param height
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;
        pantalla = new PantallaMenu(1, context, anchoPantalla, altoPantalla);

        hilo.setSurfaceSize(width,height);
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo=new Hilo();
            hilo.start();
        }
    }

    /**
     * Al finalizar el surface, se para el hilo
     * @param holder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
                e.getMessage();
        }
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
     * Control de cambio de escena de cada pantalla
     * @param event
     * @return si el cambio de pantalla a sido correcto
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                int aa = pantalla.onTouch(event);
                cambioEscena(aa);
                break;
        }
        return true;
    }



    /**
     * Clase Hilo en la cual se ejecuta el método de dibujo y de física para que se haga en paralelo con la
     * gestión de la interfaz de usuario
     */
    class Hilo extends Thread {
        public Hilo(){
        }

        /**
         * Repinta todo el lienzo
         * si la superficie no está preparada repetimos
         * Obtenemos el lienzo con Aceleración Hw
         * La sincronización es necesaria por ser recurso común
         * Implementamos las funciones acatulizarFisica, movimiento y dibujar los elementos
         * Hay que liberar el lienzo haya o no haya excepción
         */
        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) continue;
                    c = surfaceHolder.lockHardwareCanvas();
                    synchronized (surfaceHolder) {
                        pantalla.actualizarFisica();
                        pantalla.dibujar(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
        /**
         * Activa o desactiva el funcionamiento del hilo
         * @param flag
         */
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        /**
         * Función llamada si cambia el tamaño del view
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) { // Se recomienda realizarlo de forma atómica

            }
        }
    }
}
package com.example.roman.breakout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Hraci plocha
 */


//Runnable abych mohl pouzit vlakno na override run() metody
public class BreakoutView extends SurfaceView implements Runnable {

    Thread gameThread = null;

    SurfaceHolder holder;

    boolean pause = false;

    Canvas canvas;
    Paint paint;

    long fps;

    private long timeThisFrame;

    Bitmap bmp [];

    double screenW = this.getResources().getDisplayMetrics().widthPixels;
    double screenH = this.getResources().getDisplayMetrics().heightPixels;





    public BreakoutView(Context context) {
        super(context);
        holder = getHolder();
        paint = new Paint();
    }

/*    private void init(AttributeSet attrs, int defStyle) {

        bmp = new Bitmap[5];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_gray);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_blue);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_green);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.platform);

    }
*/
/*
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w/lx;
        height = h/ly;
        super.onSizeChanged(w,h,oldw,oldh);
    }
*/

    protected void draw() {

        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();

            canvas.drawColor(Color.argb(255,  26, 128, 182));
            paint.setColor(Color.argb(255,  255, 255, 255));


            holder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void run() {

        while(!pause){
            long startFrameTime = System.currentTimeMillis();

            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1){
                fps = 1000/ timeThisFrame;
            }
        }
    }

    public void update(){

    }

    public void pause(){
        pause = true;
        try{
            gameThread.join();
        } catch(InterruptedException e){
            Log.e("Error: ", "joining thread");
        }
    }

    public void resume(){
        pause = false;
        gameThread = new Thread(this);
        gameThread.start();
    }


}

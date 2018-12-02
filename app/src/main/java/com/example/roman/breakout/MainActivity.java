package com.example.roman.breakout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.roman.breakout.Items.Paddle;

public class MainActivity extends Activity {

    BreakoutView breakoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("To start the game press \"Start\"");
        //alertDialog.setMessage("");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Start",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        //alertDialog.show();
    }

    /**
     * Hraci plocha
     */


//Runnable abych mohl pouzit vlakno na override run() metody
    public class BreakoutView extends SurfaceView implements Runnable {

        Thread gameThread = null;

        SurfaceHolder holder;

        volatile  boolean play;
        boolean pause = true;


        Canvas canvas;
        Paint paint;

        long fps;

        private long timeThisFrame;

        Bitmap bmp [];

        int screenW, screenH;


        Paddle paddle;


        public BreakoutView(Context context) {
            super(context);
            holder = getHolder();
            paint = new Paint();

            screenW = this.getResources().getDisplayMetrics().widthPixels;
            screenH = this.getResources().getDisplayMetrics().heightPixels;

            paddle = new Paddle(screenW, screenH);
        }


        protected void draw() {

            if(holder.getSurface().isValid()){
                canvas = holder.lockCanvas();

                canvas.drawColor(Color.argb(255,  26, 128, 182));
                paint.setColor(Color.argb(255,  255, 255, 255));
                canvas.drawRect(paddle.getRect(),paint);


                holder.unlockCanvasAndPost(canvas);
            }

        }

        @Override
        public void run() {

            while(play){
                long startFrameTime = System.currentTimeMillis();

                draw();

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if(timeThisFrame >= 1){
                    fps = 1000/ timeThisFrame;
                }
            }
        }

        public void update(){

            paddle.update(fps);
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

        //kliknuti na screen
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent){
            switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
                //klik na screen
                case MotionEvent.ACTION_DOWN:

                    pause = false;

                    if(motionEvent.getX() > screenW / 2){
                        paddle.setMovementDirection(paddle.RIGHT);
                    }
                    else{
                        paddle.setMovementDirection(paddle.LEFT);
                    }
                    break;
                //pryc s prstem
                case MotionEvent.ACTION_UP:

                    paddle.setMovementDirection(paddle.STOP);
                    break;
            }
            return true;
        }


    }


    @Override
    protected void onResume(){
        super.onResume();
        breakoutView.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();

        breakoutView.pause();
    }

}

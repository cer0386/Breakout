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

import com.example.roman.breakout.Items.Ball;
import com.example.roman.breakout.Items.Brick;
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
        Ball ball;
        Brick[] bricks = new Brick[200];
        int nOBricks = 0;



        public BreakoutView(Context context) {
            super(context);
            holder = getHolder();
            paint = new Paint();

            screenW = this.getResources().getDisplayMetrics().widthPixels;
            screenH = this.getResources().getDisplayMetrics().heightPixels;

            paddle = new Paddle(screenW, screenH);
            ball = new Ball(screenW, screenH);


            createBricksAndAll();
        }

        public void createBricksAndAll(){

            ball.reset(screenW, screenH);

            int brickW = screenW / 8;
            int brickH = screenH / 10;
            nOBricks = 0;
            for(int column = 0; column < 8; column++){
                for(int row = 0; row < 3; row++){
                    bricks[nOBricks] = new Brick(row, column, brickW, brickH);
                    nOBricks++;
                }
            }
        }


        protected void draw() {

            if(holder.getSurface().isValid()){
                canvas = holder.lockCanvas();

                canvas.drawColor(Color.argb(255,  26, 128, 182));
                paint.setColor(Color.argb(255,  255, 255, 255));
                canvas.drawRect(paddle.getRect(),paint);
                canvas.drawRect(ball.getRect(), paint);

                paint.setColor(Color.argb(255,249,129,0));
                for(int i = 0; i < nOBricks; i++){
                    if(!bricks[i].isDestroyed()){
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }


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
            ball.update(fps);
        }

        public void pause(){
            play = false;
            try{
                gameThread.join();
            } catch(InterruptedException e){
                Log.e("Error: ", "joining thread");
            }
        }

        public void resume(){
            play = true;
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

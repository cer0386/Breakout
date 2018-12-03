package com.example.roman.breakout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

        /*AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("To start the game press \"Start\"");
        //alertDialog.setMessage("");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Start",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });*/
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

        int screenW, screenH;

        Paddle paddle;
        Ball ball;
        Brick[] bricks = new Brick[200];
        int nOBricks = 0;
        int score = 0;
        //int life = 1;



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

            pause = true;
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
            score = 0;
            //life = 1;
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

                paint.setColor(Color.argb(255,255,255,255));
                paint.setTextSize(40);
                canvas.drawText("Score: " + score, 10,50,paint);

                if(score == nOBricks * 10){
                    paint.setTextSize(100);
                    canvas.drawText("YOU HAVE WON!", 10, screenH/2, paint);
                }

                /*if(life <=0){
                    paint.setTextSize(100);
                    canvas.drawText("YOU HAVE LOST!", 10, screenH/2, paint);
                }*/


                holder.unlockCanvasAndPost(canvas);
            }

        }

        void setup(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createAlertDialog();
                }
            });
        }

        void createAlertDialog(){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("YOU LOST!");
            alertDialog.setButton("Start Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    createBricksAndAll();
                }
            });
            alertDialog.show();
        }

        @Override
        public void run() {

            while(play){
                long startFrameTime = System.currentTimeMillis();

                if(!pause){
                    update();
                }
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

            //kolize s cihlama
            for(int i = 0; i < nOBricks; i++){
                if(!bricks[i].isDestroyed()){
                    if(RectF.intersects(bricks[i].getRect(),ball.getRect())){
                        bricks[i].setDestroyed();
                        ball.reverseYDirection();
                        score += 10;
                    }
                }
            }

            //kolize s plosinou
            if(RectF.intersects(paddle.getRect(),ball.getRect())){
                ball.reverseYDirection();
                //ball.setRandomXDirection();
                ball.clearY(paddle.getRect().top - 2);

            }

            //balon padl
            if(ball.getRect().bottom > screenH){
                //pause();
                //life=0;
                pause = true;
                setup();
            }

            //strop
            if(ball.getRect().top < 0){
                ball.reverseYDirection();
                ball.clearY(22); // 22, protoze funkce funguje se spodkem kule
            }

            //leva stena
            if(ball.getRect().left < 0){
                ball.reverseXDirection();
                ball.clearX(2);

            }

            //prava stena
            if(ball.getRect().right > screenW  ){
                ball.reverseXDirection();
                ball.clearX(screenW - 22);

            }


        }

        public void pause(){
            play = false;
            pause = true;
            try{
                gameThread.join();
            } catch(InterruptedException e){
                Log.e("Error: ", "joining thread");
            }
        }

        public void resume(){
            play = true;
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

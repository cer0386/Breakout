package com.example.roman.breakout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.roman.breakout.Items.Ball;
import com.example.roman.breakout.Items.Brick;
import com.example.roman.breakout.utilities.LvL;
import com.example.roman.breakout.Items.Paddle;
import com.example.roman.breakout.utilities.OrientationData;
import com.example.roman.breakout.utilities.Sound;

public class MainActivity extends Activity {

    BreakoutView breakoutView;

    int screenW, screenH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakoutView = new BreakoutView(this);

        setContentView(breakoutView);

    }

    /**
     * Hraci plocha
     */


//Runnable abych mohl pouzit vlakno na override run() metody
    class BreakoutView extends SurfaceView implements Runnable {

        Thread gameThread = null;

        SurfaceHolder holder;

        volatile  boolean play;
        boolean pause;


        Canvas canvas;
        Paint paint;

        long fps;

        private long timeThisFrame;


        Paddle paddle;
        Ball ball;
        Brick[] bricks = new Brick[200];
        int nOBricks = 0;
        int score = 0;
        boolean gameOver;
        Sound sound;
        LvL level;

        private OrientationData orientationData ;
        private long framTime;






        public BreakoutView(Context context) {
            super(context);
            holder = getHolder();
            paint = new Paint();
            pause = true;

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            screenW = size.x;
            screenH = size.y;

            paddle = new Paddle(screenW, screenH);
            ball = new Ball(screenW, screenH);

            gameOver = false; // bude to tak, kdyz bude konec vyhodi mne to z aktivity a zapisu data
            sound = new Sound(context);


            //level = new LvL();
            init();

        }

        public void init(){


            pause = true;
            ball.reset(screenW, screenH);
            paddle.reset(screenW, screenH);


            int brickW = screenW / 8;
            int brickH = screenH / 10;
            nOBricks = 0;
            orientationData = new OrientationData(getApplication());
            orientationData.register();


            //cihly
            for(int column = 0; column < 8; column++){
                for(int row = 0; row < 2; row++){
                    bricks[nOBricks] = new Brick(row, column, brickW, brickH);
                    nOBricks++;
                }
            }
            score = 0;
            //


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
                int elapsedTime = (int) timeThisFrame;

                if(orientationData.getOrientation() != null && orientationData.getStartOrientation()!=null){
                    //1. souradnice v tom poli je pro Y souradnici
                    // souradnice X jde od PI/2 do -PI/2, tak to vynásobit 2
                    float roll = (orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1])*10;

                    Log.d("rollingg", "roll: "+roll);
                    //čím víc má tiltnutou obrazovku, tím rychleji půjde
                    if(roll < -2)
                        paddle.setMovementDirection(paddle.RIGHT);
                    else if(roll > 2){
                        paddle.setMovementDirection(paddle.LEFT);
                    }
                    if(roll <=2 && roll >= -2){
                        paddle.setMovementDirection(paddle.STOP);
                    }

                }





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
                        sound.playDestroy();
                    }
                    /*if(ball.getRect().right > bricks[i].getRect().left){
                        ball.reverseXDirection();
                    }
                    if(ball.getRect().left < bricks[i].getRect().right){
                        ball.reverseXDirection();
                    }*/
                }
            }




            //kolize s plosinou
            if(RectF.intersects(paddle.getRect(),ball.getRect())){
                sound.playContact();
                ball.reverseYDirection();
                ball.setRandomXDirection();
                ball.clearY(paddle.getRect().top - 2);


            }

            //balon padl
            if(ball.getRect().bottom > screenH){

                gameOver = true;

                pause = true;

                orientationData.newGame();
                orientationData.pause();
                sound.playGameOver();



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

        public void draw() {

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
                    sound.playVictory();
                    pause = true;

                    Intent intent = new Intent(MainActivity.this, GiveName.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                }
                if(gameOver){
                    paint.setTextSize(100);
                    canvas.drawText("YOU HAVE LOST!", 10, screenH/2, paint);
                    pause = true;

                    Intent intent = new Intent(MainActivity.this, GiveName.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                }


                holder.unlockCanvasAndPost(canvas);
            }

        }

        public void pause(){
            play = false;
            //pause = true;
            try{
                gameThread.join();
            } catch(InterruptedException e){
                Log.e("Error: ", "joining thread");
            }
        }

        public void resume(){
            play = true;
            //pause = false;
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

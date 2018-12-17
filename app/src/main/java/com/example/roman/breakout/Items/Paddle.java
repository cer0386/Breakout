package com.example.roman.breakout.Items;

import android.graphics.RectF;

public class Paddle {

    //objekt co ma v sobe 4 souradnice
    private RectF rect;

    private float width, height, paddleSpeed;
    private float x, y;

    //smer plosiny
    public final int STOP = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int paddleDirection = STOP;

    public Paddle(int screenX, int screenY){
        width = 200;
        height = 40;
        x = screenX / 2;
        y = screenY - 40;

        rect = new RectF(x, y, x + width, y+height);

        paddleSpeed = 500;
    }

    public RectF getRect(){
        return rect;
    }

    public void setMovementDirection(int dir){
        paddleDirection = dir;
    }

    //volam z updatu z breakoutView, menim smer plosiny
    public void update(long fps){
        if(paddleDirection == LEFT){
            x = x - paddleSpeed / fps;
        }
        if(paddleDirection == RIGHT){
            x = x + paddleSpeed / fps;
        }
        rect.left = x;
        rect.right = x + width;
    }


    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 40;
        rect.right = x / 2 + width;
        rect.bottom = y + height;


    }



}

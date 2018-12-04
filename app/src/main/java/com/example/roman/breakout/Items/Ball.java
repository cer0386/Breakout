package com.example.roman.breakout.Items;

import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Ball {

    private RectF rect;
    private float xSpeed, ySpeed;
    private float ballWidth = 20;
    private float ballHeight = 20;

    public Ball(int screenX, int screenY){

        xSpeed = 200;
        ySpeed = -400;

        //Umisti balon do stredu obrazovky
        rect = new RectF(screenX / 2,
        screenY - 60,
        screenX / 2 + ballWidth,
        screenY - 60 - ballHeight );
    }

    public RectF getRect() {
        return rect;
    }

    public void update(long fps){
        rect.left = rect.left +(xSpeed / fps);
        rect.top = rect.top + (ySpeed / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top + ballHeight ;
    }

    public void reverseXDirection(){
        xSpeed = - xSpeed;
    }

    public void reverseYDirection(){
        ySpeed = - ySpeed;
    }

    //random smer po hitnuti plosiny, predelat na casti te plosinky
    public void setRandomXDirection(){
        Random random = new Random();
        int num = random.nextInt(2);

        if(num == 0 ){
            reverseXDirection();
        }
    }

    //metody kvuli zaseknuti balonu
    public void clearY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    public void clearX(float x){
        rect.left = x;
        rect.right = x + ballWidth;

    }

    //umisti balon na vychozi pozici
    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 60;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 60 - ballHeight;
        xSpeed = -200;
        ySpeed = -400;

    }
}

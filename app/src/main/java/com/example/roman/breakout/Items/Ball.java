package com.example.roman.breakout.Items;

import android.graphics.RectF;

import java.util.Random;

public class Ball {

    private RectF rect;
    private float xSpeed, ySpeed;
    private float ballWidth, ballHeight = 10;

    public Ball(int screenX, int screenY){

        xSpeed = 200;
        ySpeed = -400;

        //Umisti balon do stredu obrazovky
        rect = new RectF();
    }

    public RectF getRect() {
        return rect;
    }

    public void update(long fps){
        rect.left = rect.left +(xSpeed / fps );
        rect.top = rect.top + (ySpeed / fps);
        rect.right = rect.right + ballWidth;
        rect.bottom = rect.bottom + ballHeight;
    }

    public void reverseXDirection(){
        xSpeed = - xSpeed;
    }

    public void reverseYDirection(){
        ySpeed = - ySpeed;
    }

    //random smer po hitnuti plosiny, predelat na casti te plosinky
    public void setRandomXSpeed(){
        Random random = new Random();
        int num = random.nextInt(2);

        if(num ==0 ){
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
        rect.top = y - 20;
        rect.right = x / 2 +ballWidth;
        rect.bottom = y - 20 - ballHeight;
    }
}

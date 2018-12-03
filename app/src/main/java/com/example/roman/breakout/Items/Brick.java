package com.example.roman.breakout.Items;

import android.graphics.RectF;

public class Brick {

    private RectF rect;

    private boolean destroyed;

    public Brick(int row, int column, int w, int h){

        destroyed = false;
        int padding = 3;

        rect = new RectF(
                (column * w) + padding,
                (row * h) + padding,
                column * w + w - padding,
                row * h + h - padding
                );
    }

    public RectF getRect() {
        return rect;
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}

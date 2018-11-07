package com.example.roman.breakout;

import android.content.Context;
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
import android.view.View;

/**
 * Hraci plocha
 */
public class BreakoutView extends View {

    Bitmap bmp [];
    int lx = 10;
    int ly = 15;

    int width, height;

    double screenW = this.getResources().getDisplayMetrics().widthPixels;
    double screenH = this.getResources().getDisplayMetrics().heightPixels;

    private int level[][] = new int[][] {
            {0,0,0,0,0,0,0,0,0,0},
            {1,2,3,3,2,1,1,2,3,3},
            {3,2,1,1,2,3,3,2,1,1},
            {2,1,3,2,1,3,2,1,3,2},
            {2,2,2,2,2,2,2,2,2,2},
            {3,3,3,3,3,3,3,3,3,3},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,4,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
    };

    public BreakoutView(Context context) {
        super(context);
        init(null, 0);
    }

    public BreakoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BreakoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        bmp = new Bitmap[5];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_gray);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_blue);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_green);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.tile_red);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.platform);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        width = w/lx;
        height = h/ly;
        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(int i = 0; i < ly;i++){
            for (int j =0; j< lx;j++)
                canvas.drawBitmap(bmp[level[i][j]], null, new Rect(j * width, i * height, (j+1) * width, (i + 1) * height), null);
        }
    }


}

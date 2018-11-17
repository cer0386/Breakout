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

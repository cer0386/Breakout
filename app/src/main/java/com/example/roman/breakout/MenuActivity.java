package com.example.roman.breakout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.roman.breakout.utilities.Score;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Context context = getApplication();
        Score score = new Score(context);
        if(!score.spHasData())
            score.resetSP();
        score.getTop5();

        Button bPlay = findViewById(R.id.btn_play);
        Button bScore = findViewById(R.id.btn_score);

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));

            }
        });

        bScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ScoreTab.class));
            }
        });
    }
}

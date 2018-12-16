package com.example.roman.breakout;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roman.breakout.utilities.Score;

public class GiveName extends Activity {

    int points;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give);
        points = getIntent().getIntExtra("score",0);

        Button btnAscend = (Button) findViewById(R.id.btnAscend);

        btnAscend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                name = editText.getText().toString();
                Score score = new Score(getApplication());
                score.saveScore(points,name);
                startActivity(new Intent(GiveName.this, ScoreTab.class));
            }
        });

    }



}

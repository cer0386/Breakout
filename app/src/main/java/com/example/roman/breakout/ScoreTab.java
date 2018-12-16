package com.example.roman.breakout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.roman.breakout.utilities.Player;
import com.example.roman.breakout.utilities.PlayerAdapter;
import com.example.roman.breakout.utilities.Score;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class ScoreTab extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tab);

        final ListView lv = (ListView) findViewById(R.id.topPlayers);
        Context context = getApplication();
        Score score = new Score(context);
        ArrayList<Player> players = new ArrayList<Player>(score.getListTop5());
        PlayerAdapter adapter = new PlayerAdapter(this,players);
        lv.setAdapter(adapter);
        Button btnB = (Button) findViewById(R.id.btnBack);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreTab.this, MenuActivity.class));
            }
        });
    }

}

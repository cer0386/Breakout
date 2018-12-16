package com.example.roman.breakout.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roman.breakout.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private Context context;

    private List<Player> players;


    public PlayerAdapter(Context context, List<Player> players) {
        super(context, 0, players);

        this.context = context;
        this.players = players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View row = convertView;
        Player player = getItem(position);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_item, parent, false);
        }



        String x = player.name +" --- "+ player.score;
        TextView pTextV = convertView.findViewById(R.id.pScore);
        pTextV.setText(player.name + " --- "+ player.score);

        return convertView;
    }

}
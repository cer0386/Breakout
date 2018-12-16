package com.example.roman.breakout.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.layout.simple_list_item_1;

public class Score {
    private Player[] top5;
    private Player[] top6;
    private ArrayList<Player> players;
    private Context context;

    public Score(Context context){
        top5 = new Player[5];
        top6 = new Player[6];
        players = new ArrayList<Player>(6);
        this.context = context;
    }

    public void saveScore(int score, String name){

        getTop5();
        addPlayer(score,name);

        for (int i =0; i<5; i++){

            if(score >= top5[i].score){
                top5[i].score = score;
                top5[i].name = name;
                break;
            }
        }

        SharedPreferences sp = context.getSharedPreferences("com.example.roman.breakout",Context.MODE_PRIVATE);
        SharedPreferences.Editor
                editor = sp.edit();
        Gson gson = new Gson();
        for (int i = 0; i < 5; i++){
            String json = gson.toJson(top5[i]);
            editor.putString("P"+i+1, json);
            editor.apply();

        }

        editor.commit();


    }

    public Player[] getTop5(){
        SharedPreferences sp = context.getSharedPreferences("com.example.roman.breakout", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i< 5; i++){
            String json = sp.getString("P"+i+1,"");
            top5[i] = gson.fromJson(json,Player.class);
            players.add(gson.fromJson(json,Player.class));
            Log.d("fromSP",top5[i].name+" "+top5[i].score +" " +i);

        }
        return top5;
    }

    public List<Player> getListTop5(){
        Player[] p = getTop5();
        List<Player> players = new ArrayList<Player>();
        int i = 0;
        for (Player pl: p) {
            players.add(pl);
            i++;
        }




        return players;
    }

    private void addPlayer(int score, String name){
        players.add(new Player(name, score));
        for (int i = 0; i < 6; i++) {
            top6[i] = players.get(i);
        }
        sortIt(top6);

        for (int i = 0; i < 5; i++) {
            top5[i]= top6[i];
        }

    }


    private void sortIt(Player[] top6){
        for (int i = 0; i < 6-1; i++)
            for (int j = 0; j < 6-i-1; j++)
                if (top6[j].score < top6[j+1].score)
                {
                    Player temp = top6[j];
                    top6[j] = top6[j+1];
                    top6[j+1] = temp;

                }
    }



    public void resetSP(){
        SharedPreferences sp = context.getSharedPreferences("com.example.roman.breakout",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        for (int i = 0; i < 5; i++){
            top5[i] = new Player();
            top5[i].name = "Bob";
            top5[i].score = 20;
            String json = gson.toJson(top5[i]);
            editor.putString("P"+i+1, json);
            editor.apply();

        }
        editor.commit();
    }

    public boolean spHasData(){

        SharedPreferences sp = context.getSharedPreferences("com.example.roman.breakout",Context.MODE_PRIVATE);
        Player p1;
        int i = 0;
        Gson gson = new Gson();
        String json = sp.getString("P"+i+1,"");
        p1 = gson.fromJson(json,Player.class);

        //Log.d("Listar","NIC " + p1.score);
        if(p1 != null  ){
            Log.d("Listar","neco");
            return true;
        }

        else{
            Log.d("Listar","nic");
            return false;
        }


    }


}

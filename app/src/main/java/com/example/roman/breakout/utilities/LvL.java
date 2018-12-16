package com.example.roman.breakout.utilities;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LvL {


    public int nOfBricks;
    public int [] dim;
    public int [] bricksState;
    private int lvl ;

    public LvL(Context context) {
        lvl = getLvl();
        loadLevel(lvl,context);
    }

    private int getLvl() {
        int l = 1;



        return l;
    }


    private void loadLevel(int lvl, Context context) {

        /*FileInputStream is1;
        BufferedReader reader1;

        FileInputStream is;
        BufferedReader reader;

        File file= new File(String.format("levels/%dl.txt",lvl));


        try {

            final FileInputStream fil = context.getAssets();

            reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String [] array = null;
            dim = new int[2];
            int count = 0;

            while((reader.readLine()) != null){
                array = line.split(",");

            }
            dim[0] = Integer.parseInt(array[0]);
            dim[1] = Integer.parseInt(array[1]);
            nOfBricks = dim[0]*dim[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file= new File(String.format("levels/%d.txt",lvl));

        if(file.exists()){
            try {
                is = new FileInputStream(file);

            reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String [] array = null;
            bricksState = new int[16];
            int count = 0;

            while((reader.readLine()) != null){
                array = line.split(",");
                for (int i = 0; i < array.length; i++) {
                    bricksState[count] = Integer.parseInt(array[i]);
                    count++;
                }
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/





    }
}

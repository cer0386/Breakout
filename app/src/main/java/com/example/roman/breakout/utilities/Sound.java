package com.example.roman.breakout.utilities;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

public class Sound {

    private SoundPool soundPool;
    private int damage = -1;
    private int destroy = -1;
    private int gameOver = -1;
    private int victory = -1;
    private int contact = -1;

    public Sound(Context context){
        loadSoundEffects(context);
    }

    private void loadSoundEffects(Context context){
        soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC,0);

        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("brickDamage.ogg");
            damage = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("brickDestroy.ogg");
            destroy = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("gameOver.ogg");
            gameOver = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("victory.mp3");
            victory = soundPool.load(descriptor,0);

            descriptor = assetManager.openFd("paddleContact.ogg");
            contact = soundPool.load(descriptor,0);


        }catch (IOException e){
            Log.e("soundError", "Failed to load sound files" );
        }
    }

    public void playDamage(){
        soundPool.play(damage,1.0f,1.0f,0,0,1.0f);
    }
    public void playDestroy(){
        soundPool.play(destroy,1.0f,1.0f,0,0,1.0f);
    }
    public void playVictory(){
        soundPool.play(victory,1.0f,1.0f,0,0,1.0f);
    }
    public void playContact(){
        soundPool.play(contact,1.0f,1.0f,0,0,1.0f);
    }
    public void playGameOver(){
        soundPool.play(gameOver,1.0f,1.0f,0,0,1.0f);
    }
}

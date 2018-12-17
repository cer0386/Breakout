package com.example.roman.breakout.utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;


//pokaždé jak se něco v tom sensorů změní tak to bude poslouchat a bude to vědět, že se něco děje a tu to budu moct upravit
public class OrientationData implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float [] magOutput;

    private float [] orientation = new float[3];
    private Context context;
    public float [] getOrientation(){
        return orientation;
    }

    //ne vždy zapneme hru s mobilem ve stejné pozici, tak sem ji uložím
    private float [] startOrientation = null;
    public float [] getStartOrientation() {
        return startOrientation;
    }

    public void newGame(){
        startOrientation = null;
    }

    public OrientationData(Context context){
        this.context= context;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    //registruju sensory k téhle classe
    public void register(){
        //proto tady dávám delay aby to nespadlo nebo nelaglo
        manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer,SensorManager.SENSOR_DELAY_GAME);
    }

    //oddělám ten listener, když ho nebudu používat
    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //zavolá se vždy, když se hodnota sensoru změní, to se může dít pořád
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if (event.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;
        //calculate orientation
        if(accelOutput != null && magOutput != null){
            float [] R = new float[9]; //3x3 mřížka
            float [] I = new float[9];
            boolean succes = SensorManager.getRotationMatrix(R,I, accelOutput,magOutput);

            if(succes){
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation,0,startOrientation,0,orientation.length);
                }
            }
        }
    }
}

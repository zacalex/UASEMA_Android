package edu.usc.cesr.ema_uas.Service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import edu.usc.cesr.ema_uas.util.FileManager;

import static android.content.ContentValues.TAG;

/**
 * Created by cal on 1/3/18.
 */

public class AccelerometerService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    static int count=0;
    private Sensor mySensor;
    private Calendar start;
    private  double acc = 0;
    private double stantardGravity = 9.81;

    @Nullable
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //registering Sensor
        mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        //then you should return sticky
        return Service.START_STICKY;
    }
    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //add this line only
    }
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get sensor when Change
        Log.d("accelerometer ", "X: " + event.values[0]+ " Y: " + event.values[1] + " Z: " + event.values[2]);

        if (start == null) start = Calendar.getInstance();

        long diff = Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis() ;
        if(diff > 1000) {
            FileManager.appendFile(getApplicationContext(),acc + "");
            Log.d("show curr acc", acc + "");
            acc = 0;
            start = Calendar.getInstance();
            FileManager.loadFile(getApplicationContext());
        }
        calculateSVM(event.values[0]/stantardGravity, event.values[1]/stantardGravity, event.values[2]/stantardGravity);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public void calculateSVM(double x, double y, double z){
//        Log.d("calculate svm", "" + (Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) - 1));
        acc += Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) - 1;

    }

}

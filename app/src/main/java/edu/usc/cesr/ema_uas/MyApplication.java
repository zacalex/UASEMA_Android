package edu.usc.cesr.ema_uas;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class MyApplication extends Application {
    private static Boolean alarmActivityCreated = false;

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }

    public static Boolean getAlarmActivityCreated() {
        return alarmActivityCreated;
    }

    public static void setAlarmActivityCreated(Boolean alarmActivityCreated) {
        MyApplication.alarmActivityCreated = alarmActivityCreated;
    }
}

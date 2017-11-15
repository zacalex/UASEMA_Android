package edu.usc.cesr.ema_uas.alarm;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import edu.usc.cesr.ema_uas.R;
import edu.usc.cesr.ema_uas.ui.MainActivity;
import edu.usc.cesr.ema_uas.util.DateUtil;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by cal on 11/14/17.
 */

public class MyNotificationManager {
    public void setInstantNotification(Context myContext){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(myContext)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(myContext,MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        myContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;

        NotificationManager mNotifyMgr =
                (NotificationManager) myContext.getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        Log.e("Notification","instant notification set");
    }
    public void setAllNotification(Context context, Calendar[] calendars){
        for(Calendar calendar : calendars){
            setNotificationForCalendar(context,calendar);
        }
    }
    public void removeAllNotification(Context context, Calendar[] calendars){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for(Calendar calendar: calendars){
            removeNotification(alarmManager, getPendingNotification(context,calendar), calendar);
        }
    }

    private void removeNotification(AlarmManager alarmManager, PendingIntent intent, Calendar calendar) {

        try {
            alarmManager.cancel(intent);
            Log.e("remove Notification ", "AlarmManager update was not canceled. " + DateUtil.stringifyAll(calendar));
        } catch (Exception e) {
            Log.e("remove Notification ", "AlarmManager update was not canceled. " + e.toString());
        }
    }

    public void setNotificationForCalendar(Context myContext, Calendar calendar){

        AlarmManager alarmManager = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getPendingNotification(myContext,calendar));
    }

    public PendingIntent getPendingNotification(Context myContext, Calendar calendar){


        int requestCode = DateUtil.intDate(calendar);
        if(requestCode == -1) return null;
        Intent goBacktoMain = new Intent(myContext,MainActivity.class);
        PendingIntent goBackPendingIntent = PendingIntent.getActivity(myContext,requestCode,goBacktoMain, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(myContext)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Survey with requestCode : " + requestCode)
                        .setContentText("Survey for " + DateUtil.stringifyAll(calendar) + " is Ready")
                        .setContentIntent(goBackPendingIntent)
                        .setAutoCancel(true);


        Intent notificationIntent = new Intent(myContext, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, requestCode);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, mBuilder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(myContext, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}

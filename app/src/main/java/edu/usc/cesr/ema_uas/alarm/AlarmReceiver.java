package edu.usc.cesr.ema_uas.alarm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import edu.usc.cesr.ema_uas.util.AlarmUtil;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        int requestCode = intent.getIntExtra(MyAlarmManager.REQUEST_CODE, 0);

        Intent i = new Intent(context, AlarmService.class);
        i.putExtra(MyAlarmManager.REQUEST_CODE, requestCode);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startWakefulService(context, i);

        if(requestCode % 3 == 0 || requestCode % 3 == 1){
            AlarmUtil.soundAlarm(context);
        }

        Log.e("TT", "AlarmReceiver => onReceive() => requestCode == " + requestCode + " type: " + (requestCode % 3));
    }
}

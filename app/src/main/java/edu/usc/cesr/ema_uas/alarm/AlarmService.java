package edu.usc.cesr.ema_uas.alarm;

import android.app.IntentService;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import edu.usc.cesr.ema_uas.ui.AlarmActivity;
import edu.usc.cesr.ema_uas.ui.MainActivity;

public class AlarmService extends IntentService {
    private static final String MY_SERVICE = "MY_SERVICE";

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MY_SERVICE);
        mWakeLock.acquire();

        int requestCode = intent.getIntExtra(MyAlarmManager.REQUEST_CODE, 0);

        switch (requestCode % 3){
            case 0:
            case 1: {
                //  Start AlarmActivity
                Intent i = new Intent(this, AlarmActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(MyAlarmManager.REQUEST_CODE, requestCode);
                startActivity(i);
                break;
            }
            case 2: {
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(MyAlarmManager.REQUEST_CODE, requestCode);
                startActivity(i);
                break;
            }
        }

        mWakeLock.release();

        Log.e("TT", "AlarmService => onHandleIntent() => requestCode == " + requestCode );
    }
}
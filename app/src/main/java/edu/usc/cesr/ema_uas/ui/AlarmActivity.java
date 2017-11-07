package edu.usc.cesr.ema_uas.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Calendar;

import butterknife.ButterKnife;
import edu.usc.cesr.ema_uas.R;
import edu.usc.cesr.ema_uas.alarm.MyAlarmManager;
import edu.usc.cesr.ema_uas.model.Settings;
import edu.usc.cesr.ema_uas.model.Survey;
import edu.usc.cesr.ema_uas.model.UrlBuilder;
import edu.usc.cesr.ema_uas.util.AlarmUtil;

@SuppressWarnings("FieldCanBeLocal")
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DEMO = "DEMO";
    private MyAlarmManager myAlarmManager;
    private static boolean isDemo;
    private Settings settings;
    private int requestCode;
    private int surveyCode;
    private String timeTag;

    //@OnClick(R.id.serverButton)
    public void routeToMain(){
        if(isDemo){
            finish();
        } else {
            //  Cancel reminder and set completed
            if(requestCode % 3 == 0){
                myAlarmManager.cancelSingleAlarm(this, surveyCode + 1);
            }

            settings.setTakenSurveyAndSave(this, surveyCode);

            //  Start activity; only way to reach an alarm
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.URL,
                    UrlBuilder.build(UrlBuilder.PHONE_ALARM, settings, Calendar.getInstance(), true)
                    + (timeTag == null ? "" : timeTag));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        isDemo = getIntent().getBooleanExtra(DEMO, false);
        settings = Settings.getInstance(this);
        requestCode = getIntent().getIntExtra(MyAlarmManager.REQUEST_CODE, 0);
        surveyCode = Survey.getSurveyCode(requestCode);
        timeTag = settings.getTimeTag(surveyCode);
        myAlarmManager = MyAlarmManager.getInstance(this);

        //Button stopButton = findViewById(R.id.serverButton);
        Button stopButton = (Button) findViewById(R.id.serverButton);
        stopButton.setOnClickListener(this);


        //  Demo does not go through AlarmManager due to slight delay
        if(isDemo){
            AlarmUtil.soundAlarm(this);
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
            getSupportActionBar().setIcon(R.drawable.uas_logo);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Log.e("TT", "AlarmActivity => code: " + requestCode + " isDemo: " + isDemo + " TIME_TAG: " + timeTag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.serverButton: {
                this.routeToMain();

            }
        }
    }
}
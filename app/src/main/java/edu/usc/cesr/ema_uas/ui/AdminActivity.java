package edu.usc.cesr.ema_uas.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.usc.cesr.ema_uas.R;
import edu.usc.cesr.ema_uas.model.Settings;
import edu.usc.cesr.ema_uas.model.UrlBuilder;
import edu.usc.cesr.ema_uas.util.DateUtil;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATE = "DATE", TIME = "TIME";


    EditText rtidText;
    TextView dateView;
    TextView beginView;
    TextView endView;


    private String rtidVal;
    private Calendar beginVal;
    private Calendar endVal;

    private FragmentManager fm;
    private AlertDialog dialog;

    @OnClick(R.id.admin_date)
    public void updateDate(){
        MyDialogFragment fragment = MyDialogFragment.newInstance(DATE, beginVal, dateSetListener(), null);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(fragment, "");
        transaction.commit();
    }
    @OnClick(R.id.admin_begin)
    public void updateBegin(){
        MyDialogFragment fragment = MyDialogFragment.newInstance(TIME, beginVal, null, beginListener());
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(fragment, "");
        transaction.commit();
    }
    @OnClick(R.id.admin_end)
    public void updateEnd(){
        MyDialogFragment fragment = MyDialogFragment.newInstance(TIME, endVal, null, endListener());
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(fragment, "");
        transaction.commit();
    }

    public void showAlarms(){
        Settings settings = Settings.getInstance(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ScrollView scrollableView = (ScrollView) LayoutInflater.from(this).inflate(R.layout.alert,null, false);
        ((TextView) scrollableView.findViewById(R.id.alert_text)).setText(settings.toString());
        builder.setView(scrollableView);
        builder.setPositiveButton(getResources().getString(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        dialog = builder.create();
        dialog.show();
    }
    @OnClick(R.id.admin_back)
    public void back(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @OnClick(R.id.admin_save)
    public void save(){
        syncDate(beginVal);
        Settings newSettings = new Settings(rtidVal, beginVal, endVal);     //  Does not save to settings; relies on server response to save
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.URL, UrlBuilder.build(UrlBuilder.SETTINGS_CHANGE, newSettings, Calendar.getInstance(), true));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        fm = getSupportFragmentManager();


        rtidText = (EditText) findViewById(R.id.admin_rtid_text);
        dateView = (TextView) findViewById(R.id.admin_date);
        beginView = (TextView) findViewById(R.id.admin_begin);
        endView = (TextView) findViewById(R.id.admin_end);


        setText(Settings.getInstance(this));




        rtidText.addTextChangedListener(rtidListener());    //  Other listeners are set with MyDialogFragment

        View showAlarms = findViewById(R.id.admin_show_alarms);
        showAlarms.setOnClickListener(this);

        View backButton = findViewById(R.id.admin_back);
        backButton.setOnClickListener(this);


    }
    private void setText(Settings settings){
        //  RTID
        String randomRtid = String.valueOf(Math.floor((Math.random() * 1000)));
        rtidVal = settings.getRtid() == null ? randomRtid : settings.getRtid();
        setRtidText(rtidVal);

        //  Set begin time
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 9);
        begin.set(Calendar.MINUTE, 0);
        beginVal = settings.getBeginTime() == null ? begin : settings.getBeginTime();
        setDateText(beginVal);
        setBeginText(beginVal);

        //  Set end time
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 16);
        end.set(Calendar.MINUTE, 0);
        endVal = settings.getEndTime() == null ? end : settings.getEndTime();
        setEndText(endVal);
    }

    /** Set text */
    private void setRtidText(String rtid){
        rtidText.setText(rtid);
    }
    private void setDateText(Calendar date){
        String dateUnformatted = getResources().getString(R.string.admin_date);
        String dateFormatted = String.format(dateUnformatted, DateUtil.stringifyDate(date));
        dateView.setText(dateFormatted);
    }
    private void setBeginText(Calendar date){
        String beginUnformatted =  getResources().getString(R.string.admin_begin);
        String beginFormatted = String.format(beginUnformatted, DateUtil.stringifyTime(date));
        beginView.setText(beginFormatted);
    }
    private void setEndText(Calendar date){
        String endUnformatted =  getResources().getString(R.string.admin_end);
        String endFormatted = String.format(endUnformatted, DateUtil.stringifyTime(date));
        endView.setText(endFormatted);
    }

    /** Listeners && Handlers */
    private TextWatcher rtidListener(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rtidVal = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
    private DatePickerDialog.OnDateSetListener dateSetListener(){
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                beginVal.set(Calendar.YEAR, year);
                beginVal.set(Calendar.MONTH, month);
                beginVal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                endVal.set(Calendar.YEAR, year);
                endVal.set(Calendar.MONTH, month);
                endVal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDateText(beginVal);
            }
        };
    }
    private void syncDate(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        beginVal.set(Calendar.YEAR, year);
        beginVal.set(Calendar.MONTH, month);
        beginVal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        endVal.set(Calendar.YEAR, year);
        endVal.set(Calendar.MONTH, month);
        endVal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }
    private TimePickerDialog.OnTimeSetListener beginListener(){
       return  new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                beginVal.set(Calendar.HOUR, hourOfDay);
                beginVal.set(Calendar.MINUTE, minute);
                setBeginText(beginVal);
            }
        };
    }
    private TimePickerDialog.OnTimeSetListener endListener(){
        return  new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endVal.set(Calendar.HOUR, hourOfDay);
                endVal.set(Calendar.MINUTE, minute);
                setEndText(endVal);
            }
        };
    }

    @Override
    protected void onDestroy() {

        if(dialog != null){
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_show_alarms: {
                this.showAlarms();
                break;
            }
            case R.id.admin_back: {
                this.back();
                break;
            }
        }
    }


}

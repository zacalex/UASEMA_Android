package edu.usc.cesr.ema_uas.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class MyDialogFragment extends DialogFragment {

	public MyDialogFragment(){}

	private DatePickerDialog.OnDateSetListener dateListener;
	private TimePickerDialog.OnTimeSetListener timeListener;
	private Calendar cal;
	private String type;


	public static MyDialogFragment newInstance(String type, Calendar cal, DatePickerDialog.OnDateSetListener dateListener, TimePickerDialog.OnTimeSetListener timeListener){
		MyDialogFragment fragment = new MyDialogFragment();
		fragment.setVals(type, cal , dateListener, timeListener);
		return fragment;
	}

	private void setVals(String type, Calendar cal, DatePickerDialog.OnDateSetListener dateListener, TimePickerDialog.OnTimeSetListener timeListener){
		this.type = type;
		this.cal = cal;
		this.dateListener = dateListener;
		this.timeListener  = timeListener;
	}

	@NonNull
	@SuppressWarnings("ConstantConditions")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if(type.equals(AdminActivity.DATE)){
			DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), dateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			Calendar yesterday = Calendar.getInstance();
			yesterday.set(Calendar.DAY_OF_YEAR, yesterday.get(Calendar.DAY_OF_YEAR) - 1);
			dateDialog.getDatePicker().setMinDate(yesterday.getTimeInMillis());
			return dateDialog;
		} else {
			return new TimePickerDialog(getActivity(), timeListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false);
		}
	}
}
package com.himanishkaushal.spent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

@TargetApi(3)
public class CalendarActivity extends Activity implements OnClickListener {
	
	private static final String tag = "CalendarActivity";
	
	private Button currentMonth;
	private Button prevMonth, nextMonth;
	private Button selectedDayMonthYearButton;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	@SuppressLint("NewApi")
	private int month, year;
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
		+ year);
		
		selectedDayMonthYearButton = (Button) this
		.findViewById(R.id.selectedDayMonthYear);
		selectedDayMonthYearButton.setText("");
		
		prevMonth = (Button) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);
		
		currentMonth = (Button) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
		_calendar.getTime()));
		
		nextMonth = (Button) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);
		
		
		calendarView = (GridView) this.findViewById(R.id.calendar);

		adapter = new GridCellAdapter(getApplicationContext(),
		R.id.calendar_day_gridcell, month, year, this.selectedDayMonthYearButton);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}
	
	/**
	*
	* @param month
	* @param year
	*/
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getApplicationContext(),
		R.id.calendar_day_gridcell, month, year, this.selectedDayMonthYearButton);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
		_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		
		if (v == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
			+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
	
	}
	
	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View …");
		super.onDestroy();
	}
	
	public Button getSelectedDayMonthYearButton() {
		return selectedDayMonthYearButton;
	}
	

}

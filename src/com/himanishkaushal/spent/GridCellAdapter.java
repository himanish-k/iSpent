package com.himanishkaushal.spent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GridCellAdapter extends BaseAdapter implements OnClickListener {
	
	private static final String tag = "GridCellAdapter";
	private final Context _context;
	
	private final List<String> list;
	private static final int DAY_OFFSET = 1;
	private final String[] weekdays = new String[] { "Sun", 
													 "Mon", 
													 "Tue", 
													 "Wed", 
													 "Thu", 
													 "Fri", 
													 "Sat" 
													 };
	
	private final String[] months = { "January", 
									  "February", 
									  "March", 
									  "April", 
									  "May", 
									  "June", 
									  "July", 
									  "August", 
									  "September",
									  "October", 
									  "November", 
									  "December" 
									  };
	
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	private int daysInMonth;
	private int currentDayOfMonth;
	private int currentWeekDay;
	private TextView gridcell;
	private TextView num_events_per_day;
	private Button selectedDayMonthYearButton;
	private final HashMap<String, Integer> eventsPerMonthMap;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
	"dd-MMM-yyyy");
	
	// Days in Current Month
	public GridCellAdapter(Context context, int textViewResourceId, int month, int year, Button b) {
		
		super();
		this._context = context;
		this.list = new ArrayList<String>();
		selectedDayMonthYearButton = b;
		
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		
		printMonth(month, year);
		
		// Find Number of Events
		eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
	}
	
	private String getMonthAsString(int i) {
	return months[i];
	}
	
	private String getWeekDayAsString(int i) {
	return weekdays[i];
	}
	
	private int getNumberOfDaysOfMonth(int i) {
	return daysOfMonth[i];
	}
	
	public String getItem(int position) {
	return list.get(position);
	}
	
	@Override
	public int getCount() {
	return list.size();
	}
	
	/**
	* Prints Month
	*
	* @param mm
	* @param yy
	*/
	private void printMonth(int mm, int yy) {
		
		// initialize
		Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;
		
		// get the name of the current month and the number of days in the month
		int currentMonth = mm - 1;
		String currentMonthName = getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);
		
		Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");
		
		// get instance of a calendar
		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
		Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());
		
		/*
		 * assign values to all the variables
		 * varaible summary:
		 * previous month
		 * days in previous month
		 * next month
		 * previous year
		 * next year
		 * 
		 * got current month and number of days in current month before
		 */
		if (currentMonth == 11) {
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;
			Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
			+ prevMonth + " NextMonth: " + nextMonth
			+ " NextYear: " + nextYear);
		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 1;
			Log.d(tag, "**-> PrevYear: " + prevYear + " PrevMonth:"
			+ prevMonth + " NextMonth: " + nextMonth
			+ " NextYear: " + nextYear);
		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			Log.d(tag, "***—> PrevYear: " + prevYear + " PrevMonth:"
			+ prevMonth + " NextMonth: " + nextMonth
			+ " NextYear: " + nextYear);
		}
		
		// calculate number of grid cells given to the previous month
		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		trailingSpaces = currentWeekDay;
		
		Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
		Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
		Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);
		
		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
			if (mm == 2)
				++daysInMonth;
			else if (mm == 3)
				++daysInPrevMonth;
		
		/*
		 * a list is made with all the days that will be displayed in the 
		 * calendar, few days from the previous month lead by all the days from the current 
		 * month lead by few days from the next month
		 * 
		 * it's a list of strings, encoding:
		 * day number - color - month name - year
		 */
		
		// add all the days for the previous month
		for (int i = 0; i < trailingSpaces; i++) {
			
			// log
			Log.d(tag, "PREV MONTH:= " + prevMonth + " => " + getMonthAsString(prevMonth)
			+ " " + String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i));
			// log end 
			
			list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
			+ "-GREY-" + getMonthAsString(prevMonth)
			+ "-" + prevYear);
		}
		
		// add all the days for the current month
		for (int i = 1; i <= daysInMonth; i++) {
			
			// log
			Log.d(currentMonthName, String.valueOf(i) + " "
			+ getMonthAsString(currentMonth) + " " + yy);
			// log end
			
			if (i == getCurrentDayOfMonth()) {
				list.add(String.valueOf(i) + "-BLUE-"
				+ getMonthAsString(currentMonth) + "-" + yy);
			} else {
				list.add(String.valueOf(i) + "-WHITE" + "-"
						+ getMonthAsString(currentMonth) + "-" + yy);
			}
		}
		
		// add all the days for the next month
		for (int i = 0; i < list.size() % 7; i++) {
			Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
			list.add(String.valueOf(i + 1) + "-GREY" + "-"
					+ getMonthAsString(nextMonth) + "-" + nextYear);
		}
	}
	
	/**
	* NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
	* ALL entries from a SQLite database for that month. Iterate over the
	* List of All entries, and get the dateCreated, which is converted into
	* day.
	*
	* @param year
	* @param month
	* @return
	*/
	private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
	int month) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		return map;
	}
	
	@Override
	public long getItemId(int position) {
	return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) _context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.screen_gridcell, parent, false);
		}
		
		// Get a reference to the Day gridcell
		gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
		gridcell.setOnClickListener(this);
		
		// ACCOUNT FOR SPACING
		
		Log.d(tag, "current day: " + getCurrentDayOfMonth());
		String[] day_color = list.get(position).split("-");
		String theday = day_color[0];
		String themonth = day_color[2];
		String theyear = day_color[3];
		if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
			/*
			if (eventsPerMonthMap.containsKey(theday)) {
				num_events_per_day = (TextView) row
						.findViewById(R.id.num_events_per_day);
				Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
				num_events_per_day.setText(numEvents.toString());
			}*/
		}
		
		// Set the Day GridCell
		gridcell.setText(theday);
		gridcell.setTag(theday + "-" + themonth + "-" + theyear);
		Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
		+ theyear);
		
		return row;
	}
	
	@Override
	public void onClick(View view) {
		String date_month_year = (String) view.getTag();
		selectedDayMonthYearButton.setText(date_month_year);
		
		Log.e("Selected date", date_month_year);
		try {
			Date parsedDate = dateFormatter.parse(date_month_year);
			Log.d(tag, "Parsed Date: " + parsedDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public int getCurrentDayOfMonth() {
	return currentDayOfMonth;
	}
	
	private void setCurrentDayOfMonth(int currentDayOfMonth) {
	this.currentDayOfMonth = currentDayOfMonth;
	}
	
	public void setCurrentWeekDay(int currentWeekDay) {
	this.currentWeekDay = currentWeekDay;
	}
	
	public int getCurrentWeekDay() {
	return currentWeekDay;
	}
}

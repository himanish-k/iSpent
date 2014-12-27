package com.himanishkaushal.spent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerFragment extends android.app.DialogFragment implements
		android.app.DatePickerDialog.OnDateSetListener {
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		CreateEntryActivity cea = (CreateEntryActivity) getActivity();
        int year = cea.getYear();
        int month = cea.getMonth();
        int day = cea.getDay();

        return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		
		CreateEntryActivity parent = (CreateEntryActivity) getActivity();
		parent.setDate(year, month, day);
		
        String date = AppAssistant.getDateForDisplay(year, month, day);
        
        Button dateInputButton = (Button) getActivity().findViewById(R.id.button_input_date);
        dateInputButton.setText(date);
	}

}

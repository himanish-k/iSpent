package com.himanishkaushal.spent;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateEntryActivity extends ActionBarActivity {
	
	int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
        String date = AppAssistant.getDateForDisplay(year, month, day);
        
        Button dateInputButton = (Button) findViewById(R.id.button_input_date);
        dateInputButton.setText(date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// no menu bar needed here
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void showDatePicker(View view) {
    	
    	new DatePickerFragment().show(getFragmentManager(), "datePicker");
    }
    
    public void checkInput(View view) {
    	
    	EditText payeeInputView = (EditText) findViewById(R.id.input_payee);
    	//String payeeValue = payeeInputView.getText().toString();
    	
    	EditText paymentInputView = (EditText) findViewById(R.id.input_payment);
    	//double paymentValue = Double.valueOf(paymentInputView.getText().toString());
    	
    	if(!payeeInputView.getText().toString().matches("")
    			&& !paymentInputView.getText().toString().matches("")) {
    		
    		createEntry(payeeInputView.getText().toString(), Double.parseDouble(paymentInputView.getText().toString())); 
    	} else {
    		Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void createEntry(String payee, double payment) {
    	
    	Intent intent = new Intent();
    	intent.putExtra(MainActivity.EXTRA_ENTRY_YEAR, year);
    	intent.putExtra(MainActivity.EXTRA_ENTRY_MONTH, month);
    	intent.putExtra(MainActivity.EXTRA_ENTRY_DAY, day);
    	intent.putExtra(MainActivity.EXTRA_ENTRY_PAYEE, payee);
    	intent.putExtra(MainActivity.EXTRA_ENTRY_PAYMENT, payment);
    	
    	setResult(RESULT_OK, intent);
    	finish();
    }

	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}


	public int getYear() {
		return year;
	}


	public int getMonth() {
		return month;
	}


	public int getDay() {
		return day;
	}
    
}
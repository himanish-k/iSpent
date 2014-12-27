package com.himanishkaushal.spent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	public final static String EXTRA_ENTRY_YEAR = "com.himanishkaushal.spent.ENTRY_YEAR",
							   EXTRA_ENTRY_MONTH = "com.himanishkaushal.spent.ENTRY_MONTH",
							   EXTRA_ENTRY_DAY = "com.himanishkaushal.spent.ENTRY_DAY",
							   EXTRA_ENTRY_PAYEE = "com.himanishkaushal.spent.ENTRY_PAYEE",
							   EXTRA_ENTRY_PAYMENT = "com.himanishkaushal.spent.ENTRY_PAYMENT";
	private EntrySource source;
	private EntryAdapter adapter;
	private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        source = new EntrySource(this);
        source.open();
        
        Cursor cursor = source.fetchAllEntries();
        
        adapter = new EntryAdapter(this, cursor);
        					   
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        	case R.id.action_settings:
        		return true;
        	case R.id.action_add_entry:
        		addEntry();
        		return true;
        	case R.id.action_delete_all_entries:
        		deleteAllEntries();
        		return true;
        	case R.id.action_total:
        		showTotal();
        		return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
        	
        	int year = data.getIntExtra(EXTRA_ENTRY_YEAR, 0);
        	int month = data.getIntExtra(EXTRA_ENTRY_MONTH, 0);
        	int day = data.getIntExtra(EXTRA_ENTRY_DAY, 0);
        	String payee = data.getStringExtra(EXTRA_ENTRY_PAYEE);
        	double payment = data.getDoubleExtra(EXTRA_ENTRY_PAYMENT, 0.00);
        	
        	source.insertEntry(year, month, day, payee, payment);
        	adapter.notifyDataSetChanged();
        	// listView.invalidateViews();
        	
        	Toast.makeText(this, "New entry created.", Toast.LENGTH_SHORT).show();
        }
    } 
    
    public void addEntry() {
    	
    	startActivityForResult(new Intent(this, CreateEntryActivity.class), 1);
    }
    
    public void deleteAllEntries() {
    	
    	//DialogFragment alert = new DialogFragment();
    	//alert.show(getFragmentManager(), "Are you sure ?");
    	source.deleteAllEntries();
    }
    
    public void showTotal() {
    	
    	Cursor cursor = source.fetchAllEntries();
    	double total = 0;
   
    	if( cursor.moveToFirst()) {
    		do {
    			total += cursor.getDouble(cursor.getColumnIndex(cursor.getColumnName(5)));
    		} while(cursor.moveToNext());
    	}
    	
    	cursor.close();
    	Toast.makeText(this, "Your total is $" + total, Toast.LENGTH_LONG).show();
    }
     
}

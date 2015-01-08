package com.himanishkaushal.spent;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class EntryAdapter extends CursorAdapter {
	
	private LayoutInflater inflater;
	
	public EntryAdapter(Activity context, Cursor cursor) {
		super(context, cursor, 0);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
        View v = convertView;  
        CompleteListViewHolder viewHolder;  
        if (convertView == null) {  
             v = inflater.inflate(R.layout.list_item, null);  
             viewHolder = new CompleteListViewHolder(v);  
             v.setTag(viewHolder);  
        } else {  
             viewHolder = (CompleteListViewHolder) v.getTag();  
        }  
        
        int yearValue = list.get(position).getYear();
        int monthValue = list.get(position).getMonth();
        int dayValue = list.get(position).getDay();
        String dateValue = AppAssistant.getDateForDisplay(yearValue, monthValue, dayValue);
        viewHolder.date.setText(dateValue);
        
        viewHolder.payee.setText(list.get(position).getPayee());  
        
        String paymentString = new String("$" + list.get(position).getPayment());
        viewHolder.payment.setText(paymentString);
        
        return v;  
   }
	*/
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		return inflater.inflate(R.layout.list_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		TextView date, payee, payment;
		
		int id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
		int yearValue = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        int monthValue = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        int dayValue = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3)));
		
		date = (TextView) view.findViewById(R.id.list_item_date);
		date.setText(AppAssistant.getDateForListDisplay(yearValue, monthValue, dayValue));
        payee = (TextView) view.findViewById(R.id.list_item_payee);  
        payee.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
        payment = (TextView) view.findViewById(R.id.list_item_payment);
        
        double paymentValue = cursor.getDouble(cursor.getColumnIndex(cursor.getColumnName(5)));
        payment.setText(new String("$" + paymentValue));
        
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new DeleteButtonListener(context, id));
		
	}
	
	private class DeleteButtonListener implements OnClickListener {
		
		EntrySource source;
		int id;
		Context context;
		
		public DeleteButtonListener(Context context, int id) {
			this.context = context;
			source = new EntrySource(context);
			this.id = id;
		}
		@Override
		public void onClick(View v) {
			
			
			source.open();
			source.deleteEntry(id);
			source.close();
			
			((MainActivity) context).refreshList();
		}
		
	}

}   

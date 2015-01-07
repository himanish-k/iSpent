package com.himanishkaushal.spent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EntrySource {
	
	private SQLiteDatabase database;
	private DatabaseHelper helper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
								    DatabaseHelper.COLUMN_YEAR,
								    DatabaseHelper.COLUMN_MONTH,
								    DatabaseHelper.COLUMN_DAY,
								    DatabaseHelper.COLUMN_PAYEE,
								    DatabaseHelper.COLUMN_PAYMENT };

	public EntrySource(Context context) {
		helper = new DatabaseHelper(context);
	}
	
	public void open() {
		database = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
	
	public long insertEntry(int year, int month, int day, String payee, double payment) {
		
		ContentValues newEntry = new ContentValues();
		newEntry.put(DatabaseHelper.COLUMN_YEAR, year);
		newEntry.put(DatabaseHelper.COLUMN_MONTH, month);
		newEntry.put(DatabaseHelper.COLUMN_DAY, day);
		newEntry.put(DatabaseHelper.COLUMN_PAYEE, payee);
		newEntry.put(DatabaseHelper.COLUMN_PAYMENT, payment);
		
		return database.insert(DatabaseHelper.TABLE_ENTRIES, null, newEntry);
	}
	
	public boolean deleteEntry(int id) {
		String where = DatabaseHelper.COLUMN_ID + "=" + id;
		int deleted = database.delete(DatabaseHelper.TABLE_ENTRIES, where, null);
		return deleted > 0;
	}
	
	public boolean deleteAllEntries() {
		
		int deleted = database.delete(DatabaseHelper.TABLE_ENTRIES, null, null);
		return deleted > 0;
	}
	
	public Cursor fetchAllEntries() {
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_ENTRIES, allColumns, null, null, null, null, null);
		
		if(cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	/* public Cursor query (String table, 
	 * 						String[] columns, 
	 * 						String selection, 
	 * 						String[] selectionArgs, 
	 * 						String groupBy, 
	 * 						String having, 
	 * 						String orderBy)
	 * 
	 * returns a cursor that contains all the entries ordered 
	 * from the latest date on the top
	 */
	public Cursor fetchAllEntriesByDate() {
		String orderBy = DatabaseHelper.COLUMN_YEAR + 
						 " DESC" +
						 DatabaseHelper.COMMA + 
						 DatabaseHelper.COLUMN_MONTH + 
						 " DESC" +
						 DatabaseHelper.COMMA + 
						 DatabaseHelper.COLUMN_DAY + 
						 " DESC";
		Cursor cursor = database.query(DatabaseHelper.TABLE_ENTRIES, allColumns, null, null, null, null, orderBy);
		
		if(cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	/* public Cursor query (String table, 
	 * 						String[] columns, 
	 * 						String selection, 
	 * 						String[] selectionArgs, 
	 * 						String groupBy, 
	 * 						String having, 
	 * 						String orderBy)
	 * 
	 * returns all the entries for the specified month
	 */
	public Cursor fetchAllEntriesForMonth(int month) {
		
		String selection = DatabaseHelper.COLUMN_MONTH + "=" + month;
		Cursor cursor = database.query(DatabaseHelper.TABLE_ENTRIES, allColumns, selection, null, null, null, null);
		
		if(cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}

}

package com.example.student_buy_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

	public DbOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static final int DATABASE_VERSION = 1;
	private static DbOpenHelper instance;
	 
	
	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(USERNAME_TABLE_CREATE);
//		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
//		db.execSQL(TOPUSER_TABLE_CREATE);
		 
		
	}

	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
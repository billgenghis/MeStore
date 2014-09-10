package com.smartx.bill.mepad.mestore.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.smartx.bill.mepad.mestore.entity.AppDownloading;

/**
 * @ClassName: DatabaseHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author coney Geng
 * @date 2014年9月10日 上午10:16:36
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "AppDownloadingsManager";

	// AppDownloadings table name
	private static final String TABLE_AppDownloadingS = "AppDownloadings";

	// AppDownloadings Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_CURRENT_SIZE = "current_size";
	private static final String KEY_ALL_SIZE = "all_size";
	private final ArrayList<AppDownloading> AppDownloading_list = new ArrayList<AppDownloading>();

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_AppDownloadingS_TABLE = "CREATE TABLE "
				+ TABLE_AppDownloadingS + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_CURRENT_SIZE + " INTEGER," + KEY_ALL_SIZE + " INTEGER"
				+ ")";
		db.execSQL(CREATE_AppDownloadingS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AppDownloadingS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new AppDownloading
	public void Add_AppDownloading(AppDownloading AppDownloading) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, AppDownloading.getAppName()); // Name
		values.put(KEY_CURRENT_SIZE, AppDownloading.getCurrentSize()); // current
																		// size
		values.put(KEY_ALL_SIZE, AppDownloading.getAllSize()); // all size
		// Inserting Row
		db.insert(TABLE_AppDownloadingS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single AppDownloading
	AppDownloading Get_AppDownloading(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_AppDownloadingS, new String[] { KEY_ID,
				KEY_NAME, KEY_CURRENT_SIZE, KEY_ALL_SIZE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		AppDownloading AppDownloading = new AppDownloading(
				Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor
						.getString(3)));
		// return AppDownloading
		cursor.close();
		db.close();

		return AppDownloading;
	}

	// Getting All AppDownloadings
	public ArrayList<AppDownloading> Get_AppDownloadings() {
		try {
			AppDownloading_list.clear();

			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_AppDownloadingS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					AppDownloading AppDownloading = new AppDownloading();
					AppDownloading.setID(Integer.parseInt(cursor.getString(0)));
					AppDownloading.setAppName(cursor.getString(1));
					AppDownloading.setCurrentSize(Integer.parseInt(cursor
							.getString(2)));
					AppDownloading.setAllSize(Integer.parseInt(cursor
							.getString(3)));
					// Adding AppDownloading to list
					AppDownloading_list.add(AppDownloading);
				} while (cursor.moveToNext());
			}

			// return AppDownloading list
			cursor.close();
			db.close();
			return AppDownloading_list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("all_AppDownloading", "" + e);
		}

		return AppDownloading_list;
	}

	// Updating single AppDownloading
	public int Update_AppDownloading(AppDownloading AppDownloading) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, AppDownloading.getAppName());
		values.put(KEY_CURRENT_SIZE, AppDownloading.getCurrentSize());
		values.put(KEY_ALL_SIZE, AppDownloading.getAllSize());

		// updating row
		return db.update(TABLE_AppDownloadingS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(AppDownloading.getID()) });
	}

	// Deleting single AppDownloading
	public void Delete_AppDownloading(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_AppDownloadingS, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting AppDownloadings Count
	public int Get_Total_AppDownloadings() {
		String countQuery = "SELECT  * FROM " + TABLE_AppDownloadingS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}

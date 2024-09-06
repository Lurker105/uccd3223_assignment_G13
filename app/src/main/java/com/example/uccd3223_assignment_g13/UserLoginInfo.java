package com.example.uccd3223_assignment_g13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserLoginInfo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DOB_DAY = "dob_day";
    private static final String COLUMN_DOB_MONTH = "dob_month";
    private static final String COLUMN_DOB_YEAR = "dob_year";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";

    public UserLoginInfo(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table with additional fields
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_DOB_DAY + " TEXT, " +
                COLUMN_DOB_MONTH + " TEXT, " +
                COLUMN_DOB_YEAR + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Add a new user
    public boolean addUser(String username, String password, String dobDay, String dobMonth, String dobYear, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DOB_DAY, dobDay);
        values.put(COLUMN_DOB_MONTH, dobMonth);
        values.put(COLUMN_DOB_YEAR, dobYear);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Check if a username already exists
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean result = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return result;
    }

    // Delete a user by username
    public boolean deleteUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            int result = db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
            cursor.close();
            db.close();
            return result > 0;
        } else {
            if (cursor != null) cursor.close();
            db.close();
            return false;
        }
    }
}

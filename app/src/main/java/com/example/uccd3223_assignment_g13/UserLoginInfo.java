package com.example.uccd3223_assignment_g13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserLoginInfo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final String TABLE_USERS = "users";

    // Column names
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DOB = "dob";           // New column for Date of Birth
    private static final String COLUMN_PHONE = "phone";       // New column for Phone number

    public UserLoginInfo(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table with the new columns for DOB and phone
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT NOT NULL," +
                COLUMN_DOB + " TEXT NOT NULL," +        // Adding DOB column
                COLUMN_PHONE + " TEXT NOT NULL)");      // Adding Phone column
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to register a new user with DOB and phone
    public boolean addUser(String username, String password, String dob, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DOB, dob);                 // Insert DOB into the database
        values.put(COLUMN_PHONE, phone);             // Insert phone number into the database

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Method to check if a user can log in with the provided username and password
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

    // Method to delete a user account if both username and password are correct
    public boolean deleteUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            // User found, proceed with deletion
            int result = db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
            cursor.close();
            db.close();
            return result > 0;
        } else {
            // User not found or incorrect password
            if (cursor != null) cursor.close();
            db.close();
            return false;
        }
    }
}

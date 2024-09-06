package com.example.uccd3223_assignment_g13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserLoginInfo extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "UserLoginInfo.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Columns
    private static final String TABLE_USERS = "UserLoginInfo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public UserLoginInfo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create UserLoginInfo table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Add new user
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        // Check if username already exists
        if (checkUserExists(username)) {
            return false;
        }

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Check if user exists
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Validate login credentials
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}

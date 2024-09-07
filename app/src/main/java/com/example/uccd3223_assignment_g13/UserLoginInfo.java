package com.example.uccd3223_assignment_g13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserLoginInfo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final String TABLE_USERS = "users";
    private static final String TAG = "UserLoginInfo";

    // Column names
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_PHONE = "phone";

    public UserLoginInfo(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating users table.");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT NOT NULL," +
                COLUMN_DOB + " TEXT NOT NULL," +
                COLUMN_PHONE + " TEXT NOT NULL)");
        Log.d(TAG, "Users table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database. Dropping old table.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to register a new user
    public boolean addUser(String username, String password, String dob, String phone) {
        if (userExists(username)) {
            Log.d(TAG, "Username already exists: " + username);
            return false;  // Username already exists, return false
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);

        if (result == -1) {
            Log.d(TAG, "Failed to insert user: " + username);
        } else {
            Log.d(TAG, "User successfully inserted: " + username);
        }

        db.close();
        return result != -1;  // Return true if insert was successful
    }

    // Method to check if a user already exists by username
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.d(TAG, "User existence check for " + username + ": " + exists);
        return exists;
    }

    // Method to check if the username and password are correct for login
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean result = cursor != null && cursor.moveToFirst();  // Check if a match was found
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.d(TAG, "Login check for user " + username + ": " + result);
        return result;
    }

    // Method to delete a user account if both username and password are correct
    public boolean deleteUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int result = db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
            cursor.close();
            db.close();
            Log.d(TAG, "User deleted: " + username);
            return result > 0;
        } else {
            if (cursor != null) cursor.close();
            db.close();
            Log.d(TAG, "Failed to delete user: " + username);
            return false;
        }
    }

    // Method to get user info (username, dob, phone)
    public Cursor getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, new String[]{COLUMN_USERNAME, COLUMN_DOB, COLUMN_PHONE},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
    }
}

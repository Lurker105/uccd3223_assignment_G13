package com.example.uccd3223_assignment_g13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class ExpenseDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "ExpenseDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "myexpense";

    private static final String ID_COL = "id";

    public static final String AMOUNT_COL = "amount";
    public static final String CATEGORY_COL = "category";
    private static final String DESCRIPTION_COL = "description";
    public static final String DATE_COL = "date";


    // creating a constructor for our database handler.
    public ExpenseDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AMOUNT_COL + " NUM,"
                + CATEGORY_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + DATE_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewExpenses(String Amount, String Category, String Description, String Date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT_COL, Amount);
        values.put(CATEGORY_COL, Category);
        values.put(DESCRIPTION_COL, Description);
        values.put(DATE_COL, Date);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public ArrayList<ExpenseModal> viewExpense(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorExpense = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<ExpenseModal> ExpenseModalArrayList = new ArrayList<>();

        if (cursorExpense.moveToFirst()){
            do {
                ExpenseModalArrayList.add(new ExpenseModal(cursorExpense.getString(1),
                        cursorExpense.getString(4),cursorExpense.getString(2),
                        cursorExpense.getString(3)));
            } while (cursorExpense.moveToNext());
        }

        cursorExpense.close();
        return ExpenseModalArrayList;
    }

    public void updateExpense(String originalExpenseCat, String amount, String date,
                              String category, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AMOUNT_COL, amount);
        values.put(CATEGORY_COL, category);
        values.put(DESCRIPTION_COL, description);
        values.put(DATE_COL, date);

        db.update(TABLE_NAME, values, "category=?", new String[]{originalExpenseCat});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getMonth_Year(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{DATE_COL},
                null, null, DATE_COL, null, null,null);
    }

    public Cursor getMonthlyCost(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ CATEGORY_COL + ", SUM(" + AMOUNT_COL + ") AS " + AMOUNT_COL +
                " FROM " + TABLE_NAME +
                " WHERE "+ DATE_COL + " LIKE '%" + date +
                "' GROUP BY " + CATEGORY_COL;
        return db.rawQuery(query,null);
    }

    public Cursor getDairyCost(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ DATE_COL + ", SUM(" + AMOUNT_COL + ") AS " + AMOUNT_COL +
                " FROM " + TABLE_NAME +
                " WHERE "+ DATE_COL + " = '" + date +
                "' GROUP BY " + DATE_COL;
        return db.rawQuery(query,null);
    }

}

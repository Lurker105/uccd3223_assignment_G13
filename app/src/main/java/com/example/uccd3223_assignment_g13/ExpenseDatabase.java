package com.example.uccd3223_assignment_g13;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1)
public abstract class ExpenseDatabase extends  RoomDatabase{


    private static ExpenseDatabase instance;
    
    // Define a method to access the DAO
    public abstract ExpenseDao expenseDao();

    // Volatile ensures that the instance is up-to-date and visible to all threads
    private static volatile ExpenseDatabase INSTANCE;

    // Get the singleton instance of the database
    public static ExpenseDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenseDatabase.class) {
                if (INSTANCE == null) {
                    // Build the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ExpenseDatabase.class, "expense_database")
                            .fallbackToDestructiveMigration()  // This will reset the DB if migration fails
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

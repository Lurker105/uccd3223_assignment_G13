package com.example.uccd3223_assignment_g13;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface ExpenseDao {

    @Insert
    void insertExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("SELECT * FROM expenses")
    List<Expense> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE id = :id")
    Expense getExpenseById(int id);
}

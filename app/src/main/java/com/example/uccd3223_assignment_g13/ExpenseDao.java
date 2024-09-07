package com.example.uccd3223_assignment_g13;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM Expense")
    List<Expense> getAllExpenses();



    @Insert
    void insertExpense(Expense expense);


    @Query("SELECT * FROM Expense")
    List<Expense> getAll();




}

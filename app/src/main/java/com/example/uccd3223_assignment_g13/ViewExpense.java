package com.example.uccd3223_assignment_g13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {

    private ArrayList<ExpenseModal> expenseModalArrayList;
    private ExpenseDatabase ExpenseDB;
    private ExpenseRVAdapter expenseRVAdapter;
    private RecyclerView expenseRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        // initializing all variables.
        expenseModalArrayList = new ArrayList<>();
        ExpenseDB = new ExpenseDatabase(ViewExpense.this);

        // getting our expense array
        // list from db handler class
        expenseModalArrayList = ExpenseDB.viewExpense();

        expenseRVAdapter = new ExpenseRVAdapter(expenseModalArrayList, ViewExpense.this);
        expenseRV = findViewById(R.id.idRVExpense);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewExpense.this,
                RecyclerView.VERTICAL, false);
        expenseRV.setLayoutManager(linearLayoutManager);
        
        expenseRV.setAdapter(expenseRVAdapter);

    }
}
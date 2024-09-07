package com.example.uccd3223_assignment_g13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    private EditText amountInput, categoryInput, descInput;
    private TextView dateInput;
    private Button addButton;
    private ExpenseDatabase expenseDatabase;
    private String selectedDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // set size
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        amountInput = findViewById(R.id.amount_input);
        categoryInput = findViewById(R.id.category_input);
        descInput = findViewById(R.id.desc_input);
        dateInput = findViewById(R.id.date_input);
        addButton = findViewById(R.id.add_button);

        expenseDatabase = ExpenseDatabase.getInstance(this);

        dateInput.setOnClickListener(v -> showDatePickerDialog());

        addButton.setOnClickListener(v -> saveExpense());

    }

    private void showDatePickerDialog() {

        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the TextView with the selected date
                        selectedMonth++;
                        String selectedDate = selectedDay + "/" + selectedMonth + "/" +selectedYear;
                        dateInput.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    private void saveExpense() {
        String amountText = amountInput.getText().toString().trim();
        String category = categoryInput.getText().toString().trim();
        String desc = descInput.getText().toString().trim();

        if(TextUtils.isEmpty(amountText) || TextUtils.isEmpty(category) || TextUtils.isEmpty(selectedDate)) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);

        Expense expense = new Expense(amount, category, desc, selectedDate);

        new Thread(() -> {
            expenseDatabase.expenseDao().insertExpense(expense);
                runOnUiThread(() -> {
                    Toast.makeText(AddExpense.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                });
        }).start();

    }


}
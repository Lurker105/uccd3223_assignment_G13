package com.example.uccd3223_assignment_g13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateExpense extends AppCompatActivity {
    private EditText AmountEdt, CategoryEdt, DescriptionEdt;
    private TextView DateEdt;
    private Button updateExpense, deleteExpense;
    private ExpenseDatabase ExpenseDB;
    String expenseAmount, expenseCat, expenseDesc, expenseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        AmountEdt = findViewById(R.id.EdtAmount);
        CategoryEdt = findViewById(R.id.EdtCategory);
        DescriptionEdt = findViewById(R.id.EdtDesc);
        DateEdt = findViewById(R.id.EdtDate);
        updateExpense = findViewById(R.id.idBtnUpdateExpense);

        ExpenseDB = new ExpenseDatabase(UpdateExpense.this);

        DateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        expenseAmount = getIntent().getStringExtra("amount");
        expenseCat = getIntent().getStringExtra("category");
        expenseDesc = getIntent().getStringExtra("description");
        expenseDate = getIntent().getStringExtra("date");

        AmountEdt.setText(expenseAmount);
        CategoryEdt.setText(expenseCat);
        DescriptionEdt.setText(expenseDesc);
        DateEdt.setText(expenseDate);

        updateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseDB.updateExpense(expenseCat, AmountEdt.getText().toString(),
                        CategoryEdt.getText().toString(), DescriptionEdt.getText().toString(),
                        DateEdt.getText().toString());

                Toast.makeText(UpdateExpense.this, "Course Updated",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdateExpense.this, AddExpense.class);
                startActivity(i);
            }
        });

       /* deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call a method to delete expense
                dbHandler.deleteExpense(expenseCat);
                Toast.makeText(UpdateExpense.this, "Deleted the expense",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateExpense.this, AddExpense.class);
                startActivity(i);
            }
        });*/
    }

    private void showDatePickerDialog(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpdateExpense.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the dateInput with the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        DateEdt.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();

    }
}
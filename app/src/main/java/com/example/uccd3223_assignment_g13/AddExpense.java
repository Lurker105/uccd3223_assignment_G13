package com.example.uccd3223_assignment_g13;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    private EditText amountInput, descInput;
    private Spinner categoryInput;
    private Button addButton;

    private TextView dateInput;
    private ExpenseDatabase ExpenseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        amountInput = findViewById(R.id.amount_input);
        categoryInput = findViewById(R.id.category_input);
        descInput = findViewById(R.id.desc_input);
        dateInput = findViewById(R.id.date_input);
        addButton = findViewById(R.id.add_button);

        String[] category_arr = {"Food&Drink", "Shop", "Transaction", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category_arr){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextSize(20); // Set the text size here
                return view;
            }
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextSize(20); // Set the text size here
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryInput.setAdapter(adapter);


       ExpenseDB = new ExpenseDatabase(AddExpense.this);

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = amountInput.getText().toString();
                String category = category_arr[categoryInput.getSelectedItemPosition()];
                String description = descInput.getText().toString();
                String date = dateInput.getText().toString();

                if (amount.isEmpty() || category.isEmpty() || description.isEmpty() || date.isEmpty()){
                    Toast.makeText(AddExpense.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExpenseDB.addNewExpenses(amount, category, description, date);

                Toast.makeText(AddExpense.this, "Saved", Toast.LENGTH_SHORT).show();

                amountInput.setText("");
                descInput.setText("");
                dateInput.setText("");
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddExpense.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the dateInput with the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateInput.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }


}
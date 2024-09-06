package com.example.uccd3223_assignment_g13;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput, dobDayInput, dobMonthInput, dobYearInput, phoneNumberInput;
    private Button registerButton;
    private UserLoginInfo userLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // UI
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        dobDayInput = findViewById(R.id.dobDayInput);
        dobMonthInput = findViewById(R.id.dobMonthInput);
        dobYearInput = findViewById(R.id.dobYearInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        registerButton = findViewById(R.id.registerButton);
        userLoginInfo = new UserLoginInfo(this);

        // Register button click
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String dobDay = dobDayInput.getText().toString().trim();
        String dobMonth = dobMonthInput.getText().toString().trim();
        String dobYear = dobYearInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();

        if (validateInput(username, password, dobDay, dobMonth, dobYear, phoneNumber)) {
            // Add user to the database
            boolean isAdded = userLoginInfo.addUser(username, password, dobDay, dobMonth, dobYear, phoneNumber);

            if (isAdded) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after registration
            } else {
                Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput(String username, String password, String dobDay, String dobMonth, String dobYear, String phoneNumber) {
        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Username cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(dobDay) || TextUtils.isEmpty(dobMonth) || TextUtils.isEmpty(dobYear)) {
            Toast.makeText(this, "Please enter a valid date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate date of birth (e.g., checking valid day/month ranges)
        int day = Integer.parseInt(dobDay);
        int month = Integer.parseInt(dobMonth);
        int year = Integer.parseInt(dobYear);

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2023) {
            Toast.makeText(this, "Please enter a valid date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate phone number format (XXX-XXXXXXX)
        if (!phoneNumber.matches("\\d{3}-\\d{7}")) {
            phoneNumberInput.setError("Invalid phone number format. Use XXX-XXXXXXX");
            return false;
        }

        return true;
    }
}

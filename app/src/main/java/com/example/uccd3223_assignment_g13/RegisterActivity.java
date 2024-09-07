package com.example.uccd3223_assignment_g13;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput, dobInput, phoneInput;
    private Button registerButton, loginButton;
    private UserLoginInfo userLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        dobInput = findViewById(R.id.dobInput);  // Date of Birth input field
        phoneInput = findViewById(R.id.phoneInput); // Phone number input field
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        userLoginInfo = new UserLoginInfo(this);

        // Register button logic
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Login button logic
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String dob = dobInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        // Validate user input
        if (validateInput(username, password, dob, phone)) {
            if (userLoginInfo.addUser(username, password, dob, phone)) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Username already exists. Please choose another one.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Input validation
    private boolean validateInput(String username, String password, String dob, String phone) {
        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Username cannot be empty");
            return false;
        }
        if (username.length() < 4) {
            usernameInput.setError("Username must be at least 4 characters");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty");
            return false;
        }
        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return false;
        }
        if (!validateDOB(dob)) {
            dobInput.setError("Invalid DOB. Use format: YYYY-MM-DD");
            return false;
        }
        if (!validatePhoneNumber(phone)) {
            phoneInput.setError("Invalid phone number. Use format: XXX-XXXXXXX");
            return false;
        }
        return true;
    }

    // Method to validate Date of Birth in YYYY-MM-DD format
    private boolean validateDOB(String dob) {
        // Use regex to check for valid YYYY-MM-DD format
        String dobPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return Pattern.compile(dobPattern).matcher(dob).matches();
    }

    // Method to validate phone number format XXX-XXXXXXX
    private boolean validatePhoneNumber(String phone) {
        // Use regex to check for phone number format
        String phonePattern = "^\\d{3}-\\d{7}$";
        return Pattern.compile(phonePattern).matcher(phone).matches();
    }
}

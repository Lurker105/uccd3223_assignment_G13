package com.example.uccd3223_assignment_g13;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        dobInput = findViewById(R.id.dobInput);
        phoneInput = findViewById(R.id.phoneInput);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        userLoginInfo = new UserLoginInfo(this);

        // Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Login button
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

        Log.d(TAG, "Registering user with username: " + username);  // Debug log

        // Validate user input
        if (validateInput(username, password, dob, phone)) {
            Log.d(TAG, "Checking if username exists: " + username);
            if (userLoginInfo.userExists(username)) {
                Toast.makeText(RegisterActivity.this, "Username already exists. Please choose another one.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Username already exists: " + username);
            } else {
                Log.d(TAG, "Username does not exist, proceeding to register.");
                // Proceed to register the new user if the username is unique
                boolean isUserAdded = userLoginInfo.addUser(username, password, dob, phone);
                Log.d(TAG, "Result of addUser: " + isUserAdded);
                if (isUserAdded) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "User registration successful for username: " + username);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "User registration failed for username: " + username);
                }
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
            dobInput.setError("Invalid DOB. Use format: DD-MM-YYYY");
            return false;
        }
        if (!validatePhoneNumber(phone)) {
            phoneInput.setError("Invalid phone number. Use format: XXX-XXXXXXX");
            return false;
        }
        return true;
    }

    private boolean validateDOB(String dob) {
        String dobPattern = "^\\d{2}-\\d{2}-\\d{4}$";
        return Pattern.compile(dobPattern).matcher(dob).matches();
    }

    private boolean validatePhoneNumber(String phone) {
        String phonePattern = "^\\d{3}-\\d{7}$";
        return Pattern.compile(phonePattern).matcher(phone).matches();
    }
}

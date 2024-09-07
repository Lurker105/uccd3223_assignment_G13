package com.example.uccd3223_assignment_g13;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button registerButton, loginButton;
    private UserLoginInfo userLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // Ensure this layout file is correct

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        // If there’s a login button, initialize it; ensure it's added in the layout
        loginButton = findViewById(R.id.loginButton);  // If not in layout, ensure you add this

        // Initialize user login info
        userLoginInfo = new UserLoginInfo(this);

        // Set click listeners for register and login buttons
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validate user input
        if (validateInput(username, password)) {
            // Add user and check if the username already exists
            if (userLoginInfo.addUser(username, password)) {
                // Show success message and navigate to login activity
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();  // Finish this activity to prevent user from returning to it
            } else {
                Toast.makeText(RegisterActivity.this, "Username already exists. Please choose another one.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to validate the username and password input fields
    private boolean validateInput(String username, String password) {
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
        return true;
    }
}

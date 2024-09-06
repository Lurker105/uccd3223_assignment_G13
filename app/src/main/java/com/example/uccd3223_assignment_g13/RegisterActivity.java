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
    private UserLoginInfo userLoginInfo;  // Updated to UserLoginInfo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        userLoginInfo = new UserLoginInfo(this);  // Initialize UserLoginInfo

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

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

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();
        } else if (userLoginInfo.addUser(username, password)) {
            // Successful registration
            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else {
            // Username already taken
            Toast.makeText(RegisterActivity.this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
        }
    }
}

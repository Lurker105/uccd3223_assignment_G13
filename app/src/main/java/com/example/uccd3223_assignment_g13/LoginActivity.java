package com.example.uccd3223_assignment_g13;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, registerButton;
    private UserLoginInfo userLoginInfo;  // Updated to UserLoginInfo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        userLoginInfo = new UserLoginInfo(this);  // Initialize UserLoginInfo

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();
        } else if (userLoginInfo.checkLogin(username, password)) {
            // Successful login
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            // Invalid credentials
            Toast.makeText(LoginActivity.this, "Invalid login. Please register.", Toast.LENGTH_SHORT).show();
        }
    }
}

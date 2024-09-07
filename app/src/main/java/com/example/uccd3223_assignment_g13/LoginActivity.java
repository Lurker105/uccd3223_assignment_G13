package com.example.uccd3223_assignment_g13;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, registerButton, deleteAccountButton;
    private CheckBox rememberMeCheckBox;
    private UserLoginInfo userLoginInfo;
    private boolean passwordVisible = false; // Track password visibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);


        userLoginInfo = new UserLoginInfo(this);

        // Set initial icon for password visibility off
        setIconSize(passwordInput, R.drawable.ic_visibility_off, 40, 30);

        // Toggle password visibility on touch
        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[2].getBounds().width())) {
                        // Toggle password visibility
                        if (passwordVisible) {
                            passwordInput.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            setIconSize(passwordInput, R.drawable.ic_visibility_off, 40, 30); // Set visibility off icon
                            passwordVisible = false;
                        } else {
                            passwordInput.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            setIconSize(passwordInput, R.drawable.ic_visibility, 40, 30); // Set visibility on icon
                            passwordVisible = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Handle register button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Handle delete account button click
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAccount(); // Ask for confirmation before deletion
            }
        });
    }

    // Confirm if the user wants to delete their account
    private void confirmDeleteAccount() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Enter your username to delete the account");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Enter your password to delete the account");
            return;
        }

        // Create an AlertDialog to confirm deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAccount(username, password);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // Delete the account if the username and password match
    private void deleteAccount(String username, String password) {
        boolean isDeleted = userLoginInfo.deleteUser(username, password);

        if (isDeleted) {
            Toast.makeText(LoginActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username or password. Account not deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(username, password)) {
            if (userLoginInfo.checkLogin(username, password)) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                // If login fails, prompt the user to register
                showRegisterPrompt();
            }
        }
    }

    // Show an AlertDialog prompting the user to register if login fails
    private void showRegisterPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Failed");
        builder.setMessage("Invalid login. Would you like to register a new account?");
        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

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

    // Utility method to resize and set icons to 40dp
    private void setIconSize(EditText editText, int drawableRes, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableRes);
        if (drawable != null) {
            drawable.setBounds(0, 0, (int) (width * getResources().getDisplayMetrics().density), (int) (height * getResources().getDisplayMetrics().density));
            editText.setCompoundDrawables(null, null, drawable, null);
        }
    }
}

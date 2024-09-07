package com.example.uccd3223_assignment_g13;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, registerButton, deleteAccountButton;
    private UserLoginInfo userLoginInfo;
    private boolean passwordVisible = false;
    private static final String TAG = "LoginActivity";
    private static final String SHARED_PREF_NAME = "account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        userLoginInfo = new UserLoginInfo(this);

        // Set initial icon for password visibility off
        setIconSize(passwordInput, R.drawable.ic_visibility_off, 40, 30);

        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[2].getBounds().width())) {
                        // Toggle password visibility
                        if (passwordVisible) {
                            passwordInput.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            setIconSize(passwordInput, R.drawable.ic_visibility_off, 40, 30);
                            passwordVisible = false;
                        } else {
                            passwordInput.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            setIconSize(passwordInput, R.drawable.ic_visibility, 40, 30);
                            passwordVisible = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

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

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAccount();
            }
        });
    }

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

    private void deleteAccount(String username, String password) {
        Log.d(TAG, "Attempting to delete account for user: " + username);
        boolean isDeleted = userLoginInfo.deleteUser(username, password);

        if (isDeleted) {
            Toast.makeText(LoginActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Account deleted successfully for user: " + username);
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username or password. Account not deleted.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Account deletion failed for user: " + username);
        }
    }

    private void loginUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        Cursor cursor = userLoginInfo.getUserInfo(username);

        if (validateInput(username, password)) {
            Log.d(TAG, "Validating login for user: " + username);

            if (userLoginInfo.checkLogin(username, password)) {
                // If login successful, retrieve dob and phone, then save to SharedPreferences
                if (cursor.moveToFirst()) {
                    String dob = cursor.getString(cursor.getColumnIndex(UserLoginInfo.COLUMN_DOB));
                    String phone = cursor.getString(cursor.getColumnIndex(UserLoginInfo.COLUMN_PHONE));
                    cursor.close();

                    // Store user details in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("dob", dob);
                    editor.putString("phone", phone);
                    editor.apply();  // Commit changes

                    // Navigate to MainActivity or any other page
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                Log.d(TAG, "Login failed for user: " + username);
                showRegisterPrompt();
            }
        }
    }

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

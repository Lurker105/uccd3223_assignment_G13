package com.example.uccd3223_assignment_g13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent in_back = new Intent(account.this, MainActivity.class);
                //startActivity(in_back);
                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);



        Button bt_name = (Button) findViewById(R.id.name);
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("name",bt_name,"Name: ");
            }
        });
        Button bt_dob = (Button) findViewById(R.id.dob);
        bt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("dob",bt_dob,"Date of Birth: ");
            }
        });
        Button bt_phone = (Button) findViewById(R.id.phone);
        bt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("phone",bt_phone,"Phone number: ");
            }
        });

        bt_name.setText("Name: " + pref.getString("name",""));
        bt_dob.setText("Date of Birth: " + pref.getString("dob",""));
        bt_phone.setText("Phone number: " + pref.getString("phone",""));
    }
    public void changeInfo(String type, Button bt, String text){
        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText et = new EditText(this);
        builder.setCancelable(false);
        builder.setTitle(type);
        builder.setView(et);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(et.getText().toString()!=""){
                    prefEd.putString(type,et.getText().toString());
                    prefEd.commit();
                    bt.setText(text + pref.getString(type,""));
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
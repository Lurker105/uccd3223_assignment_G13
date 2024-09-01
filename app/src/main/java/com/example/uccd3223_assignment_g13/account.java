package com.example.uccd3223_assignment_g13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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

        // back to setting
        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // account data save in shared proferences
        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);

        // Name change
        Button bt_name = (Button) findViewById(R.id.name);
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("name",bt_name,"Name: ",
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            }
        });

        // Date of birth change
        Button bt_dob = (Button) findViewById(R.id.dob);
        bt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("dob",bt_dob,"Date of Birth: ",
                        InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
            }
        });

        // Phone number change
        Button bt_phone = (Button) findViewById(R.id.phone);
        bt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("phone",bt_phone,"Phone number: ",
                        InputType.TYPE_CLASS_PHONE);
            }
        });

        // Budget change
        Button bt_budget = (Button) findViewById(R.id.budget);
        bt_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("budget",bt_budget,"Monthly budget: RM",
                        InputType.TYPE_CLASS_NUMBER);
            }
        });


        // Default value
        bt_name.setText("Name: " + pref.getString("name",""));
        bt_dob.setText("Date of Birth: " + pref.getString("dob",""));
        bt_phone.setText("Phone number: " + pref.getString("phone",""));
        bt_budget.setText("Monthly budget: RM" + pref.getString("budget",""));
    }

    // change button text
    public void changeInfo(String type, Button bt, String text, int inType){
        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText et = new EditText(this);
        et.setInputType(inType);
        builder.setCancelable(false);
        if(type == "dob"){
           builder.setTitle("Date of birth(DD/MM/YYYY)");
        }
        else builder.setTitle(type);
        builder.setView(et);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(type == "dob"&&!isValidDate(et.getText().toString())){
                    return;
                }
                if(et.getText().toString()=="") return;
                prefEd.putString(type,et.getText().toString());
                prefEd.commit();
                bt.setText(text + pref.getString(type,""));
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

    // check is valid date
    public boolean isValidDate(String date){
        if(date.equals("")) return false;
        String[] date_arr = date.split("/");
        if(date_arr.length!=3) return false;
        int day = Integer.parseInt(date_arr[0]),
                month = Integer.parseInt(date_arr[1]),
                year = Integer.parseInt(date_arr[2]);
        if(year < 0 || month < 0 || month > 12 || day < 0 || day > 31) return false;
        int[] have30day = {4,6,9,11};
        if(date_arr[1].equals(have30day) && day > 30) return false;    // both month no have 31
        if(month == 2){
            if(year%4 != 0 && day == 29) return false;  //
            if(day > 29) return false;  // february no have 30
        }
        return true;
    }
}
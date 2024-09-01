package com.example.uccd3223_assignment_g13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // set size
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        TextView tv = (TextView) findViewById(R.id.title);
        Button bt_name = (Button) findViewById(R.id.name);
        Button bt_dob = (Button) findViewById(R.id.dob);
        Button bt_phone = (Button) findViewById(R.id.phone);
        Button bt_budget = (Button) findViewById(R.id.budget);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
        bt_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_dob.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_phone.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_budget.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));

        // back to setting
        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // account data save in shared proferences
        pref = getSharedPreferences("account",MODE_PRIVATE);

        // Name change
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("name",bt_name,"Name: ",
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            }
        });

        // Date of birth change
        bt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("dob",bt_dob,"Date of Birth: ",
                        InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
            }
        });

        // Phone number change
        bt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo("phone",bt_phone,"Phone number: ",
                        InputType.TYPE_CLASS_PHONE);
            }
        });

        // Budget change
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
package com.example.uccd3223_assignment_g13;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        // set appearence
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        LinearLayout ll = (LinearLayout) findViewById(R.id.bgAbo);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        TextView tv0 = (TextView) findViewById(R.id.tv0);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        TextView tv5 = (TextView) findViewById(R.id.tv5);
        TextView tv6 = (TextView) findViewById(R.id.tv6);

        switch (pref.getString("color","white")){
            case "white":
                ll.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case "blue":
                ll.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "green":
                ll.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
        tv0.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));

        // back to setting
        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
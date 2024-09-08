package com.example.uccd3223_assignment_g13;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class appearance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);

        // set appearence
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        LinearLayout ll = (LinearLayout) findViewById(R.id.bgApp);
        TextView tv = (TextView) findViewById(R.id.title);
        TextView tv_size = (TextView) findViewById(R.id.sizeTitle);
        RadioButton rb_small = (RadioButton) findViewById(R.id.small);
        RadioButton rb_mid = (RadioButton) findViewById(R.id.medium);
        RadioButton rb_large = (RadioButton) findViewById(R.id.large);
        TextView tv_color = (TextView) findViewById(R.id.colorTitle);
        RadioButton rb_white = (RadioButton) findViewById(R.id.white);
        RadioButton rb_blue = (RadioButton) findViewById(R.id.blue);
        RadioButton rb_green = (RadioButton) findViewById(R.id.green);
        Button bt_apply = (Button) findViewById(R.id.apply);


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
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
        tv_size.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_small.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_mid.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_large.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv_color.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_white.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_blue.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_green.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_apply.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));

        // back to setting
        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                if(rb_small.isChecked()){
                    prefEd.putInt("size",12);
                    prefEd.commit();
                }
                if(rb_mid.isChecked()){
                    prefEd.putInt("size",24);
                    prefEd.commit();
                }
                if(rb_large.isChecked()){
                    prefEd.putInt("size",36);
                    prefEd.commit();
                }
                if(rb_white.isChecked()){
                    prefEd.putString("color","white");
                    prefEd.commit();
                    ll.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(rb_blue.isChecked()){
                    prefEd.putString("color","blue");
                    prefEd.commit();
                    ll.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                if(rb_green.isChecked()){
                    prefEd.putString("color","green");
                    prefEd.commit();
                    ll.setBackgroundColor(getResources().getColor(R.color.green));
                }

                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
                tv_size.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_small.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_mid.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_large.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                tv_color.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_white.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_blue.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_green.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                bt_apply.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
            }
        });
    }

}
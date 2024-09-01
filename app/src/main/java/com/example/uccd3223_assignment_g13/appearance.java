package com.example.uccd3223_assignment_g13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class appearance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);

        // set size
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        TextView tv = (TextView) findViewById(R.id.title);
        RadioButton rb_small = (RadioButton) findViewById(R.id.small);
        RadioButton rb_mid = (RadioButton) findViewById(R.id.medium);
        RadioButton rb_large = (RadioButton) findViewById(R.id.large);
        Button bt_apply = (Button) findViewById(R.id.apply);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
        rb_small.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_mid.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        rb_large.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
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

                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
                rb_small.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_mid.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                rb_large.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
                bt_apply.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
            }
        });
    }

}
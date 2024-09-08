package com.example.uccd3223_assignment_g13;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class privacy extends AppCompatActivity {
    public UserLoginInfo userLoginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        userLoginInfo = new UserLoginInfo(this);

        // set appearence
        SharedPreferences pref = getSharedPreferences("appearance",MODE_PRIVATE);
        SharedPreferences.Editor prefEd = pref.edit();

        LinearLayout ll = (LinearLayout) findViewById(R.id.bgPri);
        TextView tv = (TextView) findViewById(R.id.title);
        TextView tv_old = (TextView) findViewById(R.id.tv_oldpass);
        EditText et_old = (EditText) findViewById(R.id.et_oldpass);
        TextView tv_new = (TextView) findViewById(R.id.tv_newpass);
        EditText et_new = (EditText) findViewById(R.id.et_newpass);
        Button bt_submit = (Button) findViewById(R.id.submitPass);

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
        tv_old.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        et_old.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        tv_new.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        et_new.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_submit.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);

        // back to setting
        Button bt_back = (Button) findViewById(R.id.back_setting);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // submit button
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
                SharedPreferences.Editor prefEd = pref.edit();
                String password0 = pref.getString("password", "");
                if(et_old.getText().toString().equals(password0)){
                    if(et_new.getText().toString()!=""){
                        prefEd.putString("password",et_new.getText().toString());
                        prefEd.commit();
                        String name = pref.getString("username","");
                        String password = pref.getString("password", "");
                        String dob = pref.getString("dob","");
                        String phone = pref.getString("phone","");
                        boolean b1 = userLoginInfo.deleteUser(name,password0);
                        boolean b2 = userLoginInfo.addUser(name,password,dob,phone);
                        finish();
                    }
                }
            }
        });
    }
}
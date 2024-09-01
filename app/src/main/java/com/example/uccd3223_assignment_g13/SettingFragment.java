package com.example.uccd3223_assignment_g13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // set size
        SharedPreferences pref = getActivity().getSharedPreferences("appearance",0);
        SharedPreferences.Editor prefEd = pref.edit();

        TextView tv = (TextView) getView().findViewById(R.id.title);
        Button bt_acc = (Button) getView().findViewById(R.id.account);
        Button bt_noti = (Button) getView().findViewById(R.id.notification);
        Button bt_appe = (Button) getView().findViewById(R.id.appearance);
        Button bt_priv = (Button) getView().findViewById(R.id.security);
        Button bt_about = (Button) getView().findViewById(R.id.about);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12)+12);
        bt_acc.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_noti.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_appe.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_priv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_about.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));

        // account
        bt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_acc = new Intent(getActivity().getApplication(), account.class);
                startActivity(in_acc);
            }
        });

        // notification
        // appearance
        bt_appe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_appe = new Intent(getActivity().getApplication(), appearance.class);
                startActivity(in_appe);
            }
        });

        // privacy
        // about us
        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_about = new Intent(getActivity().getApplication(), aboutus.class);
                startActivity(in_about);
            }
        });

    }

}
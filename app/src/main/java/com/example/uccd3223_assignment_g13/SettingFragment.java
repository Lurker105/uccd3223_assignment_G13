package com.example.uccd3223_assignment_g13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

        LinearLayout ll = (LinearLayout) getView().findViewById(R.id.bgSet);
        TextView tv = (TextView) getView().findViewById(R.id.title);
        Button bt_acc = (Button) getView().findViewById(R.id.account);
        Button bt_appe = (Button) getView().findViewById(R.id.appearance);
        Button bt_priv = (Button) getView().findViewById(R.id.security);
        Button bt_about = (Button) getView().findViewById(R.id.about);
        Button bt_logout = (Button) getView().findViewById(R.id.logout);

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
        bt_acc.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_appe.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_priv.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_about.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));
        bt_logout.setTextSize(TypedValue.COMPLEX_UNIT_SP,pref.getInt("size",12));

        // account
        bt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_acc = new Intent(getActivity().getApplication(), account.class);
                startActivity(in_acc);
            }
        });

        // appearance
        bt_appe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_appe = new Intent(getActivity().getApplication(), appearance.class);
                startActivity(in_appe);
            }
        });

        // privacy
        bt_priv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_priv = new Intent(getActivity().getApplication(), privacy.class);
                startActivity(in_priv);
            }
        });

        // about us
        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in_about = new Intent(getActivity().getApplication(), aboutus.class);
                startActivity(in_about);
            }
        });

        // logout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Comfirm to logout?");
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in_logout = new Intent(getActivity().getApplication(), LoginActivity.class);
                        startActivity(in_logout);
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
        });

    }

}
package com.example.uccd3223_assignment_g13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TransactionFragment transactionFragment = new TransactionFragment();
        HomeFragment homeFragment = new HomeFragment();
        SettingFragment settingFragment = new SettingFragment();

        BottomNavigationView BNV = findViewById(R.id.bottomNavigationView);
        BNV.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.transaction){
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, transactionFragment).commit();
                }
                else if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                }
                else if (item.getItemId() == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingFragment).commit();
                }
                else{
                    return false;
                }
                return true;
            }
        });


    }

}
//TIO HENG YU
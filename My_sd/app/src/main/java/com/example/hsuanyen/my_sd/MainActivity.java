package com.example.hsuanyen.my_sd;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity   {

    private LinearLayout VL;
    private FrameLayout FL;
    private LinearLayout MCL;
    private LinearLayout BMCL;
    protected TextView mTextMessage;
    protected BottomNavigationView BNV;
    protected int CurrentMenuItem= 0;//紀錄目前User位於哪一個項目
    SharedPreferences preferences;
   // SharedPreferences.Editor editor=preferences.edit();
    String user_n;





    public void setUser_n(String user_n) {
        this.user_n = user_n;
    }



    public String getUser_n() {
        return user_n;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //Toast.makeText(this, "setcontent中", Toast.LENGTH_SHORT).show();
        VL = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
        FL = (FrameLayout) VL.findViewById(R.id.content);
        BMCL=(LinearLayout)FL.findViewById(R.id.BMCL);
        MCL=(LinearLayout) BMCL.findViewById(R.id.mcontent);
        mTextMessage=(TextView) MCL.findViewById(R.id.message);
        BNV = (BottomNavigationView) VL.findViewById(R.id.navigation);
        getLayoutInflater().inflate(layoutResID,BMCL, true);
        super.setContentView(VL);
        setUpNavigation();
    }
    private void setUpNavigation() {
        // Set navigation item selected listener
        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_secret:
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.navigation_create:
                        Intent intent2 = new Intent();
                        intent2.setClass(MainActivity.this, CreateActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.navigation_base:
                        Intent intent3 = new Intent();
                        intent3.setClass(MainActivity.this, BaseActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.navigation_setting:
                        Intent intent4 = new Intent();
                        intent4.setClass(MainActivity.this, SettingActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }

        });
    }
}

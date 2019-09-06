package com.example.hsuanyen.my_sd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = SplashActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
                String username = preferences.getString("username",null);
                String password = preferences.getString("password",null);
                String language=preferences.getString("language",null);
                Resources res=getResources();
                Configuration conf;
               // Toast.makeText(SplashActivity.this,"語言"+language,Toast.LENGTH_LONG).show();
                if(language!=null){
                    switch (language){
                        case "繁體中文":
                            conf=res.getConfiguration();
                            conf.locale= Locale.TAIWAN;
                            res.updateConfiguration(conf,res.getDisplayMetrics());
                            break;
                        case "简体中文":
                            conf=res.getConfiguration();
                            conf.locale= Locale.CHINA;
                            res.updateConfiguration(conf,res.getDisplayMetrics());
                            break;
                        case "English":
                            conf=res.getConfiguration();
                            conf.locale= Locale.US;
                            res.updateConfiguration(conf,res.getDisplayMetrics());
                            break;
                    }

                }
                if(username!=null && password!=null){
                    Toast.makeText(SplashActivity.this,getResources().getString(R.string.toast_welcome)+username, Toast.LENGTH_LONG).show();
                    //Bundle bundle=new Bundle();
                    //bundle.putString("username",username);
                    Intent intent=new Intent();
                    intent.setClass(SplashActivity.this,HomeActivity.class);
                    //intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SplashActivity.this,getResources().getString(R.string.toast_first_use_message), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.setClass(SplashActivity.this,Logged_in.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);

    }



}

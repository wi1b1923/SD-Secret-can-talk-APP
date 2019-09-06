package com.example.hsuanyen.my_sd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SettingActivity extends MainActivity {
    private TextView tv_account_now;
    private TextView tv_email;
    private TextView tv_language;
    private Button logout_btn;
    private Button change_btn;
    private ListView listView;
    private SimpleAdapter simpleAdapter;

    @Override
    public String getUser_n() {
        return super.getUser_n();
    }

    private static final String TAG = SettingActivity.class.getSimpleName();
    private long clickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mTextMessage.setText(getResources().getString(R.string.title_setting));
        CurrentMenuItem = 3;//目前Navigation項目位置
        BNV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        preferences=SettingActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        //Toast.makeText(SettingActivity.this,"setting",Toast.LENGTH_LONG);
        logout_btn=(Button) findViewById(R.id.logout_btn);
        change_btn=(Button)findViewById(R.id.button5);
        tv_account_now=(TextView) findViewById(R.id.tv_account_now);
        tv_email=(TextView)findViewById(R.id.textView5);
        tv_language=(TextView)findViewById(R.id.textView10);
        listView = (ListView)findViewById(R.id.list_view);
        String username = preferences.getString("username",null);
        String email = preferences.getString("email",null);
        String language = preferences.getString("language",null);
        tv_account_now.setText(getResources().getString(R.string.setting_pg_tv_account_now)+" "+username);
        tv_email.setText(email);
        int[] image = {
                R.drawable.team, R.drawable.search, R.drawable.question
        };
        String[] imgText = {
                SettingActivity.this.getResources().getString(R.string.about),  SettingActivity.this.getResources().getString(R.string.web),SettingActivity.this.getResources().getString(R.string.how_use)
        };
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("img", image[i]);
            item.put("new_text", imgText[i]);
            items.add(item);
        }
        simpleAdapter = new SimpleAdapter(this,
                items, R.layout.setting_list, new String[]{"img", "new_text"},
                new int[]{R.id.img, R.id.new_text});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(SettingActivity.this,"i="+i,Toast.LENGTH_LONG).show();
                switch (i){
                    case 0:
                        Intent intent_about = new Intent();
                        intent_about.setClass(SettingActivity.this,AboutActivity.class);
                        startActivity(intent_about);
                        finish();
                        break;
                    case 1:
                        Uri uri = Uri.parse("https://s.xstar.me/ci/");
                        Intent intent_web = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent_web);
                        break;
                    case 2:
                        Intent intent_use = new Intent();
                        intent_use.setClass(SettingActivity.this,DescriptionActivity.class);
                        startActivity(intent_use);
                        finish();
                        break;
                }
            }
        });
//        Uri uri = Uri.parse("http://www.google.com");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
        if(language!=null){
            tv_language.setText(language);
        }else{
            String system_lan=SettingActivity.this.getResources().getConfiguration().locale.getCountry();
//            Toast.makeText(SettingActivity.this,system_lan,Toast.LENGTH_LONG).show();
            if(system_lan.equals("CN")){
                tv_language.setText("简体中文");
            }else if(system_lan.equals("TW")){
                tv_language.setText("繁體中文");
            }else {
                tv_language.setText("English");
            }
        }


        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] language = {"繁體中文","简体中文","English"};

                AlertDialog.Builder dialog_list = new AlertDialog.Builder(SettingActivity.this);
                dialog_list.setTitle(getResources().getString(R.string.lan_text));
                dialog_list.setItems(language, new DialogInterface.OnClickListener(){
                    @Override

                    //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Resources res=getResources();
                        Configuration conf;
                        Intent ch_lan=new Intent();
                        String language_c="";
                        //Toast.makeText(SettingActivity.this, "你選的是" + language[which], Toast.LENGTH_SHORT).show();
                        switch (which){
                            case 0:
                                language_c="繁體中文";
                                preferences.edit().putString("language",language_c).commit();
                                //Toast.makeText(SettingActivity.this,"語言"+language,Toast.LENGTH_LONG).show();
                                conf=res.getConfiguration();
                                conf.locale= Locale.TAIWAN;
                                res.updateConfiguration(conf,res.getDisplayMetrics());

                                ch_lan.setClass(SettingActivity.this,SplashActivity.class);
                                startActivity(ch_lan);
                                finish();
                                break;
                            case 1:
                                language_c="简体中文";
                                preferences.edit().putString("language",language_c).commit();
                                //Toast.makeText(SettingActivity.this,"語言"+language,Toast.LENGTH_LONG).show();
                                conf=res.getConfiguration();
                                conf.locale= Locale.CHINA;
                                res.updateConfiguration(conf,res.getDisplayMetrics());
                                ch_lan.setClass(SettingActivity.this,SplashActivity.class);
                                startActivity(ch_lan);
                                finish();
                                break;
                            case 2:
                                language_c="English";
                                preferences.edit().putString("language",language_c).commit();
                                conf=res.getConfiguration();
                                conf.locale= Locale.US;
                                res.updateConfiguration(conf,res.getDisplayMetrics());

                                ch_lan.setClass(SettingActivity.this,SplashActivity.class);
                                startActivity(ch_lan);
                                finish();
                                break;
                        }
                    }
                });
                dialog_list.show();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this,getResources().getString(R.string.toast_logout), Toast.LENGTH_LONG).show();
                preferences.edit().clear().commit();
                //要加回到登入頁面
                Intent login_back=new Intent();
                login_back.setClass(SettingActivity.this,Logged_in.class);
                startActivity(login_back);
                finish();
            }
        });
//        try {//取得APP目前的versionName
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            tvVersion.setText( packageInfo.versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit(); return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次後退鍵退出程式", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            Log.e(TAG, "exit application");
            this.finish();
            System.exit(0);
        }
    }
}

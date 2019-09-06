package com.example.hsuanyen.my_sd;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseActivity extends MainActivity {
    private LinearLayout base_pg;
    private Button open_map;
    private String marker_name;
    private final String REQUEST_ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private ListView base_list_view;
    private SimpleAdapter base_Adater;
    private List<HashMap<String,String>> items;
    private String user_id;
    private static final String TAG = BaseActivity.class.getSimpleName();
    private long clickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mTextMessage.setText(getResources().getString(R.string.title_base));
//        Intent intent= this.getIntent();
//        Bundle bundle = intent.getExtras();
//        marker_name = bundle.getString("title");
//        double[] locations = bundle.getDoubleArray("locations");
//        double lat ,lon ;
//        lat = locations[0];
//        lon = locations[1];
//        Toast.makeText(BaseActivity.this,"地點為 "+marker_name+lat+"度"+lon+"度",Toast.LENGTH_LONG).show();

        CurrentMenuItem = 2;//目前Navigation項目位置
        BNV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        open_map=(Button) findViewById(R.id.open_map);
        open_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermission()) {
                    if (needCheckPermission()) {
                        //如果須要檢查權限，由於這個步驟要等待使用者確認，
                        //所以不能立即執行儲存的動作，
                        //必須在 onRequestPermissionsResult 回應中才執行
                        return;
                    }
                }
                goMap();

            }
        });
        /////////////////////////
        processView();
        items=new ArrayList<>();
        HashMap<String,String> record;

        preferences=BaseActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        String user_base_get=preferences.getString("user_base",null);
        if(user_base_get.equals("null")){

        }else{
            String[] user_base=preferences.getString("user_base",null).split("[&]");

            if (user_base != null && user_base.length > 0) {
                for (String value : user_base) {
                    if(value!=""){
                        record=new HashMap<>();
                        String[] s=value.split("[\\|]");
                        record.put("base_name",s[1]);
                        record.put("base_lat",s[2]);
                        record.put("base_lon",s[3]);
                        items.add(record);
                        String[]keys={"base_name","base_lat","base_lon"};
                        int[] viewIds={R.id.base_name_text,R.id.lat_text,R.id.lon_text};
                        base_Adater=new SimpleAdapter(this,items,R.layout.base_list_item,keys,viewIds);
                        base_list_view.setAdapter(base_Adater);
                    }
                }
            }
        }


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
    private void processView(){
        base_list_view=(ListView) findViewById(R.id.blist_view);
    }
    /**
     * 確認是否要請求權限(API > 23)
     * API < 23 一律不用詢問權限
     */
    private boolean needCheckPermission() {
        //MarshMallow(API-23)之後要在 Runtime 詢問權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {REQUEST_ACCESS_COARSE_LOCATION};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
            return true;
        }
        return false;
    }
    /**
     * 是否已經請求過該權限
     * API < 23 一律回傳 true
     */
    private boolean hasPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return(ActivityCompat.checkSelfPermission(this, REQUEST_ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    private void goMap(){
        Intent go_map=new Intent();
        Bundle bundle = new Bundle();
        user_id = preferences.getString("user_id",null);
        bundle.putString("user_id",user_id );
        go_map.putExtras(bundle);
        go_map.setClass(BaseActivity.this,MapsActivity.class);
        startActivity(go_map);
        finish();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200){
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(">>>", "取得授權，可以執行動作了");
                    goMap();
                }
            }
        }
    }

}

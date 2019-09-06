package com.example.hsuanyen.my_sd;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends MainActivity {

//    @Override
//    public void setUser_n(String user_n) {
//        super.setUser_n(user_n);
//    }
    private ListView secret_list_view;
    private SimpleAdapter secret_Adater;
    private List<HashMap<String,String>> items;

    private static final String TAG = HomeActivity.class.getSimpleName();
    private long clickTime = 0;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        mTextMessage.setText(getResources().getString(R.string.title_secret));

        CurrentMenuItem = 0;//目前Navigation項目位置
        BNV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態


        processView();
        items=new ArrayList<>();
        HashMap<String,String> record;

        preferences=HomeActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        String user_secret_get=preferences.getString("user_secret",null);
        if(user_secret_get.equals("null")){

        }else{
            String[] user_secret=preferences.getString("user_secret",null).split("[&]");

            if (user_secret != null && user_secret.length > 0) {

                for (String value : user_secret) {
                    if(value!=""){
                        record=new HashMap<>();

                        String[] s=value.split("[\\|]");

                        record.put("recieve_user",s[3]);
                        record.put("status",this.getResources().getString(R.string.status)+"：");
                        if (s[6].equals("0")){
                            record.put("ispublished",this.getResources().getString(R.string.ispublished_n));
                            if(s[4].equals("0")){
                                record.put("time",s[5]+this.getResources().getString(R.string.to));
                                record.put("to",this.getResources().getString(R.string.to2)+s[2]);
                            }else{
                                record.put("time",this.getResources().getString(R.string.died_to));
                                record.put("to",this.getResources().getString(R.string.to2)+s[2]);
                            }
                        }else{
                            record.put("ispublished",this.getResources().getString(R.string.ispublished_y));
                            if(s[4].equals("0")){
                                record.put("time",this.getResources().getString(R.string.already)+s[5]+this.getResources().getString(R.string.to));
                                record.put("to",this.getResources().getString(R.string.to2)+s[2]);
                            }else{
                                record.put("time",getResources().getString(R.string.already)+this.getResources().getString(R.string.died_to));
                                record.put("to",this.getResources().getString(R.string.to2)+s[2]);
                            }
                        }
                        if(s[7].equals("@")){
                            record.put("base_name",this.getResources().getString(R.string.secret_base)+"："+this.getResources().getString(R.string.no_base));
                        }else{
                            record.put("base_name",this.getResources().getString(R.string.secret_base)+"："+s[7]);
                        }
                        record.put("context",s[1]);

                        items.add(record);

                        String[]keys={"recieve_user","status","ispublished","time","context","to","base_name"};
                        final int[] viewIds={R.id.recieve_user_text,R.id.status_text,R.id.ispublished_text,R.id.time_text,R.id.context_text,R.id.to,R.id.base_name_text};
                        secret_Adater=new SimpleAdapter(this,items,R.layout.listview_item,keys,viewIds);
                        secret_list_view.setAdapter(secret_Adater);

//                        secret_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                            }
//                        });
                    }else{

                    }
                }
            }

        }

        //setViewPager();
//        Bundle bundle = this.getIntent().getExtras();
//        String username = bundle.getString("username");
//        setUser_n(username);

    }
//    public void get_my_all_secret(String[] values) {
//        String str = "";
//        if (values != null && values.length > 0) {
//            for (String value : values) {
//                value.split("[,]");
//
//            }
//        }
//    }

    private void processView(){
        secret_list_view=(ListView) findViewById(R.id.list_view);
    }
//    private void setViewPager(){
//        FragmentList_One myFragment1 = new FragmentList_One();
//        FragmentList_Two myFragment2 = new FragmentList_Two();
//        FragmentList_Three myFragment3 = new FragmentList_Three();
//        List<Fragment> fragmentList = new ArrayList<Fragment>();
//        fragmentList.add(myFragment1);
//        fragmentList.add(myFragment2);
//        fragmentList.add(myFragment3);
//        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
//        myViewPager.setAdapter(myFragmentAdapter);
//    }
}

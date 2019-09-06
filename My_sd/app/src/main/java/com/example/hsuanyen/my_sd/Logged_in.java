package com.example.hsuanyen.my_sd;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Logged_in extends AppCompatActivity {


    Button bLogin , bRegister;

    private static final String TAG = Logged_in.class.getSimpleName();
    private long clickTime = 0;
    //boolean legal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister=(Button) findViewById(R.id.bRegister);
        TextView title =(TextView)findViewById(R.id.textView);
        TextView subtitle=(TextView) findViewById(R.id.textView2);
        title.setTypeface(Typeface.createFromAsset(getAssets(),"font/CaviarDreams.ttf"));
        subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"font/CaviarDreams.ttf"));
//email格式認證要重新改
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //etUsername.addTextChangedListener(mEditTextWatcher);
                int check=3;
                String email=etEmail.getText().toString();
                if(email.length()==0){
                    check = 1;
                }else {
                    for(int i = 0 ; i < email.length();i++) {
                        //String s = word.substring(i,i+1);
                        int c = (int)email.charAt(i);
                        if(c>=65 && c<=90 || c>=97 && c<=122 || c>=48 && c<=57) {
                            //合法
                        } else {
                            check = 3;
                        }
                    }
                }
                switch (check){
                    case 1:
                        //TODO:錯誤處理
                        //這邊還要再翻譯
                        Toast.makeText(Logged_in.this,"帳戶/密碼不得為空值",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        //TODO:錯誤處理
                        Toast.makeText(Logged_in.this,"請輸入合法值(英文大小寫A~Z,數字0~9)",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        String Email = etEmail.getText().toString();
                        String Password = etPassword.getText().toString();
                        String task = "login";
                        BackgroundTask backgroundTask = new BackgroundTask(Logged_in.this);
                        etEmail.setText("");
                        etPassword.setText("");
                        backgroundTask.execute(task,Password,Email);

                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Logged_in.this, Register.class);
                Logged_in.this.startActivity(registerIntent);

            }
        });

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
//監聽文字更動用(看要不要)
//        TextWatcher mEditTextWatcher= new TextWatcher() {
//        private CharSequence temp;
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
//            temp = s;
//        }
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before,int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            //判斷是否為合法格式
//            boolean check = true;
//            for(int i = 0 ; i < temp.toString().length();i++) {
//                //String s = word.substring(i,i+1);
//                int c = (int)temp.toString().charAt(i);
//                if(c>=65 && c<=90 || c>=97 && c<=122 || c>=48 && c<=57) {
//                    //合法
//                } else {
//                    check = false;
//                }
//            }
//            if(!check) {
//                //TODO:錯誤處理
//                Toast.makeText(Logged_in.this,"請輸入合法值(英文大小寫A~Z,數字0~9)",Toast.LENGTH_LONG).show();
//                //legal=false;
//            }
//        }
//    };
}
package com.example.hsuanyen.my_sd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {
    private Button use_back_btn;
    private Button base_use;
    private Button secret_use;
    private Button dead_use;
    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;

    private static final String TAG = DescriptionActivity.class.getSimpleName();
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
        setContentView(R.layout.activity_description);
        layout01=(LinearLayout) findViewById(R.id.layout01);
        layout02=(LinearLayout)findViewById(R.id.layout02);
        layout03=(LinearLayout)findViewById(R.id.layout03);
        use_back_btn=(Button) findViewById(R.id.back_btn);
        base_use=(Button) findViewById(R.id.button3);
        secret_use=(Button) findViewById(R.id.button8);
        dead_use=(Button) findViewById(R.id.button7);
        use_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent use_back = new Intent();
                use_back.setClass(DescriptionActivity.this,SettingActivity.class);
                startActivity(use_back);
                finish();
            }
        });
        base_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout01.setVisibility(View.VISIBLE);
                layout02.setVisibility(View.GONE);
                layout03.setVisibility(View.GONE);
            }
        });
        secret_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout02.setVisibility(View.VISIBLE);
                layout01.setVisibility(View.GONE);
                layout03.setVisibility(View.GONE);

            }
        });
        dead_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout03.setVisibility(View.VISIBLE);
                layout01.setVisibility(View.GONE);
                layout02.setVisibility(View.GONE);
            }
        });
    }
}

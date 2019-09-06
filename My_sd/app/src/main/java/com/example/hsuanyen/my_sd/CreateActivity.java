package com.example.hsuanyen.my_sd;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends MainActivity {

    private EditText recipient_ed;
    private EditText email_ed;
    private  EditText mytext_ed;
    private Button Secret_btn;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox checkBox;
    private Spinner spinner;
    private TextView tv_date;
    private  TextView tv_time;
    private LinearLayout choose_time;
    private String spinner_text;
    String base_name;
    String publish_time="";
    String user_id;
    String isdie="0";
    String[] user_base;
    String[] bn;
    String user_base_get;
    String[] getbase_id;

    private static final String TAG = CreateActivity.class.getSimpleName();
    private long clickTime = 0;


    private static String TimeFix(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mTextMessage.setText(getResources().getString(R.string.title_create));
        CurrentMenuItem = 1;//目前Navigation項目位置
        BNV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        tv_date=(TextView) findViewById(R.id.tv_date);
        tv_time=(TextView) findViewById(R.id.tv_time);
        Secret_btn = (Button) findViewById(R.id.create_btn);
        recipient_ed=(EditText) findViewById(R.id.recipient_ed);
        email_ed=(EditText)findViewById(R.id.email_ed);
        mytext_ed=(EditText) findViewById(R.id.mytext_ed);
        datePicker=(DatePicker) findViewById(R.id.datePicker);
        timePicker=(TimePicker) findViewById(R.id.timePicker);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        spinner=(Spinner) findViewById(R.id.spinner);
        choose_time=(LinearLayout) findViewById(R.id.choose_time);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        preferences=CreateActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id",null);
        user_base_get=preferences.getString("user_base",null);

        if(user_base_get.equals("null")){
            bn=new String[1];
            getbase_id=new String[1];
            bn[0]="無設定";
            getbase_id[0]="無";
            ArrayAdapter<String> bnlist = new ArrayAdapter<>(CreateActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    bn);

            spinner.setAdapter(bnlist);
        }else{
            user_base=preferences.getString("user_base",null).split("[&]");
            if (user_base != null && user_base.length > 0) {
                int length,i;

                i=0;
                length=user_base.length+1;
                bn=new String[length];
                getbase_id=new String[length];
                bn[i]="無設定";
                getbase_id[i]="無";
                for (String value : user_base) {
                    if(value!=""){
                        i=i+1;
                        String[] s=value.split("[\\|]");
                        getbase_id[i]=s[0];

                        bn[i]=s[1];

                    }
                }
            }
            ArrayAdapter<String> bnlist = new ArrayAdapter<>(CreateActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    bn);

            spinner.setAdapter(bnlist);

        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()){
                    isdie="1";
                    choose_time.setVisibility(View.GONE);
                }else{
                    isdie="0";
                    choose_time.setVisibility(View.VISIBLE);
                }
            }
        });

 //       Calendar calendar = Calendar.getInstance();
        //int year = calendar.get(Calendar.YEAR);
        //int month = calendar.get(Calendar.MONTH);
        //int day = calendar.get(Calendar.DAY_OF_MONTH);
//        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
  //          @Override
   //         public void onDateChanged(DatePicker view, int year,
    //                                  int monthOfYear, int dayOfMonth) {
//
 //               if (isDateBefore(view)) {
  //                  Calendar mCalendar = Calendar.getInstance();
   //                 view.init(mCalendar.get(Calendar.YEAR),
    //                        mCalendar.get(Calendar.MONTH),
     //                       mCalendar.get(Calendar.DAY_OF_MONTH), this);
      //          }
       //     }

            //要限制使用者輸入# & |

        //    private boolean isDateBefore(DatePicker tempView) {
         //       Calendar mCalendar = Calendar.getInstance();
          //      Calendar tempCalendar = Calendar.getInstance();
           //     tempCalendar.set(tempView.getYear(), tempView.getMonth(),
            //            tempView.getDayOfMonth(), 0, 0, 0);
             //   if (tempCalendar.before(mCalendar))
              //      return true;
               // else
                //    return false;
            //}
        //});

        Secret_btn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                int hour=timePicker.getCurrentHour();
                int min =timePicker.getCurrentMinute();

                if(isdie=="1"){
                    publish_time = "";
                }else{
                    publish_time = TimeFix(year) + "-" + TimeFix(month) + "-" + TimeFix(day)+" "+TimeFix(hour)+":"+TimeFix(min);
                }
                String recieve_user=recipient_ed.getText().toString();
                String recieve_email=email_ed.getText().toString();
                String content_text=mytext_ed.getText().toString();
                if(user_base_get.equals("null")){
                    spinner_text="無";
                    base_name="無設定";

                }else{
                    base_name=spinner.getSelectedItem().toString();
                    int count=0;
                    for (String value : bn) {
                        if(value.equals(base_name)){
                            spinner_text=getbase_id[count];

                        }
                        count+=1;
                    }

                }
                if(spinner_text.startsWith("n")){
                    spinner_text=spinner_text.substring(4,spinner_text.length());
                }

                String work = "create";
                BackgroundTask2 backgroundTask2 =new BackgroundTask2(CreateActivity.this);
                backgroundTask2.execute(work,user_id,content_text,recieve_email,recieve_user,isdie,publish_time,spinner_text,base_name);

            }
        });
    }
}

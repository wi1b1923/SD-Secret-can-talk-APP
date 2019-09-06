package com.example.hsuanyen.my_sd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 喵喵團隊
 */
public class BackgroundTask2 extends AsyncTask<String,Void,String>  {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    String[] secret=new String[9];
    String[] base=new String[4];
    String sec_base_id;
    String myData;

    BackgroundTask2(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("work","0");
        editor.commit();
        String urlSecret = "https://c.sdsecret.tk/login_and_register/secret_insert.php";
        String urlBase="https://c.sdsecret.tk/login_and_register/base_insert.php";
        String work = params[0];


        if(work.equals("create")){
            String sec_userid = params[1];
            String sec_content = params[2];
            String sec_recieve_email = params[3];
            String sec_recieve_user = params[4];
            String sec_isdie = params[5];
            String sec_publish_time = params[6];

            sec_base_id = params[7];


            secret[0]="";
            secret[1]=sec_content;
            secret[2]=sec_recieve_email;
            secret[3]=sec_recieve_user;
            secret[4]=sec_isdie;
            secret[5]=sec_publish_time;
            secret[6]="0";
            if(params[8].equals("無設定")){
                secret[7]="@";
            }else{
                secret[7]=params[8];
            }
            //要記得改 等base spinner做好後 這裡船base名字


            try {
                URL url = new URL(urlSecret);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                if(params[7].equals("無")){
                    myData = URLEncoder.encode("sec_userid","UTF-8")+"="+URLEncoder.encode(sec_userid,"UTF-8")+"&"
                            +URLEncoder.encode("sec_content","UTF-8")+"="+URLEncoder.encode(sec_content,"UTF-8")+"&"
                            +URLEncoder.encode("sec_recieve_email","UTF-8")+"="+URLEncoder.encode(sec_recieve_email,"UTF-8")+"&"
                            +URLEncoder.encode("sec_recieve_user","UTF-8")+"="+URLEncoder.encode(sec_recieve_user,"UTF-8")+"&"
                            +URLEncoder.encode("sec_isdie","UTF-8")+"="+URLEncoder.encode(sec_isdie,"UTF-8")+"&"
                            +URLEncoder.encode("sec_publish_time","UTF-8")+"="+URLEncoder.encode(sec_publish_time,"UTF-8");

                }else{
                    myData = URLEncoder.encode("sec_userid","UTF-8")+"="+URLEncoder.encode(sec_userid,"UTF-8")+"&"
                            +URLEncoder.encode("sec_content","UTF-8")+"="+URLEncoder.encode(sec_content,"UTF-8")+"&"
                            +URLEncoder.encode("sec_base_id","UTF-8")+"="+URLEncoder.encode(sec_base_id,"UTF-8")+"&"
                            +URLEncoder.encode("sec_recieve_email","UTF-8")+"="+URLEncoder.encode(sec_recieve_email,"UTF-8")+"&"
                            +URLEncoder.encode("sec_recieve_user","UTF-8")+"="+URLEncoder.encode(sec_recieve_user,"UTF-8")+"&"
                            +URLEncoder.encode("sec_isdie","UTF-8")+"="+URLEncoder.encode(sec_isdie,"UTF-8")+"&"
                            +URLEncoder.encode("sec_publish_time","UTF-8")+"="+URLEncoder.encode(sec_publish_time,"UTF-8");
                }

                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(dataResponse);

                editor.putString("work","create");
                editor.commit();
               return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(work.equals("base")){
            String base_userid = params[1];
            String base_name = params[2];
            String base_lat = params[3];
            String base_lon = params[4];
            base[0]="";
            base[1]=base_name;
            base[2]=base_lat;
            base[3]=base_lon;

            try {
                URL url = new URL(urlBase);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("base_user_id","UTF-8")+"="+URLEncoder.encode(base_userid,"UTF-8")+"&"
                        +URLEncoder.encode("base_name","UTF-8")+"="+URLEncoder.encode(base_name,"UTF-8")+"&"
                        +URLEncoder.encode("base_lat","UTF-8")+"="+URLEncoder.encode(base_lat,"UTF-8")+"&"
                        +URLEncoder.encode("base_lon","UTF-8")+"="+URLEncoder.encode(base_lon,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();

                editor.putString("work","base");
                editor.commit();

                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //This method willbe called when doInBackground completes... and it will return the completion string which
    //will display this toast.

    @Override
    protected void onPostExecute(String s) {
        String work = preferences.getString("work","0");

        //////等待改

        if(work.equals("create")) {
            String user_secret=preferences.getString("user_secret",null);
            secret[0]=s;
            user_secret+=set_str(secret);
            editor.putString("user_secret", user_secret);
            editor.commit();
            Intent Home=new Intent();
            Home.setClass(context,HomeActivity.class);
            context.startActivity(Home);

        }
        if(work.equals("base")) {
            String user_base = preferences.getString("user_base", null);
            base[0] = s;
            user_base += set_str(base);
            editor.putString("user_base", user_base);
            editor.commit();
            Intent base_h = new Intent();
            base_h.setClass(context, BaseActivity.class);
            context.startActivity(base_h);
            //補finish
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String set_str(String[] values) {
        String regularEx = "|";
        String str = "";
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            str+="&";
        }
        return str;
    }


//提示文字記得要改username改email
    public void display(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        switch (i) {
            case 1:
                builder.setTitle(context.getResources().getString(R.string.toast_register_fail1));
                builder.setMessage(context.getResources().getString(R.string.toast_register_fail2));
                break;
            case 2:
                builder.setTitle(R.string.toast_login_fail1);
                builder.setMessage(R.string.toast_login_fail2);
                break;
        }
        builder.show();
    }
}


package com.example.hsuanyen.my_sd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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
public class BackgroundTask extends AsyncTask<String,Void,String>  {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    BackgroundTask(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.commit();

        String urlRegistration = "https://c.sdsecret.tk/login_and_register/register.php";
        String urlLogin  = "https://c.sdsecret.tk/login_and_register/login.php";
        String task = params[0];

        if(task.equals("register")){
            String regPassword = params[1];
            String regUsername = params[2];
            String regEmail = params[3];

            try {
                URL url = new URL(urlRegistration);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_name","UTF-8")+"="+URLEncoder.encode(regUsername,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_email","UTF-8")+"="+URLEncoder.encode(regEmail,"UTF-8");
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

                editor.putString("flag","register");
                editor.commit();
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(task.equals("login")){
            String loginPassword = params[1];
            String loginEmail = params[2];
////////////////////////////////////
            try {
                URL url = new URL(urlLogin);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");                 //設置post請求
                httpURLConnection.setDoOutput(true);                          //設置是否向httpurlconnection輸出
                httpURLConnection.setDoInput(true);                           //同上                                        輸入

                //send the username and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_loginPassword","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_loginEmail","UTF-8")+"="+URLEncoder.encode(loginEmail,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();                                   //把緩衝數據刷到目的地
                bufferedWriter.close();
                outputStream.close();

                //get response from the database
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
                httpURLConnection.disconnect();

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(dataResponse);

                editor.putString("flag","login");
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
        String flag = preferences.getString("flag","0");

        //////等待改

        if(flag.equals("register")) {
            String test1 = "false";
            String username ="";
            String email="";
            String[] serverResponse =s.split("[,]");
            test1 = serverResponse[0];
            username =serverResponse[1];
            email=serverResponse[2];
            if (test1.equals("true")) {
                //看要不要加上跳轉登入時自動上email
                Toast.makeText(context,context.getResources().getString(R.string.toast_register_success1)+ username +context.getResources().getString(R.string.toast_register_success2),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Logged_in.class);
                context.startActivity(intent);
            } else {

                //Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                display(1);
            }

        }else if (flag.equals("login")) {
                String test = "false";
                String email = "";
                String password = "";
                String username ="";
                String user_id="";
                String Response_secret="";
                String Response_base="";
                String[] serverResponse = s.split("[#]");
                String[] serverResponse1 = serverResponse[0].split("[,]");
                Response_secret = serverResponse[1];
                Response_base = serverResponse[2];
                test = serverResponse1[0];
                password = serverResponse1[1];
                email = serverResponse1[2];
                username=serverResponse1[3];
                user_id=serverResponse1[4];

                if (test.equals("true")) {
                    editor.putString("username", username);
                    editor.commit();
                    editor.putString("password", password);
                    editor.commit();
                    editor.putString("email",email);
                    editor.commit();
                    editor.putString("user_id",user_id);
                    editor.commit();
                    if(Response_secret=="null"){
                        editor.putString("user_secret",Response_secret);
                        editor.commit();
                    }else{
                        editor.putString("user_secret",Response_secret);
                        editor.commit();
                    }
                    if(Response_base=="null"){
                        editor.putString("user_base",Response_base);
                        editor.commit();
                    }else{
                        editor.putString("user_base",Response_base);
                        editor.commit();
                    }
//                    Bundle bundle=new Bundle();
//                    bundle.putString("username",username);
                    Intent intent = new Intent(context, HomeActivity.class);
//                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    display(2);
                    // /display("登入失敗...", "帳號或密碼輸入錯誤!!!");
                }

        } else {
            display(3);
            // /display("登入失敗...","未知錯誤發生");
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    public String set_str(String[] values) {
        String str = "";
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
            }

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
            case 3:
                builder.setTitle(R.string.toast_login_fail1);
                builder.setMessage(R.string.toast_login_fail3);
                break;
        }
        builder.show();
    }
}


package com.vscholars.stack2code.vscholars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 16/3/17.String check;
 */

public class LoginPage extends AppCompatActivity{
    SQLiteDatabase sqLiteDatabase;
    String name = "cache.db";
    String str="error";
    String check,email,password,enc_pass,enc_email;
    Button loginButton;
    RadioButton userRB1,verifierRB2;
    TextView textReg;
    EditText emailInput,passwordInput;
    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //actionbar hide

        sqLiteDatabase = openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists logged_in_user(email varchar primary key,password varchar,user_type varchar);");
        check=checkIfLoggedIn();



        loginButton=(Button)findViewById(R.id.login_button);
        textReg=(TextView) findViewById(R.id.text_reg);
        userRB1=(RadioButton)findViewById(R.id.user_rb1);
        verifierRB2=(RadioButton)findViewById(R.id.verifier_rb2);
        emailInput=(EditText)findViewById(R.id.email_input);
        passwordInput=(EditText)findViewById(R.id.password_input);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=checkIfLoggedIn();
                SharedPreferences sharedpreferences = getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("s_email",emailInput.getText().toString().toLowerCase());
                editor.commit();
                String email=emailInput.getText().toString();
                String password=passwordInput.getText().toString();
                try {
                    String key = "stack2code@54321"; // 128 bit key
                    // Create key and cipher
                    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    // encrypt the text
                    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                    byte[] encrypted_password = cipher.doFinal(password.getBytes());
                    byte[] encrypted_user_email = cipher.doFinal(email.getBytes());

                    StringBuilder sb_pass = new StringBuilder();
                    StringBuilder sb_email = new StringBuilder();
                    for (byte b : encrypted_password) {
                        sb_pass.append((char) b);
                    }
                    for (byte b : encrypted_user_email) {
                        sb_email.append((char) b);
                    }

                    // the encrypted String
                    enc_pass = sb_pass.toString();
                    enc_email = sb_email.toString();
                }catch (Exception e){

                }

                if (check.equals("no")) {
                    Intent intent=new Intent(LoginPage.this,ProgressActivity.class);
                    startActivity(intent);

                    if(userRB1.isChecked())
                        new j_stud().execute(email.toLowerCase(),password);
                     if(verifierRB2.isChecked())
                         new j_verifier().execute(email.toLowerCase(),password);


                }
                else if (check.equals("yes")){
                    SharedPreferences sf = getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedpreferences.edit();
                    editor.putString("s_email",emailInput.getText().toString().toLowerCase());
                    editor.commit();
                   // Toast.makeText(LoginPage.this,"One user is already logged in",Toast.LENGTH_LONG).show();
                }
            }
        });



        textReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new activity needs to be created for registering purpose it is meant for testing
                Intent i=new Intent(LoginPage.this,RegisterPage.class);
                startActivity(i);
               // overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private String checkIfLoggedIn() {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from logged_in_user", null);
            if (cursor != null) {
                cursor.moveToFirst();
                String str=cursor.getString(0)+","+cursor.getString(1);
                //Toast.makeText(LoginPage.this,str,Toast.LENGTH_LONG).show();
                return "yes";
            }
        } catch (Exception e) {
            if (e.toString().equals("android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0")) {
                //Toast.makeText(LoginPage.this, "No logged in user", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        return "ambiguity";
    }
    public class j_stud extends AsyncTask<String, Void, Void>  {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", s[0]));
                params.add(new BasicNameValuePair("password", s[1]));


//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/login_script.php", "POST", params);

                int i=jsono.getInt("success");

                if (i == 1) {

                    str= "success";

                } else {
                    str= jsono.getString("message");
                }


            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);


            if(str.equals("success")) {
               // Toast.makeText(LoginPage.this,str,Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginPage.this, MainActivity.class);

                if (userRB1.isChecked()) {
                    String userType = "student";
                    sqLiteDatabase.execSQL("insert into logged_in_user(email,password,user_type)values('" + enc_email + "','" + enc_pass + "','" + userType + "');");
                    i.putExtra("userType", 1);
                    //Toast.makeText(LoginPage.this, "Student", Toast.LENGTH_LONG).show();
                    startActivity(i);
                  //  overridePendingTransition(R.anim.slidein,R.anim.slideout);
                } else {
                   // Toast.makeText(LoginPage.this, "Please Choose if you are student or verifier", Toast.LENGTH_LONG).show();
                }


            }else{

                Toast.makeText(LoginPage.this," Wrong Uname or Password",Toast.LENGTH_LONG).show();
            }

        }
    }
    public class j_verifier extends AsyncTask<String, Void, Void>  {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", s[0]));
                params.add(new BasicNameValuePair("password", s[1]));


//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/loginverifier.php", "POST", params);

                int i=jsono.getInt("success");

                if (i == 1) {

                    str= "success";

                } else {
                    str= jsono.getString("message");
                }


            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);


            if(str.equals("success")) {


                //Toast.makeText(LoginPage.this,str,Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginPage.this, MainActivity.class);


                 if (verifierRB2.isChecked()) {
                    String userType = "verifier";
                    sqLiteDatabase.execSQL("insert into logged_in_user(email,password,user_type)values('" + enc_email + "','" + enc_pass + "','" + userType + "');");
                    i.putExtra("userType", 2);
                    //Toast.makeText(LoginPage.this, "Verifier", Toast.LENGTH_LONG).show();
                    startActivity(i);
                     overridePendingTransition(R.anim.slidein,R.anim.slideout);
                } else {
                   // Toast.makeText(LoginPage.this, "Please Choose if you are student or verifier", Toast.LENGTH_LONG).show();
                }


            }else{

               // Toast.makeText(LoginPage.this,str,Toast.LENGTH_LONG).show();
            }

        }
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    public void onBackPressed(){
        Intent i=new Intent(LoginPage.this,LoginHome.class);
        startActivity(i);
        overridePendingTransition(R.anim.slidein,R.anim.slideout);
    }
}
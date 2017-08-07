package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class DigiLockerSignInActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    String name = "cache.db";
    String check,enc_email,enc_pass;
    EditText uname,passwordInput;
    Button signIn;
    String password_string,email_string;
    int count =0;
    String j_str;
    String str;
    JSONObject jsono;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digilocker_signin_signup);
        uname=(EditText)findViewById(R.id.input_email_digi);
        passwordInput=(EditText)findViewById(R.id.input_password_digi);

        signIn=(Button)findViewById(R.id.sign_in_digi);
        sqLiteDatabase = openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists logged_in_user_digi(email varchar primary key,password varchar);");
        sqLiteDatabase.execSQL("delete from logged_in_user_digi");
        check=checkIfLoggedIn();
        int userType=getIntent().getIntExtra("userType",0);

        if (check.equals("yes")) {
            Intent i = new Intent(DigiLockerSignInActivity.this, ItemSelectorDigilockerActivity.class);
            Toast.makeText(DigiLockerSignInActivity.this, "One user is already logged into Digilocker Account", Toast.LENGTH_LONG).show();
            startActivity(i);
        }else{
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        password_string=passwordInput.getText().toString();
                        email_string=uname.getText().toString();
                        Toast.makeText(DigiLockerSignInActivity.this, "t1"+" "+password_string+" "+email_string, Toast.LENGTH_LONG).show();
                        String key = "stack2code@54321"; // 128 bit key
                        // Create key and cipher
                        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                        Cipher cipher = Cipher.getInstance("AES");
                        // encrypt the text
                        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                        byte[] encrypted_password = cipher.doFinal(password_string.getBytes());
                        byte[] encrypted_user_email=cipher.doFinal(email_string.getBytes());

                        StringBuilder sb_pass = new StringBuilder();
                        StringBuilder sb_email=new StringBuilder();
                        for (byte b : encrypted_password) {
                            sb_pass.append((char) b);
                        }
                        for (byte b: encrypted_user_email){
                            sb_email.append((char)b);
                        }

                        // the encrypted String

                        enc_pass = sb_pass.toString();
                        enc_email=sb_email.toString();


                         new  j_digi().execute(email_string,password_string);




                    }catch (Exception e){

                    }

                }
            });
        }

    }
    private String checkIfLoggedIn() {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from logged_in_user_digi", null);
            if (cursor != null) {
                cursor.moveToFirst();
                String str=cursor.getString(0)+","+cursor.getString(1);
                Toast.makeText(DigiLockerSignInActivity.this,str,Toast.LENGTH_LONG).show();
                return "yes";
            }
        } catch (Exception e) {
            if (e.toString().equals("android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0")) {
                Toast.makeText(DigiLockerSignInActivity.this, "No logged in user", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        return "ambiguity";
    }
    public class j_digi extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", s[0]));
                params.add(new BasicNameValuePair("password", s[1]));


//                 getting JSON Object
//                 Note that create product url accepts POST method
                jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/digi_login.php", "POST", params);
               count =jsono.getInt("count");


                int i=jsono.getInt("success");

                if (i == 1) {

                    j_str= "success";

                } else {
                    j_str= jsono.getString("message");
                }


            } catch (Exception e) {
                j_str=e.toString();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Toast.makeText(DigiLockerSignInActivity.this,j_str,Toast.LENGTH_SHORT).show();

            if(j_str.equals("success")) {
                Toast.makeText(DigiLockerSignInActivity.this,"sucess",Toast.LENGTH_LONG).show();
                check=checkIfLoggedIn();
                    if (check.equals("no")) {
//                        Intent i=new Intent(DigiLockerSignInActivity.this,ItemSelectorDigilockerActivity.class);
                         sqLiteDatabase.execSQL("insert into logged_in_user_digi(email,password)values('" + enc_email + "','" + enc_pass + "');");
//                        i.putExtra("userType",getIntent().getIntExtra("userType",0));
//                        startActivity(i);

                        Digi_certi_name[] dcn=new Digi_certi_name[count];
                        for(int x=0;x<count;x++){
                            dcn[x]=new Digi_certi_name();
                        }
                        try {
                            JSONArray ja=jsono.getJSONArray("certi");

                            for(int x=0;x<count;x++){
                                JSONObject jo=ja.getJSONObject(x);
                                dcn[x].certi_name=jo.getString("certi_name");
                                dcn[x].uri=jo.getString("uri");
                            }

                            Intent intent=new Intent(DigiLockerSignInActivity.this,ItemSelectorDigilockerActivity.class);
                            String certificatesNames[]=new String[count];
                            String certificatesURI[]=new String[count];
                            for (int j=0;j<count;++j){
                                certificatesNames[j]=new String();
                                certificatesURI[j]=new String();
                                certificatesNames[j]=dcn[j].certi_name;
                                certificatesURI[j]=dcn[j].uri;
                            }
                            intent.putExtra("certificatesName",certificatesNames);
                            intent.putExtra("certificatesURI",certificatesURI);
                            intent.putExtra("count",count);

                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(DigiLockerSignInActivity.this,e.toString(),Toast.LENGTH_LONG).show();

                    }


                }
            }else{
                Toast.makeText(DigiLockerSignInActivity.this,j_str,Toast.LENGTH_LONG).show();
            }

        }
    }

    public void onBackPressed(){
        Intent intent=new Intent(DigiLockerSignInActivity.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);

    }
}
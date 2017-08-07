package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vineet_jain on 21/3/17.
 */

public class LoginHome extends AppCompatActivity{
    SQLiteDatabase sqLiteDatabase;
    String name = "cache.db";
    String check;
    Button buttonSignUp, buttonSignIn;
    Button skip;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);

        sqLiteDatabase = openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists logged_in_user(email varchar primary key,password varchar,user_type varchar);");
        check=checkIfLoggedIn();

        if(check.equals("yes")){
            Intent i=new Intent(LoginHome.this,MainActivity.class);
            Cursor cursor=sqLiteDatabase.rawQuery("select * from logged_in_user",null);
            cursor.moveToFirst();
            if (cursor.getString(2).equals("student")){
                i.putExtra("userType",1);
                startActivity(i);
                
            }else if (cursor.getString(2).equals("verifier")){
                i.putExtra("userType",2);
                startActivity(i);
                overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        }else if (check.equals("no")){

        }

        buttonSignIn=(Button)findViewById(R.id.btnSingIn);
        buttonSignUp=(Button)findViewById(R.id.btnSignUp);
        skip=(Button)findViewById(R.id.skip);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginHome.this,LoginPage.class);
                startActivity(i);
                overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginHome.this,RegisterPage.class);
                startActivity(i);
                overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginHome.this,MainActivity.class);
                i.putExtra("userType",3);
                startActivity(i);
                overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        });
    }

    private String checkIfLoggedIn() {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from logged_in_user", null);
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst();
                String str=cursor.getString(0)+","+cursor.getString(1);
                return "yes";
            }
        } catch (Exception e) {
            if (e.toString().equals("android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0")) {
                return "no";
            }
        }
        return "ambiguity";
    }

    protected void onPause() {
        super.onPause();
        finish();
    }

    public void onBackPressed(){
        onPause();

    }
}

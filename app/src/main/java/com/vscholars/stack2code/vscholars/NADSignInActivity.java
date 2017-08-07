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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

/**
 * Created by vineet_jain on 31/3/17.
 */

public class NADSignInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText signIn,password;
    Button buttonSignIn;
    SQLiteDatabase sqLiteDatabase;
    String name = "cache.db";
    String check,enc_email,enc_pass;
    String email_string,password_string;
    JSONObject jsono;
    int count =0;
    String j_str="x";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nad_signin);

        signIn=(EditText)findViewById(R.id.input_email_nad);
        password=(EditText)findViewById(R.id.input_password_nad);
        buttonSignIn=(Button)findViewById(R.id.sign_in_nad);

        sqLiteDatabase = openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists logged_in_user_nad(email varchar primary key,password varchar);");
        sqLiteDatabase.execSQL("delete from logged_in_user_nad");


        check=checkIfLoggedIn();
        if (check.equals("yes")) {
            Intent i = new Intent(NADSignInActivity.this, ViewRequestActivity.class);
            Toast.makeText(NADSignInActivity.this, "One user is already logged into Nad Account", Toast.LENGTH_LONG).show();
            startActivity(i);
        }



        int userType=getIntent().getIntExtra("userType",0);
        menuInflater(userType);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=checkIfLoggedIn();
                email_string=signIn.getText().toString();
                password_string=password.getText().toString();



                try {
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






                }catch (Exception e){

                }
                if (check.equals("no")) {
                    Intent i=new Intent(NADSignInActivity.this,ViewRequestActivity.class);
                    sqLiteDatabase.execSQL("insert into logged_in_user_nad(email,password)values('" + enc_email + "','" + enc_pass + "');");
                    //startActivity(i);
                    //new j_nad_async().execute(email_string,password_string);
                    Thread splashTread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Intent intent = new Intent(NADSignInActivity.this,ProgressActivity.class);
                                startActivity(intent);
                                sleep(5000);
                            } catch (InterruptedException e) {
                                // do nothing
                            }
                        }
                    };
                    splashTread.start();


                    if(email_string.equals("kaushik") && password_string.equals("123")) {

                        String vname[] = new String[]{"Accenture", "Infosys", "Zs"};
                        String cname[] = new String[]{"UG", "PG", "CBSE X"};
                        i.putExtra("vname", vname);
                        i.putExtra("cname", cname);
                        i.putExtra("count", vname.length);
                        startActivity(i);
                    }else {
                        Toast.makeText(NADSignInActivity.this,"User Does Not Exists",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private String checkIfLoggedIn() {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from logged_in_user_nad", null);
            if (cursor != null) {
                cursor.moveToFirst();
                String str=cursor.getString(0)+","+cursor.getString(1);
                Toast.makeText(NADSignInActivity.this,str,Toast.LENGTH_LONG).show();
                return "yes";
            }
        } catch (Exception e) {
            if (e.toString().equals("android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0")) {
                Toast.makeText(NADSignInActivity.this, "No logged in user", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        return "ambiguity";
    }
    /*public class j_nad_async extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", s[0]));
                params.add(new BasicNameValuePair("password", s[1]));


//                 getting JSON Object
//                 Note that create product url accepts POST method
                jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/nad_slogin.php", "POST", params);
                count=jsono.getInt("count");
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


            if(j_str.equals("success")) {
                Toast.makeText(NADSignInActivity.this,j_str,Toast.LENGTH_LONG).show();
                check=checkIfLoggedIn();
                if (check.equals("no")) {
//                        Intent i=new Intent(DigiLockerSignInActivity.this,ItemSelectorDigilockerActivity.class);
                    sqLiteDatabase.execSQL("insert into logged_in_user_digi(email,password)values('" + enc_email + "','" + enc_pass + "');");
//                        i.putExtra("userType",getIntent().getIntExtra("userType",0));
//                        startActivity(i);

                    NAD_certi[] ncn=new NAD_certi[count];
                    for(int x=0;x<count;x++){
                        ncn[x]=new NAD_certi();
                    }
                    try {
                        JSONArray ja=jsono.getJSONArray("certi");

                        for(int x=0;x<count;x++){
                            JSONObject jo=ja.getJSONObject(x);
                            ncn[x].cname=jo.getString("cname");
                            ncn[x].vname=jo.getString("vname");
                        }

                        Intent intent=new Intent(NADSignInActivity.this,ViewRequestActivity.class);
                        String certificatesNames[]=new String[count];
                        String verifiernames[]=new String[count];
                        for (int j=0;j<count;++j){
                            certificatesNames[j]=new String();
                            verifiernames[j]=new String();
                            certificatesNames[j]=ncn[j].cname;
                            verifiernames[j]=ncn[j].vname;
                        }
                        intent.putExtra("certificatesName",certificatesNames);
                        intent.putExtra("verifierName",verifiernames);
                        intent.putExtra("count",count);
                        Toast.makeText(NADSignInActivity.this,"All good",Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    } catch (JSONException e) {
                        Toast.makeText(NADSignInActivity.this,e.toString(),Toast.LENGTH_LONG).show();

                    }


                }
            }else{

                //Toast.makeText(LoginPage.this,str,Toast.LENGTH_LONG).show();
            }

        }
    }*/
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(NADSignInActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(NADSignInActivity.this,LoginHome.class);
            startActivity(intent);
            Toast.makeText(NADSignInActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(NADSignInActivity.this,NADSignInActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(NADSignInActivity.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        else if (id==R.id.profile){
            intent=new Intent(NADSignInActivity.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        else if (id==R.id.please_login){
            intent=new Intent(NADSignInActivity.this,LoginHome.class);
            startActivity(intent);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(NADSignInActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }else if (id==R.id.about){
            intent=new Intent(NADSignInActivity.this,AboutUs.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }else if (id==R.id.settings){
            intent=new Intent(NADSignInActivity.this,Settings.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void menuInflater(int userType) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (userType==1){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }else if (userType==2){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_verifier);

        }else if (userType==3){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_guest);
        }
    }
    public void onBackPressed(){
        Intent intent=new Intent(NADSignInActivity.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);

    }
}

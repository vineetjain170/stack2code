package com.vscholars.stack2code.vscholars;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 21/3/17.
 */
public class RegisterPage extends AppCompatActivity {
    Button registerButton;
    TextView textLogin, dob;
    EditText name, email, contact, password, roll, aadhaar, address, institute;
    RadioButton male, female, other;
    String gender = "";
    String str = "error";
    String a_ver = "aadhaar not verified";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register_page);
        ActionBar ac = getSupportActionBar();
        ac.hide();

        male=(RadioButton)findViewById(R.id.register_page_rbm);
        female=(RadioButton)findViewById(R.id.register_page_rbf);
        other=(RadioButton)findViewById(R.id.register_page_rbO);

        registerButton=(Button)findViewById(R.id.register_button);
        textLogin=(TextView)findViewById(R.id.text_login);

        name=(EditText)findViewById(R.id.name_input) ;
        email=(EditText)findViewById(R.id.email_input) ;
        contact=(EditText)findViewById(R.id.contact_input) ;
        password=(EditText)findViewById(R.id.password_input) ;
        roll=(EditText)findViewById(R.id.rollno_input) ;
        aadhaar=(EditText)findViewById(R.id.aadhar_input);
        address=(EditText)findViewById(R.id.address_input);
        dob=(TextView)findViewById(R.id.dob_register);
        institute=(EditText)findViewById(R.id.college_input);

        if(male.isChecked()) gender="M";
         else if(female.isChecked()) gender="F";
          else if(other.isChecked()) gender="O";
          else{}


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,DatePickerActivity.class);
                startActivity(intent);
            }
        });
        if(getIntent().getStringExtra("date")!=null) {
            dob.setText(getIntent().getStringExtra("date"));
        }





        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(RegisterPage.this,name.getText().toString()+","+aadhaar.getText().toString(),Toast.LENGTH_LONG).show();
                new A_verify().execute(name.getText().toString(),aadhaar.getText().toString());



            }



        });


        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterPage.this,LoginPage.class);
                startActivity(i);
                overridePendingTransition(R.anim.slidein,R.anim.slideout);
            }
        });




    }
    public void onBackPressed(){
        Intent i=new Intent(RegisterPage.this,LoginHome.class);
        startActivity(i);
        overridePendingTransition(R.anim.slidein,R.anim.slideout);
    }
     public class reg extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("name", s[0]));
                params.add(new BasicNameValuePair("email", s[1]));
                params.add(new BasicNameValuePair("contact", s[2]));
                params.add(new BasicNameValuePair("password", s[3]));
                params.add(new BasicNameValuePair("roll", s[4]));
                params.add(new BasicNameValuePair("aadhaar", s[5]));
                params.add(new BasicNameValuePair("address", s[6]));
                params.add(new BasicNameValuePair("dob", s[7]));
                params.add(new BasicNameValuePair("institute",s[8]));
                params.add(new BasicNameValuePair("gender",s[9]));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/signup_script.php", "POST", params);

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
                Toast.makeText(RegisterPage.this,str,Toast.LENGTH_LONG).show();
               // Intent i=new Intent(RegisterPage.this,MainActivity.class);
               // startActivity(i);
            }else{

                Toast.makeText(RegisterPage.this,str,Toast.LENGTH_LONG).show();
            }

        }
    }
    public class A_verify extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("name", s[0]));
                params.add(new BasicNameValuePair("aadhaar", s[1]));
                Log.d("credentials",s[0]+s[1]);

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/aadhaar.php", "POST", params);

                a_ver=jsono.getString("message");



            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

//            Toast.makeText(RegisterPage.this,a_ver,Toast.LENGTH_LONG).show();

            if(a_ver.equals("y")) {
                Toast.makeText(RegisterPage.this,a_ver,Toast.LENGTH_LONG).show();
                new reg().execute(
                        name.getText().toString(),
                        email.getText().toString(),
                        contact.getText().toString(),
                        password.getText().toString(),
                        roll.getText().toString(),
                        aadhaar.getText().toString(),
                        address.getText().toString(),
                        dob.getText().toString(),
                        institute.getText().toString(),
                        gender
                );


            }else{
                //ScrollView parent=(ScrollView)findViewById(R.id.activity_main);
                //Toast.makeText(RegisterPage.this,a_ver,Toast.LENGTH_LONG).show();
                //Snackbar.make(parent,a_ver,Snackbar.LENGTH_LONG).show();
            }

        }
    }          // debug this
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = RegisterPage.this.getSharedPreferences("selected_date", Context.MODE_PRIVATE);
        String date=sharedPref.getString("date", null);
        if(date!=null) {
            dob.setText(sharedPref.getString("date", null));
            Toast.makeText(this,date,Toast.LENGTH_LONG).show();
        }
    }
    public void onPause(){
        super.onPause();
    }






}
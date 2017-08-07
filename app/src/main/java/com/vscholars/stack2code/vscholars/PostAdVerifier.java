package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 27/3/17.
 */
public class PostAdVerifier extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText job,location,startDate,duration,stipend,apply;
    Button post;
    RadioButton fullTime,partTime;
    String s_verifier_name=" dummy";
    String str="error";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_ad);
        job=(EditText)findViewById(R.id.input_job_type);
        post=(Button)findViewById(R.id.action_submit);
        location=(EditText)findViewById(R.id.input_location);
        startDate=(EditText)findViewById(R.id.input_start_dtae);
        duration=(EditText)findViewById(R.id.input_duration);
        stipend=(EditText)findViewById(R.id.input_stipend);
        apply=(EditText)findViewById(R.id.input_app_date);
        fullTime=(RadioButton)findViewById(R.id.r_full_time);
        partTime=(RadioButton)findViewById(R.id.r_part_time);
        int userType=getIntent().getIntExtra("userType",0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Ad");
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setShowHideAnimationEnabled(true);
                getSupportActionBar().hide();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setShowHideAnimationEnabled(true);
                getSupportActionBar().show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menuInflater(userType);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_jobType=job.getText().toString();
                String s_location=location.getText().toString();
                String s_start=startDate.getText().toString();
                String s_duration=duration.getText().toString();
                String s_stipend=stipend.getText().toString();
                String s_apply=apply.getText().toString();
                String s_parttime="null";
                if(partTime.isChecked()){
                    s_parttime= "Part Time";
                }
                if(fullTime.isChecked()){
                    s_parttime= "Full Time";
                }



                new j_postad().execute(s_jobType,s_location,s_start,s_duration,s_stipend,s_apply,s_parttime);



            }
        });

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
    public class j_postad extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("job_type", s[0]));
                params.add(new BasicNameValuePair("company_name", s_verifier_name));
                params.add(new BasicNameValuePair("location", s[1]));
                params.add(new BasicNameValuePair("start_date", s[2]));
                params.add(new BasicNameValuePair("duration", s[3]));
                params.add(new BasicNameValuePair("stipend", s[4]));
                params.add(new BasicNameValuePair("apply_by", s[5]));
                params.add(new BasicNameValuePair("full_timeORpart_time", s[6]));





//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/addjob.php", "POST", params);


                str= jsono.getString("message");




            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            Toast.makeText(PostAdVerifier.this,str,Toast.LENGTH_LONG).show();




        }
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(PostAdVerifier.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(PostAdVerifier.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(PostAdVerifier.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(PostAdVerifier.this,ViewRequestActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(PostAdVerifier.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.profile){
            intent=new Intent(PostAdVerifier.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.please_login){
            intent=new Intent(PostAdVerifier.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(PostAdVerifier.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.log_out_verifier){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(PostAdVerifier.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(PostAdVerifier.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }else if (id==R.id.profile_verifier){
            intent=new Intent(PostAdVerifier.this,ProfileViewVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.view_application_request){
            intent=new Intent(PostAdVerifier.this,ViewJobApplicationVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.about){
            intent=new Intent(PostAdVerifier.this,AboutUs.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onBackPressed(){
        Intent intent=new Intent(PostAdVerifier.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
        overridePendingTransition(R.anim.slidein,R.anim.slideout);
    }
}

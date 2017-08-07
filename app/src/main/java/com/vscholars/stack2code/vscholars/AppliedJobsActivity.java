package com.vscholars.stack2code.vscholars;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 24/3/17.
 */

public class AppliedJobsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String str=" ";
    int count=0;
    JSONObject jsono;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applied_jobs);
        int userType=getIntent().getIntExtra("userType",0);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applied Jobs");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences sharedPref = AppliedJobsActivity.this.getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
        String email=sharedPref.getString("s_email", null);
        Toast.makeText(AppliedJobsActivity.this,email,Toast.LENGTH_LONG).show();
        // new  j_appliedj().execute(email);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menuInflater(userType);
    }
    public class j_appliedj extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", s[0]));



//                 getting JSON Object
//                 Note that create product url accepts POST method
                jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/login_script.php", "POST", params);

                int i=jsono.getInt("success");
                count=jsono.getInt("count");
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

                applied_job_list_object[] ajlo= new applied_job_list_object[count];
                for(int x=0;x<count;x++){
                    ajlo[x]= new applied_job_list_object();
                }
                try {
                    JSONArray ja=jsono.getJSONArray("objects");

                    for(int x=0;x<count;x++){
                        JSONObject jo=ja.getJSONObject(x);
                        ajlo[x].date=jo.getString("DATE_APPLIED");
                        ajlo[x].cname=jo.getString("COMPANY_NAME");
                        ajlo[x].job=jo.getString("JOB_TYPE");
                        ajlo[x].status=jo.getString("JOB STATUS");

                    }


                    String dateapplied[]=new String[count];
                    String companyname[]=new String[count];
                    String jobtype[]=new String[count];
                    String jobstatus[]=new String[count];
                    for (int j=0;j<count;++j){
                        dateapplied[j]=new String();
                        companyname[j]=new String();
                        jobtype[j]=new String();
                        jobstatus[j]=new String();

                        dateapplied[j]=ajlo[j].date;
                        companyname[j]=ajlo[j].cname;
                        jobtype[j]=ajlo[j].job;
                        jobstatus[j]=ajlo[j].status;
                    }


                    Intent intent=new Intent(AppliedJobsActivity.this,ItemSelectorDigilockerActivity.class);

                    intent.putExtra("dateapplied",dateapplied);
                    intent.putExtra("companyname",companyname);
                    intent.putExtra("jobtype",jobtype);
                    intent.putExtra("jobstatus",jobstatus);
                    intent.putExtra("count",count);
                    intent.putExtra("userType",getIntent().getIntExtra("userType",0));

                    startActivity(intent);



            } catch (JSONException e) {
                    e.printStackTrace();}
                }else{

                Toast.makeText(AppliedJobsActivity.this," page load failed",Toast.LENGTH_LONG).show();
            }

        }
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(AppliedJobsActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);

            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(AppliedJobsActivity.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(AppliedJobsActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(AppliedJobsActivity.this,NADSignInActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(AppliedJobsActivity.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.profile){
            intent=new Intent(AppliedJobsActivity.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.please_login){
            intent=new Intent(AppliedJobsActivity.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(AppliedJobsActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.about){
            intent=new Intent(AppliedJobsActivity.this,AboutUs.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void menuInflater(int userType) {
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
        Intent intent=new Intent(AppliedJobsActivity.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
        overridePendingTransition(R.anim.slideout,R.anim.slidein);

    }
}

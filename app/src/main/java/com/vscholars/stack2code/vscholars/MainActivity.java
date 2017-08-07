package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String str="error";
    int size=0;
    ListView jobUpdates;
    JSONObject jsono;
    JSONArray jarray;
    SwipeRefreshLayout jobUpdatesSwipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        int userType = getIntent().getIntExtra("userType", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        contentMainResolver(userType);

    }

    private void contentMainResolver(int userType) {
        if(userType==1){
            new JU().execute(" ");
        }
        if (userType==3){
            new JU().execute(" ");
        }
    }

    private void menuInflater(int userType) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout contentMainStudent=(LinearLayout)this.findViewById(R.id.content_main_student);
        LinearLayout contentMainVerifier=(LinearLayout)this.findViewById(R.id.content_main_verifier);
        if (userType==1){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            contentMainStudent.setVisibility(View.VISIBLE);

            contentMainVerifier.setVisibility(View.GONE);
            jobUpdatesSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            jobUpdatesSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    jobUpdatesSwipeRefresh.setRefreshing(true);
                    JU ju = new JU();
                    ju.execute(" ");
                }
            });
        }else if (userType==2){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_verifier);
            contentMainStudent.setVisibility(View.GONE);

            contentMainVerifier.setVisibility(View.VISIBLE);

        }else if (userType==3){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_guest);
            contentMainStudent.setVisibility(View.VISIBLE);

            contentMainVerifier.setVisibility(View.GONE);
            jobUpdatesSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            jobUpdatesSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    jobUpdatesSwipeRefresh.setRefreshing(true);
                    JU ju = new JU();
                    ju.execute(" ");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(MainActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(MainActivity.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(MainActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(MainActivity.this,NADSignInActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(MainActivity.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.profile){
            intent=new Intent(MainActivity.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.please_login){
            intent=new Intent(MainActivity.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(MainActivity.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.log_out_verifier){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(MainActivity.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(MainActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }else if (id==R.id.profile_verifier){
            intent=new Intent(MainActivity.this,ProfileViewVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.post_an_ad){
            intent=new Intent(MainActivity.this,PostAdVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.view_application_request){
            intent=new Intent(MainActivity.this,ViewJobApplicationVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.about){
            intent=new Intent(MainActivity.this,AboutUs.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }else if (id==R.id.settings){
            intent=new Intent(MainActivity.this,Settings.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }else if (id==R.id.help){
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class JU extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

//                 getting JSON Object
//                 Note that create product url accepts POST method
                jsono =JSONParser.makeHttpRequest("http://anurag.gear.host/job.php", "POST", params);
                size=jsono.getInt("count");
                if(jsono.getInt("success")==1){
                    str="successful";
                }else{

                    str=" un successful";
                }



            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }



        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            if(jobUpdatesSwipeRefresh!=null) {
                jobUpdatesSwipeRefresh.setRefreshing(false);
            }

            final j_item[] a= new j_item[size];
            for(int i=0;i<size;i++){
                a[i]=new j_item();
            }
            try {

                if(jsono.getInt("success")==1) {

                    jarray=jsono.getJSONArray("objects");

                    try {

                        for (int i = 0; i < size; i++) {

                            JSONObject temp =jarray.getJSONObject(i);

                            a[i].job = temp.getString("job_type");
                            a[i].company = temp.getString("company_name");
                            a[i].location = temp.getString("location");
                            a[i].start = temp.getString("start_date");
                            a[i].duration = temp.getString("duration");
                            a[i].stipend = temp.getString("stipend");
                            a[i].apply = temp.getString("apply_by");
                            a[i].ftpt = temp.getString("work_hour");
                            //Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
                            // Toast.makeText(getActivity(),temp.toString(),Toast.LENGTH_LONG).show();


                        }
                       // Toast.makeText(MainActivity.this,size+"",Toast.LENGTH_LONG).show();
                        jobUpdates = (ListView) findViewById(R.id.job_updates_list);
                        jobUpdates.setAdapter(new AdapterForJobUpdate(MainActivity.this,a, size));
                        jobUpdates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(MainActivity.this,ApplyForJobStudent.class);
                                intent.putExtra("companyName",a[position].company);
                                intent.putExtra("jobType",a[position].job);
                                intent.putExtra("applyBy",a[position].job);
                                intent.putExtra("stipend",a[position].stipend);
                                intent.putExtra("FTPT",a[position].ftpt);
                                intent.putExtra("duration",a[position].duration);
                                intent.putExtra("startDate",a[position].start);
                                intent.putExtra("location",a[position].location);
                                intent.putExtra("userType",getIntent().getIntExtra("userType",0));
                                startActivity(intent);


                            }
                        });

                    } catch (Exception e) {

                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }


                }
                else{
                   // Toast.makeText(MainActivity.this,"unsuccessfull",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}

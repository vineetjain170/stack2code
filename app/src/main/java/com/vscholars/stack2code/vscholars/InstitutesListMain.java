package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

/**
 * Created by vineet_jain on 24/3/17.
 */

public class InstitutesListMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button filterList;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.institutes_main_view);
        int userType=getIntent().getIntExtra("userType",0);
        filterList=(Button)findViewById(R.id.filter_options);
        filterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InstitutesListMain.this,InstitutesFilter.class);
                i.putExtra("userType",getIntent().getIntExtra("userType",0));
                startActivity(i);
                overridePendingTransition(R.anim.slideup,R.anim.slidedown);
            }
        });
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aicte Institutes");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(InstitutesListMain.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(InstitutesListMain.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(InstitutesListMain.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(InstitutesListMain.this,ViewRequestActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(InstitutesListMain.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.profile){
            intent=new Intent(InstitutesListMain.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.please_login){
            intent=new Intent(InstitutesListMain.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(InstitutesListMain.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.about){
            intent=new Intent(InstitutesListMain.this,AboutUs.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }else if (id==R.id.settings){
            intent=new Intent(InstitutesListMain.this,Settings.class);
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
        Intent intent=new Intent(InstitutesListMain.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
        overridePendingTransition(R.anim.slideout,R.anim.slidein);

    }
}

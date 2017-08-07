package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by vineet_jain on 2/4/17.
 */

public class AboutUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        int userType = getIntent().getIntExtra("userType", 0);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menuInflater(userType);
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent=new Intent(AboutUs.this,MainActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id==R.id.view_institute){
            intent=new Intent(AboutUs.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.log_out){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(AboutUs.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(AboutUs.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }
        else if (id==R.id.view_request){
            intent=new Intent(AboutUs.this,NADSignInActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.applied_jobs){
            intent=new Intent(AboutUs.this,AppliedJobsActivity.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.profile){
            intent=new Intent(AboutUs.this,StudentProfileView.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.please_login){
            intent=new Intent(AboutUs.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }
        else if (id==R.id.view_institute_guest){
            intent=new Intent(AboutUs.this,InstitutesListMain.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.log_out_verifier){
            String name = "cache.db";
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(name, Context.MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("delete from logged_in_user");
            intent =new Intent(AboutUs.this,LoginHome.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
            Toast.makeText(AboutUs.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
        }else if (id==R.id.profile_verifier){
            intent=new Intent(AboutUs.this,ProfileViewVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.post_an_ad){
            intent=new Intent(AboutUs.this,PostAdVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.view_application_request){
            intent=new Intent(AboutUs.this,ViewJobApplicationVerifier.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
            overridePendingTransition(R.anim.slidein,R.anim.slideout);
        }else if (id==R.id.settings){
            intent=new Intent(AboutUs.this,Settings.class);
            intent.putExtra("userType",getIntent().getIntExtra("userType",0));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

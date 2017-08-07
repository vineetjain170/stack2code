package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by vineet_jain on 24/3/17.
 */

public class ViewRequestActivity extends AppCompatActivity{
    ListView viewRequest;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);
        try {
            viewRequest=(ListView)findViewById(R.id.list_view_request);
            String vname[] = getIntent().getStringArrayExtra("vname");
            String cname[] = getIntent().getStringArrayExtra("cname");
            int count = getIntent().getIntExtra("count", 0);
            AdapterForViewRequest adapterForViewRequest=new AdapterForViewRequest(this,vname,cname,count);
            viewRequest.setAdapter(adapterForViewRequest);
        }catch (Exception e){
            Toast.makeText(ViewRequestActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed(){
        Intent intent=new Intent(ViewRequestActivity.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
        overridePendingTransition(R.anim.slidein,R.anim.slideout);

    }
}

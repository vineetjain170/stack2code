package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by assasin on 3/29/2017.
 */
public class DatePickerActivity extends AppCompatActivity{
    DatePicker datePicker;
    int day,month,year;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_register_page);
        datePicker=(DatePicker)findViewById(R.id.datePicker2);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(0.8*width),(int)(0.8*height));

    }
    public void onPause(){
        super.onPause();
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();
        String date=year+"-"+month+"-"+day;
        SharedPreferences sharedpreferences = getSharedPreferences("selected_date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("date",date);
        editor.commit();
        finish();
        //Intent intent=new Intent(DatePickerActivity.this,RegisterPage.class);
        //intent.putExtra("date",date);
        //startActivity(intent);

    }
}
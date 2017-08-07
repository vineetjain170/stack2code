package com.vscholars.stack2code.vscholars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by assasin on 3/29/2017.
 */

public class PopUpActivityApplyJob extends AppCompatActivity{
    TextView openDigiSignIn,submitCV;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_apply_job_student);
        getSupportActionBar().hide();
        getWindow().setLayout(500,300);
        openDigiSignIn=(TextView)findViewById(R.id.action_digi_locker);
        submitCV=(TextView)findViewById(R.id.action_upload_cv);
        submitCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpActivityApplyJob.this,"CV Uploaded",Toast.LENGTH_LONG).show();
            }
        });
        openDigiSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PopUpActivityApplyJob.this,DigiLockerSignInActivity.class);
                intent.putExtra("userType",getIntent().getIntExtra("userType",0));
                startActivity(intent);

            }
        });

    }
}
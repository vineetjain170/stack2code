package com.vscholars.stack2code.vscholars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vineet_jain on 29/3/17.
 */

public class ApplyForJobStudent extends AppCompatActivity{
    TextView tvCompanyName,tvJobType,tvApplyBy,tvStipend,tvFTPT,tvDuration,tvStartDate,tvLocation;
    Button applyForJob;
    int userType;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_apply_job_student);
        userType=getIntent().getIntExtra("userType",0);
        String companyName=getIntent().getStringExtra("companyName");
        String jobType=getIntent().getStringExtra("jobType");
        String applyBy=getIntent().getStringExtra("applyBy");
        String stipend=getIntent().getStringExtra("stipend");
        String fullTimeOrPartTime=getIntent().getStringExtra("FTPT");
        String duration=getIntent().getStringExtra("duration");
        String startDate=getIntent().getStringExtra("startDate");
        String location=getIntent().getStringExtra("location");

        tvCompanyName=(TextView)findViewById(R.id.company_name_header);
        tvJobType=(TextView)findViewById(R.id.job_type_apply);
        tvStartDate=(TextView)findViewById(R.id.start_date_apply);
        tvDuration=(TextView)findViewById(R.id.duration_apply);
        tvLocation=(TextView)findViewById(R.id.location_apply);
        tvStipend=(TextView)findViewById(R.id.stipend_apply);
        tvApplyBy=(TextView)findViewById(R.id.last_date_for_apply);
        tvFTPT=(TextView)findViewById(R.id.full_time_part_time);

        tvCompanyName.setText(companyName);
        tvJobType.setText(jobType);
        tvApplyBy.setText(applyBy);
        tvStipend.setText(stipend);
        tvFTPT.setText(fullTimeOrPartTime);
        tvDuration.setText(duration);
        tvStartDate.setText(startDate);
        tvLocation.setText(location);

        applyForJob=(Button)findViewById(R.id.apply_for_job_action);
        applyForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType==1) {
                    Intent intent = new Intent(ApplyForJobStudent.this, PopUpActivityApplyJob.class);
                    intent.putExtra("userType", getIntent().getIntExtra("userType", 0));
                    //Toast.makeText(ApplyForJobStudent.this,getIntent().getIntExtra("userType",0)+"",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }else if (userType==3){
                    Intent intent=new Intent(ApplyForJobStudent.this,LoginHome.class);
                    startActivity(intent);
                }
            }
        });

    }
}
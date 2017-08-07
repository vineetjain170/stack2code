package com.vscholars.stack2code.vscholars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by assasin on 4/1/2017.
 */

public class ItemSelectorDigilockerActivity extends AppCompatActivity{
    ListView digiLockerCertificates;
    Button actionSubmit;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_digilocker_certificate_selector);
        actionSubmit=(Button)findViewById(R.id.action_sumbit_documents_digi);
        digiLockerCertificates=(ListView)findViewById(R.id.digilocker_itemselector);
        try {
            int count = getIntent().getIntExtra("count", 0);
            String certificatesNames[] = getIntent().getStringArrayExtra("certificatesName");
            String certificatesURI[] = getIntent().getStringArrayExtra("certificatesURI");
            AdapterForDigilockerItemSelector adapterForDigilockerItemSelector=new AdapterForDigilockerItemSelector(ItemSelectorDigilockerActivity.this,certificatesNames.length,certificatesNames);
            digiLockerCertificates.setAdapter(adapterForDigilockerItemSelector);
        }catch (Exception e){
            Toast.makeText(ItemSelectorDigilockerActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }
        actionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemSelectorDigilockerActivity.this,MainActivity.class);
                intent.putExtra("userType",getIntent().getIntExtra("userType",0));
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            startActivity(new Intent(ItemSelectorDigilockerActivity.this,ProgressActivity.class));
                            int waited = 0;
                            // Splash screen pause time
                            while (waited < 1500) {
                                sleep(100);
                                waited += 100;
                            }
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                }.start();
                startActivity(intent);
            }
        });

    }
    public void onBackPressed(){
        Intent intent=new Intent(ItemSelectorDigilockerActivity.this,MainActivity.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
    }
}

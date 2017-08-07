package com.vscholars.stack2code.vscholars;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by assasin on 4/1/2017.
 */

public class AdapterForDigilockerItemSelector extends BaseAdapter{
    Context context;
    int count;
    String certificatesNames[];
    AdapterForDigilockerItemSelector(Context context,int count,String[] certificatesNames){
        try {
            this.context = context;
            this.count = count;
            this.certificatesNames = new String[count];
            for (int i = 0; i < count; ++i) {
                this.certificatesNames[i] = certificatesNames[i];
            }
        }catch (Exception e){

        }
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.listview_item_digilocker,null);
        TextView certificateName=(TextView)convertView.findViewById(R.id.textview_digilockeritem);
        certificateName.setText(certificatesNames[position]);
        final CheckBox checkBoxDigiLocker=(CheckBox)convertView.findViewById(R.id.checkbox);
        certificateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxDigiLocker.isChecked()==true) {
                    checkBoxDigiLocker.setChecked(false);
                }else if (checkBoxDigiLocker.isChecked()==false){
                    checkBoxDigiLocker.setChecked(true);
                }
            }
        });
        return convertView;
    }
}

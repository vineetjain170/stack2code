package com.vscholars.stack2code.vscholars;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by vineet_jain on 24/3/17.
 */

public class AdapterForViewRequest extends BaseAdapter{
    int count;
    String[] vname;
    String[] cname;
    Context context;
    AdapterForViewRequest(Context context,String[] vname,String[] cname,int count){
        this.context=context;
        this.count=count;
        this.vname=new String[count];
        this.cname=new String[count];
        for(int i=0;i<count;++i){
            this.vname[i]=vname[i];
            this.cname[i]=cname[i];
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
        convertView=layoutInflater.inflate(R.layout.request_item_layout,null);
        TextView requesterName=(TextView)convertView.findViewById(R.id.requester_name_nad);
        TextView certificateName=(TextView)convertView.findViewById(R.id.name_certificate_nad);
        requesterName.setText(vname[position]);
        certificateName.setText(cname[position]);
        return convertView;
    }
}

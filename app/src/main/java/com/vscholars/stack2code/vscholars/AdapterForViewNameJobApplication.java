package com.vscholars.stack2code.vscholars;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by vineet_jain on 27/3/17.
 */

public class AdapterForViewNameJobApplication extends BaseAdapter{
    int noOfApplications;
    Context context;
    String[] names;
    @Override
    public int getCount() {
        return noOfApplications;
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
        convertView=layoutInflater.inflate(R.layout.layout_for_name_display_job_application,null);
        TextView textView=(TextView)convertView.findViewById(R.id.textView26);
        textView.setText(names[position]);
        return null;
    }
}

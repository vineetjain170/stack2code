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

public class AdapterForAppliedJobs extends BaseAdapter{
    int noOfAppliedJobs;
    Context context;
    @Override
    public int getCount() {
        return noOfAppliedJobs;
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
        convertView=layoutInflater.inflate(R.layout.applied_jobs_item_layout,null);
        TextView companyName=(TextView)convertView.findViewById(R.id.job_type_apply);
        TextView date=(TextView)convertView.findViewById(R.id.date);
        TextView status=(TextView)convertView.findViewById(R.id.status);
        return null;
    }
}

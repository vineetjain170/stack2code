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

public class AdapterForJobUpdate extends BaseAdapter{
    Context context;
    int size;
    j_item values[];
    AdapterForJobUpdate(Context context,j_item values[],int size){
        this.size=size;
        this.values=new j_item[size];
        for(int i=0;i<size;++i){
            this.values[i]=new j_item();
        }
        for(int i=0;i<size;++i) {
            this.values[i].job = values[i].job;
            this.values[i].company = values[i].company;
            this.values[i].location = values[i].location;
            this.values[i].start = values[i].start;
            this.values[i].duration = values[i].duration;
            this.values[i].stipend = values[i].stipend;
            this.values[i].apply = values[i].apply;
            this.values[i].ftpt = values[i].ftpt;
        }
        this.context=context;
    }
    @Override
    public int getCount() {
        return size;
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
        convertView=layoutInflater.inflate(R.layout.layout_job_updates,null);
        TextView jobType=(TextView)convertView.findViewById(R.id.job_type);
        TextView companyName=(TextView)convertView.findViewById(R.id.company_name);
        TextView locationsJob=(TextView)convertView.findViewById(R.id.locations_job);
        TextView startDate=(TextView)convertView.findViewById(R.id.start_date_job);
        TextView durationJob=(TextView)convertView.findViewById(R.id.duration_job);
        TextView stipendJob=(TextView)convertView.findViewById(R.id.stipend_job);
        TextView applyBy=(TextView)convertView.findViewById(R.id.apply_by_job);
        TextView fullTimeOrPartTime=(TextView)convertView.findViewById(R.id.full_time_or_part_time);
        jobType.setText(values[position].job);
        companyName.setText(values[position].company);
        locationsJob.setText(values[position].location);
        startDate.setText(values[position].start);
        durationJob.setText(values[position].duration);
        stipendJob.setText(values[position].stipend);
        applyBy.setText(values[position].apply);
        fullTimeOrPartTime.setText(values[position].ftpt);
        return convertView;
    }
}

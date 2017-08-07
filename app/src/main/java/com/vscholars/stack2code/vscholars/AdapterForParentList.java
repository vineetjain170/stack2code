package com.vscholars.stack2code.vscholars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by vineet_jain on 22/3/17.
 */

public class AdapterForParentList extends BaseAdapter{
    Context context;
    String values[];
    int positionSelected;
    int color;
    AdapterForParentList(Context context,String values[],int positionSelected,int color,int[] ids){
        this.context=context;
        this.values=values;
        this.positionSelected=positionSelected;
        this.color=color;
    }
    @Override
    public int getCount() {
        return values.length;
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
        LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.parent_option_layout,null);
        TextView textView=(TextView)convertView.findViewById(R.id.textView21);
        textView.setText(values[position]);
        if (positionSelected==position){
            convertView.setBackgroundColor(color);
        }
        return convertView;
    }
}

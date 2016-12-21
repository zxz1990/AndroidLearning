package com.example.zxz.androidtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.zxz.androidtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 学智 on 2016-01-02.
 */
public class MyAdapter<T> extends BaseAdapter {

    private static String TAG = "MyAdapter";

    private List<T> mList = new ArrayList<>();

    private LayoutInflater mInflater;

    private int mResourceId;

    public MyAdapter(Context context, int resourceId) {
        super();
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResourceId = resourceId;
    }

    public void setData(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "position:"+position+",convertView:"+convertView+",ViewGroup:"+parent);
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(mResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(String.valueOf(getItem(position)));
        return convertView;
    }
}

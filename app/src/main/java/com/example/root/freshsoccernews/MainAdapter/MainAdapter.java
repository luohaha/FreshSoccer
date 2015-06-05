package com.example.root.freshsoccernews.MainAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.freshsoccernews.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-5-22.
 */
public class MainAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mList;
    private Context mContext;

    public MainAdapter(ArrayList<HashMap<String, Object>> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }
    public void onDateChange(ArrayList<HashMap<String, Object>> list) {
        this.mList = list;
        this.notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_lv_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.main_lv_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.main_lv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (viewHolder != null && viewHolder.textView != null && viewHolder.imageView != null) {
            viewHolder.textView.setText((String) mList.get(position).get("title"));
            Ion.with(viewHolder.imageView)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .load((String) mList.get(position).get("img"));
        }
        return convertView;
    }
}

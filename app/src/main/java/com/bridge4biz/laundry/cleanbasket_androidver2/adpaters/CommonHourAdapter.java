package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;

import java.util.ArrayList;


/**
 * Created by gingeraebi on 2016. 6. 30..
 */
public class CommonHourAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> timeList;

    public CommonHourAdapter(Context context, ArrayList<Integer> timeList) {
        this.context = context;
        this.timeList = timeList;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_time, parent, false);
        RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
        TextView timeText = (TextView) view.findViewById(R.id.time);
        int startHour = timeList.get(position);
        timeText.setText(String.format("%02d:00-%02d:00",startHour,startHour+ 1));

        return view;
    }

    public int getTime(int position) {
        return timeList.get(position);
    }
}

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
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.TimeDiscount;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 6. 30..
 */
public class HappyHourAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TimeDiscount> discountList;

    public HappyHourAdapter(Context context, ArrayList<TimeDiscount> discountList) {
        this.context = context;
        this.discountList = discountList;
    }

    @Override
    public int getCount() {
        return discountList.size();
    }

    @Override
    public Object getItem(int position) {
        return discountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getTime(int position) {
        return discountList.get(position).getTime();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_time_discount, parent, false);
        RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView discount = (TextView) view.findViewById(R.id.discount);

        TimeDiscount timeDiscount = discountList.get(position);
        int startHour = timeDiscount.getTime();
        time.setText(String.format("%02d:00~%02d:00",startHour,startHour+ 1));
        discount.setText(timeDiscount.getDiscount() + "%");
        container.setBackgroundColor(context.getResources().getColor(timeDiscount.getBackgorundColor()));
        return view;
    }
}

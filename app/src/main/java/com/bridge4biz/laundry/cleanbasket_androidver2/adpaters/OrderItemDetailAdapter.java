package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Item;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 7. 14..
 */
public class OrderItemDetailAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;
    private OrderInfo order;


    public OrderItemDetailAdapter(Context context, OrderInfo order) {
        this.context = context;
        this.items = order.getItem();
        this.order = order;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.item_order_detail, parent, false);
        TextView itemName = (TextView) view.findViewById(R.id.categoryName);
        TextView itemCount = (TextView) view.findViewById(R.id.itemCount);
        TextView itemPrice = (TextView) view.findViewById(R.id.itemPrice);


        Item item = items.get(position);

        itemName.setText(item.name);
        itemCount.setText(item.count + "");
        itemPrice.setText(item.price + "");


        return view;
    }
}
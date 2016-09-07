package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.OrderItemDetailActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;

import java.util.ArrayList;


/**
 * Created by gingeraebi on 2016. 6. 30..
 */
public class OrderHistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Order> orders;

    public OrderHistoryAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_order_history, parent, false);
        TextView orderNumber = (TextView) view.findViewById(R.id.orderNumber);
        TextView orderState = (TextView) view.findViewById(R.id.orderStatus);
        TextView pickUpTime = (TextView) view.findViewById(R.id.pickUpTime);
        TextView dropOffTime = (TextView) view.findViewById(R.id.dropOffTime);
        Button showDetail = (Button) view.findViewById(R.id.showDetail);

        final OrderInfo order = orders.get(position);
        Log.d("주문 내역", order.toString());

        orderNumber.setText(order.getOrder_number());
        orderState.setText(order.getStatus());
        pickUpTime.setText(order.getPrettyPickUpDate());
        dropOffTime.setText(order.getPrettyDropOffDate());

        showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CleanBasketApplication application = (CleanBasketApplication) ((Activity) context).getApplication();
                application.setDetailOrder(order);
                Intent intent = new Intent(context, OrderItemDetailActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}

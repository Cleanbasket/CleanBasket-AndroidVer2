package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.OrderItemDetailAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.dropOffPrice)
    TextView dropOffPrice;

    @BindView(R.id.totalPrice)
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_detail);
        ButterKnife.bind(this);

        OrderInfo orderInfo = ((CleanBasketApplication) getApplication()).getDetailOrder();
        OrderItemDetailAdapter orderItemDetailAdapter = new OrderItemDetailAdapter(this, orderInfo);

        listView.setAdapter(orderItemDetailAdapter);

        dropOffPrice.setText(orderInfo.getDropoff_price() + "");
        totalPrice.setText(orderInfo.getPrice() + "");
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }
}

package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class OrderCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.complete)
    public void complete() {
        finish();
    }
}

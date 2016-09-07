package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.DropOffContainerAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.PickUpContainerAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.TimePickerAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.TimePickerFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.TimeUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimePickerActivity extends AppCompatActivity implements TimePickerFragment.OnItemSelectedListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.title)
    TextView title;

    TimePickerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        Intent intent = getIntent();
        String timeType = intent.getStringExtra("TimeType");
        ButterKnife.bind(this);

        if (timeType.equals("pickUp")) {
            TimeUtil timeUtil = new TimeUtil();
            adapter = new PickUpContainerAdapter(getSupportFragmentManager());

            TimePickerFragment todayTimePicker = new TimePickerFragment();
            todayTimePicker.init(timeUtil.getTodayString(), Constants.FRAGMENT_PICKUP_TIME, this);

            if (timeUtil.getTodayCalendar().get(Calendar.DAY_OF_WEEK) != 1) {
                adapter.addFragment(todayTimePicker, timeUtil.getTodayString());
            }
            adapter.addMultiFragment(timeUtil.getWeekAsStringStartAt(timeUtil.getTodayCalendar()), this);
            viewPager.setAdapter((PickUpContainerAdapter) adapter);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            title.setText("배달 시간");
            TimeUtil timeUtil = new TimeUtil();
            adapter = new DropOffContainerAdapter(getSupportFragmentManager());
            OrderInfo order = ((CleanBasketApplication) getApplication()).getNewOrder();
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.init(timeUtil.getTwoAfterDayFormedTitle(order.pickup_date), Constants.FRAGMENT_DROPOFF_TIME, this);

            if (timeUtil.getTwoDayAfterCalender(order.pickup_date).get(Calendar.DAY_OF_WEEK) != 1) {
                adapter.addFragment(timePickerFragment, timeUtil.getTwoAfterDayFormedTitle(order.pickup_date));
            }
            adapter.addMultiFragment(timeUtil.getWeekAsStringStartAt(timeUtil.getTwoDayAfterCalender(order.pickup_date)), this);

            viewPager.setAdapter((DropOffContainerAdapter) adapter);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    public void onItemClicked() {
        setResult(3);
        finish();
    }
}

package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.CommonHourAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.HappyHourAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.TimeUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.TimeDiscount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimePickerFragment extends Fragment {

    @BindView(R.id.label_happyHours)
    RelativeLayout happyHourLabel;

    @BindView(R.id.happyHours)
    GridView happyHours;

    @BindView(R.id.commonHours)
    GridView commonHours;

    private String tabName;
    private TimeUtil timeUtil;
    private HappyHourAdapter happyHourAdapter;
    private CommonHourAdapter commonHourAdapter;
    private OrderInfo order;
    private CleanBasketApplication application;

    private OnItemSelectedListener onItemSelectedListener;

    private int fragmentstate;

    public TimePickerFragment() {
    }

    public void init(String tabName, int fragmentState, OnItemSelectedListener onItemSelectedListener) {
        this.tabName = tabName;
        this.fragmentstate = fragmentState;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        ButterKnife.bind(this, view);
        timeUtil = new TimeUtil();
        setHappyHours();
        setCommonHours();

        return view;
    }

    //수거시간, 배달시간 모두 CoommonHours값은 존재.
    private void setCommonHours() {
        commonHourAdapter = new CommonHourAdapter(getContext(), getCommonHours());
        commonHours.setAdapter(commonHourAdapter);

        commonHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                application = ((CleanBasketApplication) getActivity().getApplication());
                order = application.getNewOrder();
                int time = commonHourAdapter.getTime(position);

                if (fragmentstate == Constants.FRAGMENT_PICKUP_TIME) {
                    order.setPickupDateByDateAndTime(tabName, time);
                    application.setNewOrder((Order)order);
                } else {
                    order.setDropOffDateByDateAndTime(tabName, time);
                    application.setNewOrder((Order)order);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onItemSelectedListener.onItemClicked();
                    }
                },100);

            }
        });
    }

    //오늘일 경우에만 happyHour 보여주기
    private void setHappyHours() {
        if (tabName.equals(timeUtil.getTodayString()) && getHappyHours().size() > 0) {
            happyHourAdapter = new HappyHourAdapter(getContext(), getHappyHours());
            happyHours.setAdapter(happyHourAdapter);
        } else {
            happyHourLabel.setVisibility(View.GONE);
            happyHours.setVisibility(View.INVISIBLE);
        }

        happyHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                application = ((CleanBasketApplication) getActivity().getApplication());
                order = application.getNewOrder();
                int time = happyHourAdapter.getTime(position);
                order.setPickupDateByDateAndTime(tabName, time);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onItemSelectedListener.onItemClicked();
                    }
                },100);

            }
        });
    }


    //TODO SERVER API가 생기면 서버에서 받아오는 로직으로 변경
    private ArrayList<TimeDiscount> getHappyHours() {
        ArrayList<TimeDiscount> discountList = new ArrayList<>();
        discountList.add(new TimeDiscount(10, 15, R.color.colorPrimaryDark));
        discountList.add(new TimeDiscount(11, 10, R.color.colorPrimary));
        discountList.add(new TimeDiscount(13, 20, R.color.colorAccent));
        return discountList;
    }

    //TODO SERVER API가 생기면 서버에서 받아오는 로직으로 변경
    private ArrayList<Integer> getCommonHours() {
        CleanBasketApplication application = (CleanBasketApplication) getActivity().getApplication();
        return application.getCommonHours();
    }

}

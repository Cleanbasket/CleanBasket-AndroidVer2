package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.OrderHistoryAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnFragmentChangeClicked;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    ArrayList<Order> orders = new ArrayList<>();

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    Gson gson = new Gson();

    private OnFragmentChangeClicked onFragmentChangeClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, v);
        refreshLayout.setOnRefreshListener(this);
        getOrders();

        return v;
    }

    private void getOrders() {
        final OrderManager orderManager = new OrderManager();
        orderManager.getAllOrder(new Callback<JsonData>() {
                                     @Override
                                     public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                                         JsonData jsonData = response.body();
                                         if (jsonData.constant == Constants.SUCCESS) {
                                             orders = gson.fromJson(jsonData.data, new TypeToken<List<Order>>() {
                                             }.getType());

                                             OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(getContext(), orders);
                                             listView.setAdapter(orderHistoryAdapter);
                                             listView.deferNotifyDataSetChanged();
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<JsonData> call, Throwable t) {

                                     }
                                 }

        );
    }

    @Override
    public void onRefresh() {
        getOrders();
        refreshLayout.setRefreshing(false);
    }

    public void setOnFragmentChangeClicked(OnFragmentChangeClicked onFragmentChangeClicked) {
        this.onFragmentChangeClicked  = onFragmentChangeClicked;
    }

    @OnClick(R.id.orderLatest)
    public void onBack() {
        onFragmentChangeClicked.onChangeClick();
    }


}

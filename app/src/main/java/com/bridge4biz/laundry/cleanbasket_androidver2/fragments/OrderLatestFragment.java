package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.OrderItemDetailActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.dialog.OrderUpdateDialog;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnFragmentChangeClicked;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderLatestFragment extends BaseFragment {

    private ArrayList<Order> orders;
    private Order order;
    private Gson gson = new Gson();

    private OnFragmentChangeClicked onFragmentChangeClicked;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_latest, container, false);
        ButterKnife.bind(this, v);

        getOrder();

        return v;
    }

    private void getOrder() {
        OrderManager orderManager = new OrderManager();
        orderManager.getRecentOrder(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                orders = gson.fromJson(jsonData.data, new TypeToken<List<Order>>() {
                }.getType());
                if (!orders.isEmpty()) {
                    order = orders.get(0);
                    setView(order);
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    @BindView(R.id.cleanPartnerPlan)
    TextView cleanPartnerPlan;

    @BindView(R.id.pickUpTime)
    TextView pickUpTimeText;

    @BindView(R.id.dropOffTime)
    TextView dropOffTimeText;

    @BindView(R.id.profileImage)
    ImageView profileImage;

    private void setView(Order order) {
        if (order.state < 3) {
            cleanPartnerPlan.setText(order.getPickupMan() + "가 " + order.getPrettyPickUpDate() + "에 방문 예정입니다.");
            if (order.getPickupInfo() != null) {
                Picasso.with(getContext()).load(AddressConstant.SERVER_URL + "/" + order.getPickupInfo().img).into(profileImage);
            }
        } else {
            cleanPartnerPlan.setText(order.getDropoffMan() + "가 " + order.getPrettyDropOffDate() + "에 방문 예정입니다.");
            if (order.getDropoffInfo() != null)
                Picasso.with(getContext()).load(AddressConstant.SERVER_URL + "/" + order.getDropoffInfo().img).into(profileImage);
        }

        pickUpTimeText.setText(order.getPrettyPickUpDate());
        dropOffTimeText.setText(order.getPrettyDropOffDate());
        setOrderTimeLine();
    }

    @BindView(R.id.timeLine)
    ImageView orderTimeLine;

    private void setOrderTimeLine() {
        switch (order.getState()) {
            case 1: {
                Picasso.with(getContext()).load(R.drawable.ic_timeline1).into(orderTimeLine);
                break;
            }
            case 2: {
                Picasso.with(getContext()).load(R.drawable.ic_timeline2).into(orderTimeLine);
                break;
            }
            case 3: {
                Picasso.with(getContext()).load(R.drawable.ic_timeline3).into(orderTimeLine);
                break;
            }
            case 4: {
                Picasso.with(getContext()).load(R.drawable.ic_timeline4).into(orderTimeLine);
                break;
            }
        }
    }


    @OnClick(R.id.phoneCall)
    public void phoneCall() {

        if (order.getState() == 1) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.pickupInfo.phone));
            startActivity(intent);
        } else if (order.getState() == 3) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.dropoffInfo.phone));
            startActivity(intent);
        }
    }

    @OnClick(R.id.orderDetail)
    public void showDetail() {
        Intent intent = new Intent(getActivity(), OrderItemDetailActivity.class);
        ((CleanBasketApplication) getActivity().getApplication()).setDetailOrder(order);
        startActivity(intent);
    }

    @OnClick(R.id.orderDetails)
    public void showDetails() {
        onFragmentChangeClicked.onChangeClick();

    }

    @OnClick(R.id.orderChange)
    public void showOrderUpdateDialog() {
        OrderUpdateDialog orderUpdateDialog = OrderUpdateDialog.newInstance();
        orderUpdateDialog.show(getActivity().getFragmentManager(), "주문 수정");
    }

    public void setOnFragmentChangeClicked(OnFragmentChangeClicked onFragmentChangeClicked) {
        this.onFragmentChangeClicked = onFragmentChangeClicked;
    }

}

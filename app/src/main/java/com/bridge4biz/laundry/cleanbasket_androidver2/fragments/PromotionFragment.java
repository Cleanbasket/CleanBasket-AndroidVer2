package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.PromotionAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.dialog.PromotionCodeDialog;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnFragmentChangeClicked;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnPromotionAddListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Promotion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 8. 16..
 */
public class PromotionFragment extends BaseFragment {

    @BindView(R.id.promotionRecycler)
    RecyclerView promotionRecycler;

    private RecyclerView.LayoutManager layoutManager;
    private OnFragmentChangeClicked onFragmentChangeClicked;
    private PromotionAdapter promotionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promotion, container, false);
        ButterKnife.bind(this, v);
        layoutManager = new LinearLayoutManager(getContext());
        setPromotionRecycler();
        return v;
    }

    private void setPromotionRecycler() {
        MyInfoManager myInfoManager = new MyInfoManager();
        myInfoManager.getPromotions(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                handleResponse(jsonData);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    private void handleResponse(JsonData jsonData) {
        switch (jsonData.constant) {
            case Constants.SUCCESS:
                Gson gson = new Gson();
                ArrayList<Promotion> promotions = gson.fromJson(jsonData.data, new TypeToken<ArrayList<Promotion>>() {
                }.getType());

                promotionAdapter = new PromotionAdapter(getContext(), promotions);
                promotionRecycler.setLayoutManager(layoutManager);
                promotionRecycler.setAdapter(promotionAdapter);
                break;
            case Constants.ERROR :
                break;
        }
    }

    public void setOnFragmentChangeClicked(OnFragmentChangeClicked onFragmentChangeClicked) {
        this.onFragmentChangeClicked = onFragmentChangeClicked;
    }

    @OnClick(R.id.addPromotionCode)
    public void onAddPromotionCodeClicked() {
        OnPromotionAddListener onPromotionAddListener = new OnPromotionAddListener() {
            @Override
            public void onPromotionAdded() {
                promotionAdapter.notifyDataSetChanged();
            }
        };
        PromotionCodeDialog promotionCodeDialog = PromotionCodeDialog.newInstance(onPromotionAddListener);
        promotionCodeDialog.show(getActivity().getFragmentManager(), "프로모션 등록");
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onFragmentChangeClicked.onChangeClick();
    }

}

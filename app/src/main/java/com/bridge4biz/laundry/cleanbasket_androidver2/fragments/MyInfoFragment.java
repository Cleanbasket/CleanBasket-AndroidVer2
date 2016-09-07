package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.AddressManageActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.auth.AuthManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.auth.LoginActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnFragmentChangeClicked;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.MemberInfo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyInfoFragment extends BaseFragment {
    private MemberInfo myInfo;
    private Gson gson = new Gson();
    private OnFragmentChangeClicked onFragmentChangeClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_info, container, false);
        ButterKnife.bind(this, v);
        getMyInfo();
        return v;
    }

    public void getMyInfo() {
        MyInfoManager myInfoManager = new MyInfoManager();
        myInfoManager.getMyInfo(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == Constants.SUCCESS) {
                    myInfo = gson.fromJson(jsonData.data, MemberInfo.class);
                    setView();
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    @BindView(R.id.cleanLevelImage)
    ImageView cleanLevelImage;
    @BindView(R.id.mileage)
    TextView mileageText;
    @BindView(R.id.cleanLevelText)
    TextView cleanLevelText;

    private void setView() {
        if (myInfo == null) return;

        if (myInfo.total >= 150000 && myInfo.total < 300000) {
            cleanLevelText.setText(makeCleanLevelSentence("SILVER"));
            Picasso.with(getContext()).load(R.drawable.ic_level_2).into(cleanLevelImage);
        } else if (myInfo.total >= 300000 && myInfo.total < 500000) {
            cleanLevelText.setText(makeCleanLevelSentence("GOLD"));
            Picasso.with(getContext()).load(R.drawable.ic_level_3).into(cleanLevelImage);
        } else if (myInfo.total >= 500000) {
            cleanLevelText.setText(makeCleanLevelSentence("LOVE"));
            Picasso.with(getContext()).load(R.drawable.ic_level_4).into(cleanLevelImage);
        }

        DecimalFormat df = new DecimalFormat("#,##0");
        String mileage = df.format(myInfo.mileage);

        mileageText.setText("마일리지: " + mileage + "점");
    }

    private String makeCleanLevelSentence(String level) {
        return "고객님은 현재 " + level + "등급입니다";
    }

    @OnClick(R.id.setting)
    public void logout() {
        AuthManager authManager = new AuthManager();
        authManager.logout();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.addressManage)
    public void showAddressManage() {
        Intent intent = new Intent(getActivity(), AddressManageActivity.class);
        startActivity(intent);
    }

    public void setOnFragmentChangeClicked(OnFragmentChangeClicked onFragmentChangeClicked) {
        this.onFragmentChangeClicked = onFragmentChangeClicked;
    }

    @OnClick(R.id.promotion)
    public void onPromotionClicked() {
        onFragmentChangeClicked.onChangeClick();
    }

    @OnClick(R.id.cardManage)
    public void onCardManageClicked() {

    }

}

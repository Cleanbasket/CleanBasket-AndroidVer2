package com.bridge4biz.laundry.cleanbasket_androidver2.network;

import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Code;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gingeraebi on 2016. 8. 12..
 */
public class MyInfoManager {

    private MyInfoInterface myInfoInterface;

    public MyInfoManager() {
        myInfoInterface = RetrofitBase.getInstance().getRetrofit().create(MyInfoInterface.class);
    }

    public void getMyInfo(Callback<JsonData> myInfoCallback) {
        Call<JsonData> call = myInfoInterface.getMyInfo();
        call.enqueue(myInfoCallback);
    }

    public void getMileage(Callback<JsonData> mileageCallback) {
        Call<JsonData> call = myInfoInterface.getMileage();
        call.enqueue(mileageCallback);
    }

    public void getPromotions(Callback<JsonData> promotionCallback) {
        Call<JsonData> call = myInfoInterface.getPromotion();
        call.enqueue(promotionCallback);
    }

    public void addPromotion(Callback<JsonData> promotionCallback, String promotionCode) {
        Call<JsonData> call = myInfoInterface.addPromotion(new Code(promotionCode));
        call.enqueue(promotionCallback);
    }

}

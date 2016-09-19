package com.bridge4biz.laundry.cleanbasket_androidver2.network;

import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.RegId;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 9. 7..
 */
public class FirebaseManager {
    private FirebaseInterface firebaseInterface;

    public FirebaseManager() {
        this.firebaseInterface = RetrofitBase.getInstance().getRetrofit().create(FirebaseInterface.class);
    }

    public void sendRefreshTokenToServer(String refreshToken) {
        Call<JsonData> call = firebaseInterface.sendRefreshToken(new RegId(refreshToken));
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    public void sendReviewToServer(Review review) {
        Call<JsonData> call = firebaseInterface.sendReview(review);
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }
}

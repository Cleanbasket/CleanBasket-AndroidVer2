package com.bridge4biz.laundry.cleanbasket_androidver2.network;

import android.util.Log;

import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

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
        Call<JsonData> call = firebaseInterface.sendRefreshToken(refreshToken);
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if(jsonData.constant == Constants.SUCCESS) {
                    Log.d("파이어베이스", "교체 성공");
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }
}

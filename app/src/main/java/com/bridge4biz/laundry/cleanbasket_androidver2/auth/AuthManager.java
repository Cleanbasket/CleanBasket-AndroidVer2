package com.bridge4biz.laundry.cleanbasket_androidver2.auth;


import com.bridge4biz.laundry.cleanbasket_androidver2.network.RetrofitBase;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 8. 3..
 */
public class AuthManager {

    private AuthInterface authInterface;

    public AuthManager() {
        authInterface = RetrofitBase.getInstance().getRetrofit().create(AuthInterface.class);
    }

    public void login(Callback<JsonData> callback, String email, String password, boolean remeber, String regid) {
        Call<JsonData> call = authInterface.login(email,password,remeber, regid);
        call.enqueue(callback);
    }

    public void signUpWithKaKao(Callback<JsonData> kakaoCallback, UserData userData) {
        Call<JsonData> call = authInterface.singUpKaKao(userData);
        call.enqueue(kakaoCallback);
    }

    public void logout() {
        Call<JsonData> call = authInterface.logout();
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    public void signUpWithEmail(Callback<JsonData> emailCallback, String email, String password) {
        Call<JsonData> call = authInterface.requestJoin(new UserData(email,password));
        call.enqueue(emailCallback);
    }
}

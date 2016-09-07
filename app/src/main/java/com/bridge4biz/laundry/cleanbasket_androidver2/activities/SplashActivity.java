package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.auth.AuthInterface;
import com.bridge4biz.laundry.cleanbasket_androidver2.auth.LoginActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.RetrofitBase;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.SharedPreferenceBase;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.google.gson.Gson;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    private AuthInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        service = RetrofitBase.getInstance().getRetrofit().create(AuthInterface.class);
        Handler delayStartActivityHandler = new Handler();
        delayStartActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginCheck();
            }
        }, 2000);
    }

    private void loginCheck(){

        Call<JsonData> response = service.loginCheck();
        response.enqueue(new Callback<JsonData>() {
            Intent intent = null;
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                switch (jsonData.constant) {
                    case Constants.SESSION_EXPIRED:
                        Toast.makeText(SplashActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        break;
                    case Constants.SESSION_VALID:
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        if(SharedPreferenceBase.getSharedPreference("MANAGER", new HashSet<String>()).contains(jsonData.data.toString())){
                            SharedPreferenceBase.putSharedPreference("IsManager",true);
                        }else {
                            SharedPreferenceBase.putSharedPreference("IsManager",false);
                        }
                        intent.putExtra("userID",jsonData.data.toString());
                        break;
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

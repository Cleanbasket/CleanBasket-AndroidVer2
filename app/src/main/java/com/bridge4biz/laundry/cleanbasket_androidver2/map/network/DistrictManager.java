package com.bridge4biz.laundry.cleanbasket_androidver2.map.network;

import android.util.Log;

import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.District;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.RetrofitBase;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 7. 25..
 */
public class DistrictManager {

    private ArrayList<District> districts;
    private DistrictService service;
    private Gson gson = new Gson();

    public DistrictManager() {
        service = RetrofitBase.getInstance().getRetrofit().create(DistrictService.class);
        service.getDistrict().enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                districts = gson.fromJson(response.body().data, new TypeToken<List<District>>() {
                }.getType());
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }


    public ArrayList<District> getDistricts() {
        return districts;
    }

    public boolean isDeliverableDistrict(String address) {
        Log.e("주소", address);

        for (District district : districts) {

            if (address.startsWith(district.city + " " + district.district + " " +district.dong)) {
                Log.e("서비스 가능 지역" ,(district.city + " " + district.district + " " + district.dong));
                return true;
            }else if (!district.dong.equals("") && address.endsWith(district.dong)){
                return true;
            }
        }
        return false;
    }

}

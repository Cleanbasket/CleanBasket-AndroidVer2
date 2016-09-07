package com.bridge4biz.laundry.cleanbasket_androidver2.map.network;


import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DistrictService {
    @GET("district")
    public Call<JsonData> getDistrict();

}

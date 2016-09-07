package com.bridge4biz.laundry.cleanbasket_androidver2.network;


import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Code;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 8. 12..
 */
public interface MyInfoInterface {
    @GET(AddressConstant.GET_AUTH_MEMBER_INFO)
    Call<JsonData> getMyInfo();

    @GET(AddressConstant.GET_MILEAGE)
    Call<JsonData> getMileage();

    @GET(AddressConstant.GET_PROMOTION)
    Call<JsonData> getPromotion();

    @POST(AddressConstant.ADD_PROMOTION)
    Call<JsonData> addPromotion(@Body Code code);
}

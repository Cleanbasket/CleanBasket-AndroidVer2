package com.bridge4biz.laundry.cleanbasket_androidver2.network;

import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 9. 7..
 */
public interface FirebaseInterface  {

    @FormUrlEncoded
    @POST(AddressConstant.FCM_REGID)
    Call<JsonData> sendRefreshToken(@Field("regid") String token);
}

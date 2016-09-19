package com.bridge4biz.laundry.cleanbasket_androidver2.network;

import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.RegId;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Review;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 9. 7..
 */
public interface FirebaseInterface  {

    @POST(AddressConstant.FCM_REGID)
    Call<JsonData> sendRefreshToken(@Body RegId regId);

    @POST(AddressConstant.ADD_REVIEW)
    Call<JsonData> sendReview(@Body Review review);
}

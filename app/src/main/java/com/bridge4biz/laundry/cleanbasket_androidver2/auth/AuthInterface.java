package com.bridge4biz.laundry.cleanbasket_androidver2.auth;


import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface AuthInterface {

    @POST(AddressConstant.LOGIN)
    @FormUrlEncoded
    Call<JsonData> login(@Field("email") String email, @Field("password") String password, @Field("remember") Boolean remember, @Field("regid") String regid);

    @POST(AddressConstant.LOGIN_CHECK)
    Call<JsonData> loginCheck();

    @POST(AddressConstant.REGISTER)
    Call<JsonData> singUpKaKao(@Body UserData userData);

    @POST(AddressConstant.REGISTER)
    Call<JsonData> requestJoin(@Body UserData user);

    @GET(AddressConstant.LOGOUT)
    Call<JsonData> logout();

}

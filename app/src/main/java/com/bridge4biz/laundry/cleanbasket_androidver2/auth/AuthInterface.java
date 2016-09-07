package com.bridge4biz.laundry.cleanbasket_androidver2.auth;


import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.UserData;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST(AddressConstant.AUTH_REGISTER)
    Call<JsonData> requestJoin(@Part MultipartBody.Part file, @Part("email") String email, @Part("password") String password, @Part("name") String name, @Part("phone") String phone, @Part("birthday") String birthday);

    @GET(AddressConstant.LOGOUT)
    Call<JsonData> logout();

}

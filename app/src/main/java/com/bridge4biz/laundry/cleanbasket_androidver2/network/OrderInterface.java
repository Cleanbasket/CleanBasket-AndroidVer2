package com.bridge4biz.laundry.cleanbasket_androidver2.network;


import com.bridge4biz.laundry.cleanbasket_androidver2.constants.AddressConstant;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 7. 11..
 */
public interface OrderInterface {
    @POST(AddressConstant.ADD_ORDER)
    Call<JsonData> addOrder(@Body Order order);

    @GET(AddressConstant.GET_ALL_ORDER)
    Call<JsonData> getAllOrder();

    @GET(AddressConstant.GET_RECENT_ORDER)
    Call<JsonData> getRecentOrder();

    @GET(AddressConstant.GET_ADDRESS)
    Call<JsonData> getAddress();

    @POST(AddressConstant.GET_ADDRESS)
    Call<JsonData> sendAddress(@Body AddressInfo addressInfo);

    @GET(AddressConstant.GET_ORDER_ITEM)
    Call<JsonData> getItemsInfo();

    @POST(AddressConstant.DELETE_ADDRESS)
    Call<JsonData> deleteAddress(@Body AddressInfo addressInfo);

}
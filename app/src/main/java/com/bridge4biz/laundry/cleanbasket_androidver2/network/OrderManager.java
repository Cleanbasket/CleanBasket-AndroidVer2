package com.bridge4biz.laundry.cleanbasket_androidver2.network;


import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gingeraebi on 2016. 7. 7..
 */
public class OrderManager {

    private OrderInterface orderInterface;

    public OrderManager() {
        orderInterface = RetrofitBase.getInstance().getRetrofit().create(OrderInterface.class);
    }

    public void addOrder(Order order, Callback<JsonData> orderCallback) {
        Call<JsonData> call = orderInterface.addOrder(order);
        call.enqueue(orderCallback);
    }

    public void getAllOrder(Callback<JsonData> orderCallback) {
        Call<JsonData> call = orderInterface.getAllOrder();
        call.enqueue(orderCallback);
    }

    public void getRecentOrder(Callback<JsonData> orderCallback) {
        Call<JsonData> call = orderInterface.getRecentOrder();
        call.enqueue(orderCallback);
    }

    public void getAddress(Callback<JsonData> orderCallback) {
        Call<JsonData> call = orderInterface.getAddress();
        call.enqueue(orderCallback);
    }

    public void getItemsInfo(Callback<JsonData> itemCallback) {
        Call<JsonData> call = orderInterface.getItemsInfo();
        call.enqueue(itemCallback);
    }

    public void sendAddress(Callback<JsonData> addressCallback, AddressInfo addressInfo) {
        Call<JsonData> call = orderInterface.sendAddress(addressInfo);
        call.enqueue(addressCallback);

    }

    public void deleteAddress(Callback<JsonData> addressCallback, AddressInfo addressInfo) {
        Call<JsonData> call = orderInterface.deleteAddress(addressInfo);
        call.enqueue(addressCallback);
    }

}

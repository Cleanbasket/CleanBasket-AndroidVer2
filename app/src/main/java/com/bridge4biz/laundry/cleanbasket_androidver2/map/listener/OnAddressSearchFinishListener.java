package com.bridge4biz.laundry.cleanbasket_androidver2.map.listener;


import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.GeocodeResponse;

import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 7. 21..
 */
public interface OnAddressSearchFinishListener {
    public void onAddressSearchSuccess(Response<GeocodeResponse> response);
}

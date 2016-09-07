package com.bridge4biz.laundry.cleanbasket_androidver2.map.network;


import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.CustomLatLng;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.GeocodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapService {
    @GET("api/geocode/json")
    public Call<GeocodeResponse> getGeoCodeByLatLng(@Query("latlng") CustomLatLng latlng, @Query("language") String language);

    @GET("api/geocode/json")
    public Call<GeocodeResponse> getGeoCodeByAddress(@Query("address") String address, @Query("language") String language);
}

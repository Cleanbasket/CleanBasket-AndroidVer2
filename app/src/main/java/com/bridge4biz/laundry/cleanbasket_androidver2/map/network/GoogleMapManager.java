package com.bridge4biz.laundry.cleanbasket_androidver2.map.network;

import android.util.Log;

import com.bridge4biz.laundry.cleanbasket_androidver2.map.listener.OnAddressSearchFinishListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.listener.OnLatLngSearchSuccessListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.AddressComponent;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.CustomLatLng;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.Geocode;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.GeocodeResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by gingeraebi on 2016. 7. 21..
 */
public class GoogleMapManager {
    private GoogleMap map;
    private GoogleMapService googleMapService;
    private String SUBLOCALITY_LV2 = "sublocality_level_2";
    private LatLng currentLatLng;

    public GoogleMapManager(GoogleMap map) {
        this.map = map;
        this.googleMapService = GoogleMapRetrofitBase.getInstance().getRetrofit().create(GoogleMapService.class);
    }

    public LatLng getCurrentLatLng() {
        return currentLatLng;
    }

    public void searchGeoCodeByAddress(String address, final OnAddressSearchFinishListener onAddressSearchFinishListener) {
        Call<GeocodeResponse> call = googleMapService.getGeoCodeByAddress(address, "ko");
        call.enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                onAddressSearchFinishListener.onAddressSearchSuccess(response);
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {

            }
        });
    }

    public void searchGeoCodeByLatLng(final LatLng location, final OnLatLngSearchSuccessListener onLatLngSearchSuccessListener) {
        Call<GeocodeResponse> call = googleMapService.getGeoCodeByLatLng(new CustomLatLng(location), "ko");
        call.enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                onLatLngSearchSuccessListener.onLatLngSearchSuccess(response);
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                Log.e("GoogleMapManager", "데이터를 가져오는데 실패하였습니다.\n" + t.getMessage());
            }
        });
    }


    public String getAddressFromGeoCode(GeocodeResponse geocodeResponse) {
        String address = "";
        for (Geocode geocode : geocodeResponse.getResults()) {
            ArrayList<AddressComponent> addressComponents = (ArrayList<AddressComponent>) geocode.getAddress_components();
            for (AddressComponent component : addressComponents) {
                if (component.getTypes().contains(SUBLOCALITY_LV2) && geocode.getFormatted_address().endsWith("동")) {
                    address = geocode.getFormatted_address();
                    currentLatLng = new LatLng(geocode.getGeometry().getLocation().getLat(), geocode.getGeometry().getLocation().getLng());
                    break;
                }
            }
        }

        String withoutCountryNameAddress = "";

        for (String splitAddress : address.split(" ")) {
            if (splitAddress.equals("대한민국") || splitAddress.equals("경기도")) {
                continue;
            }
            withoutCountryNameAddress += splitAddress + " ";
        }

        return withoutCountryNameAddress;
    }

    public void addMarker(String markerTitle, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerTitle);
        markerOptions.position(position);
        map.addMarker(markerOptions);
    }

    public void addMarker(String markerTitle, String details, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerTitle);
        markerOptions.snippet(details);
        markerOptions.position(position);
        map.addMarker(markerOptions);
    }

}

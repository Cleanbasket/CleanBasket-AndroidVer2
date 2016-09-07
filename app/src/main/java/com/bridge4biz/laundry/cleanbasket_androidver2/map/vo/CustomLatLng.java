package com.bridge4biz.laundry.cleanbasket_androidver2.map.vo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gingeraebi on 2016. 7. 22..
 */
public class CustomLatLng  {
    private LatLng latLng;

    public CustomLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return String.format("%f,%f", latLng.latitude, latLng.longitude);
    }
}

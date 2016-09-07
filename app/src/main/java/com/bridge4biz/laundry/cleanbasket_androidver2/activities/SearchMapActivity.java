package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.dialog.AddressDetailDialog;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnAddressDialogDoneListner;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.listener.OnAddressSearchFinishListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.listener.OnLatLngSearchSuccessListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.network.DistrictManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.network.GoogleMapManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.GeocodeResponse;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMapActivity extends AppCompatActivity implements TextView.OnEditorActionListener, OnMapReadyCallback, GoogleMap.OnCameraChangeListener, OnAddressSearchFinishListener, OnLatLngSearchSuccessListener {

    private GoogleMap googleMap;
    private GoogleMapManager mapManager;
    private boolean isServiceArea = false;
    private DistrictManager districtManager;
    @BindView(R.id.mapView)
    MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        ButterKnife.bind(this);

        initMapView();
        searchEdit.setOnEditorActionListener(this);
        districtManager = new DistrictManager();
    }

    private void initMapView() {
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        mapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng gangNam = new LatLng(37.497887, 127.027656);
        googleMap = map;
        googleMap.setOnCameraChangeListener(this);
        googleMap.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gangNam, 13));
        mapManager = new GoogleMapManager(googleMap);
        mapManager.searchGeoCodeByLatLng(gangNam, this);
    }

    //주소를 입력받아 search하는 로직
    @BindView(R.id.searchEdit)
    EditText searchEdit;
    @BindView(R.id.addressResult)
    TextView addressResult;
    @BindView(R.id.addressResultBar)
    RelativeLayout addressResultBar;



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        searchMap();
        return true;
    }

    public void searchMap() {
        if (!searchEdit.getText().toString().equals("")) {
            mapManager.searchGeoCodeByAddress(searchEdit.getText().toString(), this);
            hideSoftKeyboard();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        inputAddressDetail.setVisibility(View.GONE);
        LatLng current = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
        mapManager.searchGeoCodeByLatLng(current, this);

    }

    @Override
    public void onAddressSearchSuccess(Response<GeocodeResponse> response) {
        findCurrentAddress(response);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mapManager.getCurrentLatLng()));
    }

    @Override
    public void onLatLngSearchSuccess(Response<GeocodeResponse> response) {
        findCurrentAddress(response);
    }

    @BindView(R.id.inputAddressDetail)
    RelativeLayout inputAddressDetail;

    private void findCurrentAddress(Response<GeocodeResponse> response) {
        GeocodeResponse result = response.body();
        String address = mapManager.getAddressFromGeoCode(result);
        if (address.equals("")) {
            inputAddressDetail.setVisibility(View.GONE);
            addressResult.setText(getResources().getString(R.string.not_correct_address));
            addressResultBar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else if (!districtManager.isDeliverableDistrict(address)) {
            inputAddressDetail.setVisibility(View.GONE);
            addressResult.setText(getResources().getString(R.string.not_service_district));
            addressResultBar.setBackgroundColor(getResources().getColor(R.color.gray));
        } else {
            inputAddressDetail.setVisibility(View.VISIBLE);
            addressResult.setText(address);
            addressResultBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
    }

    @OnClick(R.id.inputAddressDetail)
    public void showAddressDetailInputDialog() {
        final String address = addressResult.getText().toString();
        AddressDetailDialog detailDialog = AddressDetailDialog.newInstance(new OnAddressDialogDoneListner() {
            @Override
            public void onAddressInputDone(String detailAddress) {
                CleanBasketApplication application = (CleanBasketApplication) getApplication();
                application.newOrder.address = address;
                application.newOrder.addr_remainder = detailAddress;

                OrderManager orderManager = new OrderManager();
                orderManager.sendAddress(new Callback<JsonData>() {
                    @Override
                    public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                        JsonData jsonData = response.body();
                        if(jsonData.constant == 1) {
                            Log.d("주소추가", "성공");
                            finish();
                        }else{
                            Log.e("주소추가", "샐패 , constant :" + jsonData.constant);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonData> call, Throwable t) {
                        Log.e("주소추가", "샐패");
                    }
                }, new AddressInfo(address, detailAddress));


            }
        }, address);

        detailDialog.show(getFragmentManager(),"상세 주소");

    }

}

package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.ItemListActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.MainActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.OrderDetailActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.TimePickerActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.AddressAutoCompleteAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.dialog.PhoneNumDialog;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnAddressSelectedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnKeyBackPressedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnPhoneNumDialogDoneListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.listener.OnAddressSearchFinishListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.network.GoogleMapManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.map.vo.GeocodeResponse;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.TimeUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Item;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends BaseFragment implements OnKeyBackPressedListener, OnMapReadyCallback {

    private CleanBasketApplication application;

    private Gson gson = new Gson();

    private GoogleMap googleMap;
    private GoogleMapManager mapManager;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.phoneNum)
    TextView phoneNumText;
    @BindView(R.id.pickUpDateTime)
    TextView pickUpDateTime;
    @BindView(R.id.dropOffDateTime)
    TextView dropOffDateTime;
    @BindView(R.id.showItems)
    RelativeLayout showItemsLayout;
    @BindView(R.id.onTheSpot)
    RelativeLayout onTheSpotLayout;
    @BindView(R.id.showItemsText)
    TextView showItemsText;
    @BindView(R.id.onTheSpotText)
    TextView onTheSpotText;
    @BindView(R.id.pickUpManImage)
    ImageView pickUpManImage;
    @BindView(R.id.basket)
    ImageView basketImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, v);

        PermissionCheck();
        setDefaultOrder();

        return v;
    }

    private void PermissionCheck() {
        //지도를 사용하기 위해 권한 체크를 한다.
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initMapView();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission tedPermission = new TedPermission(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION);

        tedPermission.check();
    }

    private void initMapView() {
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        mapView.onResume();
    }


    private void setDefaultOrder() {
        if(application == null) application = ((CleanBasketApplication) getActivity().getApplication());
        //Application 영역에 저장되어 있는 order를 가져온다.
        OrderInfo order = application.getNewOrder();

        //order가 서버에 전송되기 위해 기본적으로 가지고 있어야 되는 인자들을 초기화 해준다.
        order.item = new ArrayList<>();
        order.coupon = new ArrayList<>();
        order.memo = "";
        order.price = 2000;
        order.payment_method = 0;
        order.dropoff_price = 2000;
        //기본 주소 설정
        setAddress(order);
        //휴대폰 번호 가져와서 설정
        setPhoneNum(order);
        //기본 주문시간 설정
        setDefaultOrderTime(order);
    }

    private void setDefaultOrderTime(OrderInfo order) {
        TimeUtil timeUtil = new TimeUtil();
        order.pickup_date = timeUtil.getDbFormedDefaultPickupTime();
        order.dropoff_date = timeUtil.getDbFormedDefaultDropOffTime();

        pickUpDateTime.setText(order.getPrettyPickUpDate());
        dropOffDateTime.setText(order.getPrettyDropOffDate());
    }

    private void setAddress(final OrderInfo order) {
        OrderManager orderManager = new OrderManager();
        Callback<JsonData> addressCallback = new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                handlerAddressResponse(jsonData, order);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e("주소 가져오기 실패", t.getMessage());
            }
        };

        orderManager.getAddress(addressCallback);
    }

    private void handlerAddressResponse(JsonData jsonData, OrderInfo order) {
        switch (jsonData.constant) {
            case Constants.SUCCESS: {

                Gson gson = new Gson();
                ArrayList<AddressInfo> addressList = gson.fromJson(jsonData.data, new TypeToken<List<AddressInfo>>(){}.getType());

                //리스트에 주소 추가하기 버튼을 만들기 위해 넣어줌
                addressList.add(new AddressInfo(-1));
                setAutoCompleteAdapter(addressList);
                String recentAddress = getRecentAddress(addressList);
                order.address = recentAddress;
                autoCompleteTextView.setText(recentAddress);

            }
        }
    }

    private void setAutoCompleteAdapter(ArrayList<AddressInfo> addressList) {
        AddressAutoCompleteAdapter arrayAdapter = new AddressAutoCompleteAdapter(getActivity(), R.layout.item_address_history, addressList);

        arrayAdapter.setOnAddressSelectedListener(new OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(String address, String addressDetail) {
                //아이템이 선택되었을 시 하는 동작을 설정
                OrderInfo order = application.getNewOrder();
                order.address = address;
                order.addr_remainder = addressDetail;
                application.setNewOrder((Order) order);
                autoCompleteTextView.dismissDropDown();
                autoCompleteTextView.setText(order.getFullAddress());

            }
        });
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private String getRecentAddress(ArrayList<AddressInfo> addressList) {
        AddressInfo recentAddressInfo = addressList.get(0);

        if (recentAddressInfo.getAdrid() == -1 || recentAddressInfo.getPrettyAddress().isEmpty()) {
            return "최근 주문한 주소가 없습니다.";
        }
        return recentAddressInfo.getPrettyAddress();
    }


    private void setPhoneNum(final OrderInfo order) {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                String phoneNumber = tMgr.getLine1Number();
                if (!phoneNumber.isEmpty()) {
                    if (phoneNumber.startsWith("+82")) {
                        phoneNumber = "0" + phoneNumber.split("\\+82")[1];
                    }
                    phoneNumText.setText(phoneNumber);
                    order.phone = phoneNumber;
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {

            }
        };

        TedPermission tedPermission = new TedPermission(getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE);

        tedPermission.check();

    }


    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng gangNam = new LatLng(37.497887, 127.027656);
        googleMap = map;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gangNam, 13));

        mapManager = new GoogleMapManager(googleMap);

        if (!autoCompleteTextView.getText().toString().equals("")) {
            moveCameraToAddress();
        }
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                showAddressHistory();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //뒤로가기키 커스텀 설정위해 사용
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
    }

    //  뒤로가기키 커스텀 설정위해 사용
    @Override
    public void onBack() {
        //autoCompleteView가 보여주는 중에는 그걸 끈다.
        if (autoCompleteTextView.isPopupShowing()) {
            autoCompleteTextView.dismissDropDown();
        } else {
            MainActivity activity = (MainActivity) getActivity();
            activity.setOnKeyBackPressedListener(null);
            activity.onBackPressed();
        }
    }

    @OnClick(R.id.autoCompleteTextView)
    public void showAddressHistory() {
        autoCompleteTextView.showDropDown();
    }

    @OnClick(R.id.phoneNumContainer)
    public void showPhoneInputDialog() {
        PhoneNumDialog phoneNumDialog = PhoneNumDialog.newInstance(new OnPhoneNumDialogDoneListener() {
            @Override
            public void onPhoneNumInputDone(String phoneNum) {
                setPhoneNum(phoneNum);
            }
        });

        phoneNumDialog.show(getActivity().getFragmentManager(), "");
    }


    private void setPhoneNum(String phoneNum) {
        if (phoneNum.isEmpty()) {
            Toast.makeText(getContext(), "잘못된 번호 양식입니다,", Toast.LENGTH_LONG).show();
            return;
        } else {
            phoneNumText.setText(phoneNum);
        }
    }

    @OnClick(R.id.pickUpDate)
    void showPickUpFragment() {
        Intent intent = new Intent(getActivity(), TimePickerActivity.class);
        intent.putExtra("TimeType", "pickUp");
        startActivityForResult(intent, 3);
    }

    @OnClick(R.id.dropOffDate)
    void showDropOffFragmnet() {
        Intent intent = new Intent(getActivity(), TimePickerActivity.class);
        intent.putExtra("TimeType", "dropOff");
        startActivityForResult(intent, 3);
    }

    Boolean showItemsClicked = false, onTheSpotClicked = true;

    @OnClick({R.id.showItems, R.id.onTheSpot})
    void selectItems(View v) {
        if (v.getId() == R.id.showItems) {
            startItemListActivity();
            if (!showItemsClicked) {
                setItemClicked();
                showItemsClicked = true;
                onTheSpotClicked = false;
            }

        } else if (v.getId() == R.id.onTheSpot && !onTheSpotClicked) {
            setOnTheSpotClicked();
            showItemsClicked = false;
            onTheSpotClicked = true;
        }
    }

    @OnClick(R.id.submit)
    public void onOrderClicked() {
        setOrderByInputInfo();
        startOrderDeatilActivity();
    }

    private void setOnTheSpotClicked() {
        showItemsLayout.setBackgroundColor(getResources().getColor(R.color.white_gray));
        onTheSpotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        showItemsText.setTextColor(getResources().getColor(R.color.black));
        onTheSpotText.setTextColor(getResources().getColor(R.color.white));

        Picasso.with(getContext()).load(R.drawable.ic_order_payment).into(basketImage);
        Picasso.with(getContext()).load(R.drawable.ic_order_pickupreality_select).into(pickUpManImage);
    }

    private void setItemClicked() {
        showItemsLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        onTheSpotLayout.setBackgroundColor(getResources().getColor(R.color.white_gray));
        showItemsText.setTextColor(getResources().getColor(R.color.white));
        onTheSpotText.setTextColor(getResources().getColor(R.color.black));

        Picasso.with(getContext()).load(R.drawable.ic_order_payment_select).into(basketImage);
        Picasso.with(getContext()).load(R.drawable.ic_order_pickupreality).into(pickUpManImage);
    }


    private void setOrderByInputInfo() {
        OrderInfo order = application.getNewOrder();
        order.phone = phoneNumText.getText().toString();
        if (order.address == null) {
            order.address = autoCompleteTextView.getText().toString();
        }
        if (onTheSpotClicked) {
            order.item.add(new Item(10, 1));
        }
    }


    private void moveCameraToAddress() {
        mapManager.searchGeoCodeByAddress(autoCompleteTextView.getText().toString(), new OnAddressSearchFinishListener() {
            @Override
            public void onAddressSearchSuccess(Response<GeocodeResponse> response) {
                GeocodeResponse result = response.body();
                mapManager.getAddressFromGeoCode(result);
                LatLng currentLatLng = mapManager.getCurrentLatLng();
                if (currentLatLng != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                }
            }
        });
    }

    private void startItemListActivity() {
        setOrderByInputInfo();
        Intent intent = new Intent(getActivity(), ItemListActivity.class);
        startActivityForResult(intent, 1);
    }

    private void startOrderDeatilActivity() {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onResume() {
        super.onResume();
        application = ((CleanBasketApplication) getActivity().getApplication());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        OrderInfo order = application.getNewOrder();

        if (requestCode == 1) {
            setItemClicked();
            Picasso.with(getContext()).load(R.drawable.ic_order_payment_select).into(basketImage);
            showItemsText.setText("총" + order.getPrice() + "원");
        } else if (requestCode == 2) {
            setOnTheSpotClicked();
            showItemsText.setText("견적내기(가격보기)");
        } else if (requestCode == 3) {
            Log.e("3번코드로 들어온다.", order.pickup_date);
            pickUpDateTime.setText(order.getPrettyPickUpDate());
            dropOffDateTime.setText(order.getPrettyDropOffDate());
        }
    }

}

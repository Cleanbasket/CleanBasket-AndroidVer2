package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.AddressListAdpapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressManageActivity extends AppCompatActivity {

    @BindView(R.id.addressList)
    ListView addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);

        OrderManager orderManager = new OrderManager();

        orderManager.getAddress(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == Constants.SUCCESS) {
                    Gson gson = new Gson();
                    ArrayList<AddressInfo> addressInfos = gson.fromJson(jsonData.data, new TypeToken<List<AddressInfo>>() {
                    }.getType());
                    setAddressListAdapter(addressInfos);
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });

    }

    public void setAddressListAdapter(ArrayList<AddressInfo> addressInfos) {
        AddressListAdpapter addressListAdpapter = new AddressListAdpapter(this, R.layout.item_address, addressInfos);
        addressList.setAdapter(addressListAdpapter);
    }
}

package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 8. 22..
 */
public class AddressListAdpapter extends ArrayAdapter<AddressInfo> {

    private Context context;
    private int resourceId;
    private ArrayList<AddressInfo> addressInfos;

    @BindView(R.id.address)
    TextView addressText;

    @BindView(R.id.delete)
    ImageView deleteImage;

    public AddressListAdpapter(Context context, int resourceId, ArrayList<AddressInfo> addressInfos) {
        super(context, resourceId, addressInfos);

        this.context = context;
        this.resourceId = resourceId;
        this.addressInfos = addressInfos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);

        ButterKnife.bind(this, view);

        addressText.setText(addressInfos.get(position).getPrettyAddress());


        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddressInfo addressInfo = addressInfos.get(position);
                OrderManager orderManager = new OrderManager();
                orderManager.deleteAddress(new Callback<JsonData>() {
                    @Override
                    public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                        JsonData jsonData = response.body();
                        if(jsonData.constant == 1) {
                            addressInfos.remove(addressInfo);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonData> call, Throwable t) {

                    }
                }, addressInfo);
            }
        });
        return view;
    }

}

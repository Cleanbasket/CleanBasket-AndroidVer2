package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.SearchMapActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnAddressSelectedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.AddressInfo;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 7. 18..
 */
public class AddressAutoCompleteAdapter extends ArrayAdapter<AddressInfo> {

    private OnAddressSelectedListener onAddressSelectedListener;
    private ArrayList<AddressInfo> addressInfos;
    private int resourceId;

    public AddressAutoCompleteAdapter(Context context, int resource, ArrayList<AddressInfo> addressInfos) {
        super(context, resource, addressInfos);
        this.addressInfos = addressInfos;
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AddressInfo addressInfo = addressInfos.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        //주소 추가하기의 경우 눌렀을 때 다른 액티비티로 이동.
        if(addressInfo.getAdrid() == -1) {
            View view = layoutInflater.inflate(R.layout.item_add_address, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SearchMapActivity.class);
                    getContext().startActivity(intent);
                }
            });
            return view;

        }

        View view = layoutInflater.inflate(R.layout.item_address_history,parent,false);
        final TextView address = (TextView) view.findViewById(R.id.address);
        address.setText(addressInfo.getPrettyAddress());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressSelectedListener.onAddressSelected(addressInfo.getAddress(), addressInfo.getAddr_remainder());
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((AddressInfo) (resultValue)).getPrettyAddress();

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            filterResults.values = addressInfos;
            filterResults.count = addressInfos.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    public void setOnAddressSelectedListener(OnAddressSelectedListener onAddressSelectedListener){
        this.onAddressSelectedListener = onAddressSelectedListener;
    }

}

package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.ItemListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemListFragment extends Fragment {

    @BindView(R.id.listView)
    ListView listView;

    private ItemListAdapter adapterForListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, v);

        listView.setAdapter(adapterForListView);

        return v;
    }

    public void setAdapterForListView(ItemListAdapter adapterForListView) {
        this.adapterForListView = adapterForListView;
    }


}

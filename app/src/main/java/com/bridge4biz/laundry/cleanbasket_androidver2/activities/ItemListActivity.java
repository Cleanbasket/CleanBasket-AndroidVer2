package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.ItemListContainerAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.event.ItemCountMapEvent;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.BusProvider;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Item;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.ItemResponse;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemListActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private OrderManager orderManager;

    private ItemListContainerAdapter adapter;
    private ItemResponse itemResponse;

    private HashMap<Integer, Integer> itemPriceMap = new HashMap<>();
    private HashMap<Integer, Integer> itemCountMap = new HashMap<>();

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        orderManager = new OrderManager();
        orderManager.getItemsInfo(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == 1) {

                    itemResponse = gson.fromJson(jsonData.data, ItemResponse.class);
                    itemPriceMap = itemResponse.getItemPriceMap();

                    adapter = new ItemListContainerAdapter(ItemListActivity.this, getSupportFragmentManager());
                    adapter.setItemResponse(itemResponse);
                    adapter.initFragments();

                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });

    }

    @BindView(R.id.itemCount)
    TextView itemCountText;
    @BindView(R.id.totalPrice)
    TextView totalPriceText;

    @Subscribe
    public void onItemCountMapEvent(ItemCountMapEvent itemCountMapEvent) {
        itemCountMap = itemCountMapEvent.getItemCountMap();
        int totalPrice = getTotalPrice();

        totalPriceText.setText(getResources().getString(R.string.total_price) + " " + totalPrice + getResources().getString(R.string.won));

        int itemCount = 0;

        for (int itemCode : itemCountMap.keySet()) {
            itemCount += itemCountMap.get(itemCode);
        }

        itemCountText.setText(getResources().getString(R.string.item) + itemCount + getResources().getString(R.string.gae));


        BusProvider.getInstance().unregister(this);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    private int getTotalPrice() {
        int totalPrice = 0;
        for (int itemCode : itemCountMap.keySet()) {
            totalPrice += (itemPriceMap.get(itemCode) * itemCountMap.get(itemCode));
        }

        if (totalPrice < 20000) {
            totalPrice += 2000;
        }

        return totalPrice;
    }

    @OnClick(R.id.submit)
    public void makeOrder() {
        CleanBasketApplication application = (CleanBasketApplication) getApplication();
        OrderInfo order = application.getNewOrder();
        order.item = makeItems();
        order.price = getTotalPrice();
        if(getTotalPrice() >= 20000) {
            order.dropoff_price = 0;
        }
        application.setNewOrder((Order) order);
        setResult(1);
        finish();
    }



    private ArrayList<Item> makeItems() {
        ArrayList<Item> items = new ArrayList<>();

        for (int itemCode : itemCountMap.keySet()) {
            items.add(new Item(itemCode, itemCountMap.get(itemCode)));
        }

        if (items.isEmpty()) {
            items.add(new Item(10, 1));
        }
        return items;
    }

}

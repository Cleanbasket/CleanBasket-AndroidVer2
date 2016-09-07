package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bridge4biz.laundry.cleanbasket_androidver2.event.ItemCountMapEvent;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.ItemListFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnItemListClickedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.BusProvider;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.ItemCode;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.ItemResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by gingeraebi on 2016. 7. 28..
 */
public class ItemListContainerAdapter extends FragmentStatePagerAdapter implements OnItemListClickedListener {

    private List<Fragment> fragments;
    protected List<String> fragmentTitles;

    private ItemResponse itemResponse;
    private Context context;

    private HashMap<Integer, Integer> itemCountMap = new HashMap<>();

    public ItemListContainerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();

    }

    public void setItemResponse(ItemResponse itemResponse) {
        this.itemResponse = itemResponse;
    }

    public void initFragments() {
        addAllCategoryFragment();
        for (int categoryNum = 1; categoryNum <= 10; categoryNum++) {
            addCategoryFragmentByNum(categoryNum);
        }
    }

    private void addAllCategoryFragment() {
        ItemListFragment allCategoryFragment = new ItemListFragment();


        ArrayList<ItemCode> itemCodes = new ArrayList<>();
        for (int categoryNum = 1; categoryNum <= 10; categoryNum++) {
            String categoryName = itemResponse.getItemCategoryName(categoryNum);
            //Add Header
            itemCodes.add(new ItemCode(-100, categoryName));
            //Add Items
            itemCodes.addAll(itemResponse.getItemsByCategory(categoryNum));
        }

        ItemListAdapter adapter = new ItemListAdapter(context, itemCodes, itemCountMap);
        adapter.setOnItemListClickedListener(this);
        allCategoryFragment.setAdapterForListView(adapter);

        fragments.add(allCategoryFragment);
        fragmentTitles.add("ALL");
    }

    private void addCategoryFragmentByNum(int categoryNum) {
        String categoryName = itemResponse.getItemCategoryName(categoryNum);

        ItemListFragment itemListFragment = new ItemListFragment();

        ArrayList<ItemCode> itemCodes = new ArrayList<>();
        itemCodes.add(new ItemCode(-100, categoryName));
        itemCodes.addAll(itemResponse.getItemsByCategory(categoryNum));

        ItemListAdapter adapter = new ItemListAdapter(context, itemCodes, itemCountMap);
        adapter.setOnItemListClickedListener(this);
        itemListFragment.setAdapterForListView(adapter);

        fragments.add(itemListFragment);
        fragmentTitles.add(itemResponse.getItemCategoryName(categoryNum));
    }

    @Override
    public void onIncreaseClicked(ItemCode itemCode) {
        int itemCodeNum = itemCode.itemCode;

        if (itemCountMap.containsKey(itemCodeNum)) {
            int count = itemCountMap.get(itemCodeNum);
            itemCountMap.remove(itemCodeNum);
            itemCountMap.put(itemCodeNum, count + 1);
        } else {
            itemCountMap.put(itemCodeNum, 1);
        }

        BusProvider.getInstance().post(new ItemCountMapEvent(itemCountMap));
    }

    @Override
    public void onDecreaseClicked(ItemCode itemCode) {
        int itemCodeNum = itemCode.itemCode;

        if (itemCountMap.containsKey(itemCodeNum)) {
            int count = itemCountMap.get(itemCodeNum);
            if (count > 1) {
                itemCountMap.remove(itemCodeNum);
                itemCountMap.put(itemCodeNum, count - 1);
            } else if (count == 1) {
                itemCountMap.remove(itemCodeNum);
            }
        }

        BusProvider.getInstance().post(new ItemCountMapEvent(itemCountMap));
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    //ViewPager가 화면을 다시 그리도록 강제하기 위해서 사용
    //adapter에 notifyDataSetChanged가 불리우면 호출됨.
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}

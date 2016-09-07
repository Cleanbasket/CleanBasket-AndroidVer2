package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gingeraebi on 2016. 7. 11..
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    public void replaceFragment(int position, Fragment fragment) {
        mFragments.remove(position);
        mFragments.add(position, fragment);
        notifyDataSetChanged();
    }

    @Override
    //ViewPager가 화면을 다시 그리도록 강제하기 위해서 사용
    //adapter에 notifyDataSetChanged가 불리우면 호출됨.
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}

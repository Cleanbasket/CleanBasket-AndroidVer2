package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.TimePickerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gingeraebi on 2016. 7. 1..
 */
public class PickUpContainerAdapter extends FragmentStatePagerAdapter implements TimePickerAdapter {
    private  List<Fragment> mFragments = new ArrayList<>();
    protected  List<String> mFragmentTitles;

    public PickUpContainerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentTitles = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addMultiFragment(ArrayList<String> titles, TimePickerFragment.OnItemSelectedListener onItemSelectedListener) {
        for(String title : titles) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.init(title, Constants.FRAGMENT_PICKUP_TIME, onItemSelectedListener);
            mFragments.add(timePickerFragment);
            mFragmentTitles.add(title);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    //ViewPager가 화면을 다시 그리도록 강제하기 위해서 사용
    //adapter에 notifyDataSetChanged가 불리우면 호출됨.
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}

package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.ServiceIntroFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gingeraebi on 2016. 7. 13..
 */
public class ServiceIntroViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();

    public ServiceIntroViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addMultiFragment(Resources resources) {
        ServiceIntroFragment intro1 = new ServiceIntroFragment();
        intro1.setInfo(resources.getString(R.string.intro1));
        intro1.setBackground(R.drawable.intro_1);
        mFragments.add(intro1);

        ServiceIntroFragment intro2 = new ServiceIntroFragment();
        intro2.setInfo(resources.getString(R.string.intro2));
        intro2.setBackground(R.drawable.intro_2);
        mFragments.add(intro2);

        ServiceIntroFragment intro3 = new ServiceIntroFragment();
        intro3.setInfo(resources.getString(R.string.intro3));
        intro3.setBackground(R.drawable.intro_3);
        mFragments.add(intro3);

        ServiceIntroFragment intro4 = new ServiceIntroFragment();
        intro4.setInfo(resources.getString(R.string.intro4));
        intro4.setBackground(R.drawable.intro_4);
        mFragments.add(intro4);
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

}
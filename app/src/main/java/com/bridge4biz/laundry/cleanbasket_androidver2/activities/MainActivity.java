package com.bridge4biz.laundry.cleanbasket_androidver2.activities;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.MainViewPagerAdapter;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.MyInfoFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.OrderFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.OrderHistoryFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.OrderLatestFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.PromotionFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.QuestionFragment;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnFragmentChangeClicked;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnKeyBackPressedListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private OnKeyBackPressedListener mOnKeyBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;


    private void initFragment() {
        fm = getSupportFragmentManager();


        mainViewPagerAdapter = new MainViewPagerAdapter(fm);


        final OrderLatestFragment orderLatestFragment = new OrderLatestFragment();
        final OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();

        orderLatestFragment.setOnFragmentChangeClicked(new OnFragmentChangeClicked() {
            @Override
            public void onChangeClick() {
                replaceFragment(1, orderHistoryFragment);
            }
        });

        orderHistoryFragment.setOnFragmentChangeClicked(new OnFragmentChangeClicked() {
            @Override
            public void onChangeClick() {
                replaceFragment(1,orderLatestFragment);
            }
        });

        final MyInfoFragment myInfoFragment = new MyInfoFragment();
        final PromotionFragment promotionFragment = new PromotionFragment();

        myInfoFragment.setOnFragmentChangeClicked(new OnFragmentChangeClicked() {
            @Override
            public void onChangeClick() {
                replaceFragment(2, promotionFragment);
            }
        });

        promotionFragment.setOnFragmentChangeClicked(new OnFragmentChangeClicked() {
            @Override
            public void onChangeClick() {
                replaceFragment(2, myInfoFragment);
            }
        });

        mainViewPagerAdapter.addFragment(new OrderFragment());
        mainViewPagerAdapter.addFragment(orderLatestFragment);
        mainViewPagerAdapter.addFragment(myInfoFragment);
        mainViewPagerAdapter.addFragment(new QuestionFragment());

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabAt(0, R.drawable.tab_order);
        setTabAt(1, R.drawable.tab_order_info);
        setTabAt(2, R.drawable.tab_my_info);
        setTabAt(3, R.drawable.tab_question);


    }

    private void replaceFragment(int positoin, Fragment fragment) {
        mainViewPagerAdapter.replaceFragment(positoin, fragment);
        tabLayout.setupWithViewPager(viewPager);
        setTabAt(0, R.drawable.tab_order);
        setTabAt(1, R.drawable.tab_order_info);
        setTabAt(2, R.drawable.tab_my_info);
        setTabAt(3, R.drawable.tab_question);
    }

    private void setTabAt(int i, int resId) {
        LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_main_tab, null);
        ImageView tabImage = (ImageView) tab.findViewById(R.id.tabIamge);
        tabImage.setBackgroundResource(resId);
        tabLayout.getTabAt(i).setCustomView(tab);
    }

    public void setOnKeyBackPressedListener(OnKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
        } else {
            super.onBackPressed();
        }
    }

}
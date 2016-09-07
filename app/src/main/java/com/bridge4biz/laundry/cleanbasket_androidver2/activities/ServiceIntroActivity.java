package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.adpaters.ServiceIntroViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class ServiceIntroActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewPagerIndicator)
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_intro);
        ButterKnife.bind(this);

        ServiceIntroViewPagerAdapter viewPagerAdapter = new ServiceIntroViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addMultiFragment(getResources());
        viewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(viewPager);
    }
}

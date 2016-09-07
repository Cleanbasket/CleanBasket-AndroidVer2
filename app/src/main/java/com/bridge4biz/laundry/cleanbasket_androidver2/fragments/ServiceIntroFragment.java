package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServiceIntroFragment extends Fragment {

    String info;
    int resId;

    @BindView(R.id.info)
    TextView infoText;

    @BindView(R.id.background)
    ImageView background;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_intro, container, false);
        ButterKnife.bind(this, v);
        infoText.setText(info);
        background.setImageDrawable(getResources().getDrawable(resId));
        return v;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBackground(int resId) {
        this.resId = resId;
    }


}

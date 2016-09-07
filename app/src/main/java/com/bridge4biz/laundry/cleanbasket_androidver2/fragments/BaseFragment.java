package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;

import android.support.v4.app.Fragment;

import com.bridge4biz.laundry.cleanbasket_androidver2.activities.MainActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnKeyBackPressedListener;


/**
 * Created by gingeraebi on 2016. 8. 30..
 */
public class BaseFragment extends Fragment implements OnKeyBackPressedListener {
    @Override
    public void onBack() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();
    }
}

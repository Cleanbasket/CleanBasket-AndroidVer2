package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.support.v4.app.Fragment;

import com.bridge4biz.laundry.cleanbasket_androidver2.fragments.TimePickerFragment;

import java.util.ArrayList;


/**
 * Created by gingeraebi on 2016. 7. 11..
 */
public interface TimePickerAdapter {
    public void addFragment(Fragment fragment, String title);
    public void addMultiFragment(ArrayList<String> titles, TimePickerFragment.OnItemSelectedListener onItemSelectedListener);
}

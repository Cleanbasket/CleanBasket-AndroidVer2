package com.bridge4biz.laundry.cleanbasket_androidver2.fragments;


import android.Manifest;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gingeraebi on 2016. 8. 16..
 */
public class QuestionFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.startZopimButton)
    public void startZopim() {
        ZopimChat.init("3IvIR4PxJLUypCdpgbBmJLBjYby32CVD");
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                VisitorInfo visitorInfo = new VisitorInfo.Builder()
                        .phoneNumber(getPhoneNumber())
                        .build();

                ZopimChat.setVisitorInfo(visitorInfo);
                ZopimChatActivity.startActivity(getActivity(), null);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {

            }
        };

        TedPermission tedPermission = new TedPermission(getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE);

        tedPermission.check();


    }


    private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        String mPhoneNumber;
        mPhoneNumber = tMgr.getLine1Number();

        if (mPhoneNumber != null) {
            mPhoneNumber = mPhoneNumber.replace("+82", "0");

            if (mPhoneNumber.length() != 11) {
                return "";
            }
        }

        return mPhoneNumber;
    }

}

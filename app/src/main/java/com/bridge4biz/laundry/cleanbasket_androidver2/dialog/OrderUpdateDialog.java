package com.bridge4biz.laundry.cleanbasket_androidver2.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gingeraebi on 2016. 8. 22..
 */
public class OrderUpdateDialog extends DialogFragment {
    public static OrderUpdateDialog newInstance() {
        return new OrderUpdateDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = inflater.inflate(R.layout.dialog_order_update, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    boolean timeSelected = false;
    boolean itemSelected = false;
    boolean cancelSelected = false;
    @OnClick({R.id.itemUpdateContainer, R.id.timeUpdateContainer, R.id.orderCancelContainer})
    public void onCardSelected(View view) {
        switch (view.getId()) {
            case R.id.timeUpdateContainer :
                timeSelected();
                break;
            case R.id.itemUpdateContainer :
                itemSelected();
                break;
            case R.id.orderCancelContainer :
                cancelSelected();
                break;
        }
    }

    private void timeSelected() {
        timeSelected = true;
        itemSelected = false;
        cancelSelected = false;
        recycleView();
    }

    private void itemSelected() {
        timeSelected = false;
        itemSelected = true;
        cancelSelected = false;
        recycleView();
    }

    private void cancelSelected() {
        timeSelected = false;
        itemSelected = false;
        cancelSelected = true;
        recycleView();
    }

    @BindView(R.id.timeUpdateContainer) RelativeLayout timeUpdateContainer;
    @BindView(R.id.itemUpdateContainer) RelativeLayout itemUpdateContainer;
    @BindView(R.id.orderCancelContainer) RelativeLayout orderCancelContainer;

    private void recycleView() {
        int colorPrimary = getResources().getColor(R.color.colorPrimary);
        int whiteGray = getResources().getColor(R.color.white_gray);
        if(timeSelected) {
            timeUpdateContainer.setBackgroundColor(colorPrimary);
            itemUpdateContainer.setBackgroundColor(whiteGray);
            orderCancelContainer.setBackgroundColor(whiteGray);
        }else if (itemSelected) {
            timeUpdateContainer.setBackgroundColor(whiteGray);
            itemUpdateContainer.setBackgroundColor(colorPrimary);
            orderCancelContainer.setBackgroundColor(whiteGray);
        }else if (cancelSelected) {
            timeUpdateContainer.setBackgroundColor(whiteGray);
            itemUpdateContainer.setBackgroundColor(whiteGray);
            orderCancelContainer.setBackgroundColor(colorPrimary);
        }
    }

}

package com.bridge4biz.laundry.cleanbasket_androidver2.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnCardSelectedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.PaymentData;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 9. 5..
 */
public class PaymentSelectDialog extends DialogFragment {
    private OnCardSelectedListener onCardSelectedListener;
    private CleanBasketApplication cleanBasketApplication;
    private boolean isCardExist = false;

    public static PaymentSelectDialog newInstance(OnCardSelectedListener onCardSelectedListener) {
        PaymentSelectDialog paymentSelectDialog = new PaymentSelectDialog();
        paymentSelectDialog.init(onCardSelectedListener);
        return paymentSelectDialog;
    }

    private void init(OnCardSelectedListener onCardSelectedListener) {
        this.onCardSelectedListener = onCardSelectedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_payment, container, false);
        ButterKnife.bind(this, rootView);

        checkCardExist();
        cleanBasketApplication = (CleanBasketApplication) getActivity().getApplication();
        onInAppClicked();


        return rootView;
    }

    @OnClick({R.id.inAppContainer, R.id.onTheSpotContainer})
    public void onPaymentSelected(View v) {
        switch (v.getId()) {
            case R.id.inAppContainer:
                onInAppClicked();
                break;
            case R.id.onTheSpotContainer:
                onTheSpotClicked();
                break;
        }
    }

    @BindView(R.id.inAppContainer)
    RelativeLayout inAppContainer;
    @BindView(R.id.inAppText)
    TextView inAppText;
    @BindView(R.id.inAppCardImage)
    ImageView inAppCardImage;


    @BindView(R.id.onTheSpotContainer)
    RelativeLayout onTheSpotContainer;
    @BindView(R.id.onTheSpotText)
    TextView onTheSpotText;
    @BindView(R.id.onTheSpotImage)
    ImageView onTheSpotImage;

    private void onInAppClicked() {
        inAppContainer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        inAppText.setTextColor(getResources().getColor(R.color.white));

        onTheSpotContainer.setBackgroundColor(getResources().getColor(R.color.white));
        onTheSpotText.setTextColor(getResources().getColor(R.color.colorPrimary));

        Picasso.with(getActivity()).load(R.drawable.ic_account_card_select).into(inAppCardImage);
        Picasso.with(getActivity()).load(R.drawable.ic_account_card).into(onTheSpotImage);

        Order order = cleanBasketApplication.getNewOrder();
        order.payment_method = 3;
        cleanBasketApplication.setNewOrder(order);

        if(!isCardExist) {

        }
    }

    private void onTheSpotClicked() {
        onTheSpotContainer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        onTheSpotText.setTextColor(getResources().getColor(R.color.white));

        inAppContainer.setBackgroundColor(getResources().getColor(R.color.white));
        inAppText.setTextColor(getResources().getColor(R.color.colorPrimary));

        Picasso.with(getActivity()).load(R.drawable.ic_account_card_select).into(onTheSpotImage);
        Picasso.with(getActivity()).load(R.drawable.ic_account_card).into(inAppCardImage);

        Order order = cleanBasketApplication.getNewOrder();
        order.payment_method = 1;
        cleanBasketApplication.setNewOrder(order);
    }

    @OnClick(R.id.selectBar)
    public void onSelectBarClicked() {
        onCardSelectedListener.onCardSelected();
        dismiss();
    }

    private void checkCardExist() {
        MyInfoManager myInfoManager = new MyInfoManager();
        myInfoManager.checkPaymentExist(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();

                if (jsonData.constant == 1) {
                    Gson gson = new Gson();
                    PaymentData paymentData = gson.fromJson(jsonData.data, PaymentData.class);
                    if (paymentData.isTrueData()) {
                        isCardExist = true;
                    } else {
                        isCardExist = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }
}

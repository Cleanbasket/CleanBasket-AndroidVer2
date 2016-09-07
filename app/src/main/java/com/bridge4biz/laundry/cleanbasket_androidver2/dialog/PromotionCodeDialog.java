package com.bridge4biz.laundry.cleanbasket_androidver2.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnPromotionAddListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JaeBong on 2016-07-27.
 */
public class PromotionCodeDialog extends DialogFragment {
    private OnPromotionAddListener onPromotionAddListener;

    public static PromotionCodeDialog newInstance(OnPromotionAddListener onPromotionAddListener) {
        PromotionCodeDialog promotionCodeDialog = new PromotionCodeDialog();
        promotionCodeDialog.init(onPromotionAddListener);
        return promotionCodeDialog;
    }

    private void init(OnPromotionAddListener onPromotionAddListener) {
        this.onPromotionAddListener = onPromotionAddListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_promotion_add, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @BindView(R.id.promotionCodeEdit)
    EditText promotionCodeEdit;

    @OnClick(R.id.addPromotionCode)
    public void addPromotionCode() {
        if (promotionCodeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "코드를 입력해주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        MyInfoManager myInfoManager = new MyInfoManager();
        myInfoManager.addPromotion(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                handleResponse(jsonData);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        }, promotionCodeEdit.getText().toString());
    }

    private void handleResponse(JsonData jsonData) {
        switch (jsonData.constant) {
            case Constants.SUCCESS :
                onPromotionAddListener.onPromotionAdded();
                dismiss();
                break;
            case Constants.IMPOSSIBLE :
                Toast.makeText(getActivity(),"등록 불가능한 프로모션입니다.",Toast.LENGTH_LONG).show();
                break;
            case Constants.ERROR :
                Toast.makeText(getActivity(), "존재하지 않는 프로모션코드입니다.",Toast.LENGTH_LONG).show();
                break;
        }

    }

    @OnClick(R.id.back)
    public void dismiss() {
        dismiss();
    }

}

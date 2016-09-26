package com.bridge4biz.laundry.cleanbasket_androidver2.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.PaymentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by JaeBong on 2016-07-27.
 */
public class PaymentAddDialog extends DialogFragment {
    private PaymentData paymentData;

    public static PaymentAddDialog newInstance() {
        PaymentAddDialog addressDetailDialog = new PaymentAddDialog();
        return addressDetailDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_card_add, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    @OnClick(R.id.addCardBar)
    public void addCard() {
        addData();

        if(!checkBox.isChecked()) {
            Toast.makeText(getActivity(),"나이스 페이 약관 동의해주세요!",Toast.LENGTH_LONG).show();
        }
        if (paymentData.isTrueData()) {
            MyInfoManager myInfoManager = new MyInfoManager();
            myInfoManager.addPayment(new Callback<JsonData>() {
                @Override
                public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                    JsonData jsonData = response.body();
                    if (jsonData.constant != 1) {
                        showAddCardFailToast();
                    } else {
                        dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonData> call, Throwable t) {
                    showAddCardFailToast();
                }
            }, paymentData);
        }
    }

    private void showAddCardFailToast() {
        Toast.makeText(getActivity(), "카드 등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
    }

    @BindView(R.id.cardNum1)
    EditText cardNum1Edit;
    @BindView(R.id.cardNum2)
    EditText cardNum2Edit;
    @BindView(R.id.cardNum3)
    EditText cardNum3Edit;
    @BindView(R.id.cardNum4)
    EditText cardNum4Edit;
    @BindView(R.id.password)
    EditText cardPwEdit;
    @BindView(R.id.month)
    EditText expMonthEdit;
    @BindView(R.id.year)
    EditText expYearEdit;
    @BindView(R.id.birth)
    EditText birthEdit;

    private void addData() {
        paymentData = new PaymentData();

        String cardNum1 = cardNum1Edit.getText().toString();
        String cardNum2 = cardNum2Edit.getText().toString();
        String cardNum3 = cardNum3Edit.getText().toString();
        String cardNum4 = cardNum4Edit.getText().toString();
        String totalCardNum = setCardNum(new String[]{cardNum1, cardNum2, cardNum3, cardNum4});

        if (totalCardNum == null) return;
        paymentData.setCardNo(totalCardNum);

        String cardPw = cardPwEdit.getText().toString();
        if (cardPw == null || cardPw.isEmpty()) return;
        paymentData.setCardPw(cardPw);

        String expMonth = expMonthEdit.getText().toString();
        if (expMonth == null || expMonth.isEmpty()) return;
        paymentData.setExpMonth(expMonth);

        String expYear = expYearEdit.getText().toString();
        if (expYear == null || expYear.isEmpty()) return;
        paymentData.setExpYear(expMonth);

        String birth = birthEdit.getText().toString();
        if (birth == null || birth.isEmpty()) return;
        paymentData.setIDNo(birth);

    }

    private String setCardNum(String[] cardNums) {
        String totalCardNum = "";

        for (String cardNum : cardNums) {
            if (cardNum.isEmpty()) return null;

            totalCardNum += cardNum;
        }

        return totalCardNum;
    }
}

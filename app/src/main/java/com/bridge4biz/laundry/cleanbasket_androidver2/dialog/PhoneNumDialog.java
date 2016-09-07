package com.bridge4biz.laundry.cleanbasket_androidver2.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnPhoneNumDialogDoneListener;


/**
 * Created by JaeBong on 2016-07-27.
 */
public class PhoneNumDialog extends DialogFragment {
    private EditText phoneNumEdit;
    private OnPhoneNumDialogDoneListener phoneNumDialogDoneListener;


    public static PhoneNumDialog newInstance(OnPhoneNumDialogDoneListener phoneNumDialogDoneListener) {
        PhoneNumDialog addressDetailDialog = new PhoneNumDialog();
        addressDetailDialog.init(phoneNumDialogDoneListener);
        return addressDetailDialog;
    }

    public void init(OnPhoneNumDialogDoneListener phoneNumDialogDoneListener) {
        this.phoneNumDialogDoneListener = phoneNumDialogDoneListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_phone_number, container, false);
        phoneNumEdit = (EditText) rootView.findViewById(R.id.phoneNum);

        rootView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        phoneNumEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String phoneNum = phoneNumEdit.getText().toString();

                if (isPhoneNum(phoneNum)) {
                    phoneNumDialogDoneListener.onPhoneNumInputDone(phoneNum);
                    dismiss();
                    return false;
                } else {
                    phoneNumDialogDoneListener.onPhoneNumInputDone("");
                    dismiss();
                }
                return false;
            }
        });
        return rootView;
    }

    private boolean isPhoneNum(String phoneNum) {
        if(!phoneNum.startsWith("01")) return false;
        if(phoneNum.length() < 9) return false;

        return true;
    }
}

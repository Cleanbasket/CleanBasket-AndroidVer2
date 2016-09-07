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
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnAddressDialogDoneListner;


/**
 * Created by JaeBong on 2016-07-27.
 */
public class AddressDetailDialog extends DialogFragment {
    private TextView addressText;
    private EditText addressDetailEdit;
    private String address;
    private OnAddressDialogDoneListner addressDialogDoneListner;


    public static AddressDetailDialog newInstance(OnAddressDialogDoneListner onAddressDialogDoneListner, String address) {
        AddressDetailDialog addressDetailDialog = new AddressDetailDialog();
        addressDetailDialog.init(onAddressDialogDoneListner, address);
        return addressDetailDialog;
    }

    public void init(OnAddressDialogDoneListner addressDialogDoneListner, String address) {
        this.addressDialogDoneListner = addressDialogDoneListner;
        this.address = address;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_address_detail, container, false);
        addressText = (TextView) rootView.findViewById(R.id.address);
        addressDetailEdit = (EditText) rootView.findViewById(R.id.address_detail);
        rootView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addressDetailEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String address = addressDetailEdit.getText().toString();
                if (!address.equals("")) {
                    addressDialogDoneListner.onAddressInputDone(address);
                    dismiss();
                    return false;
                }
                return false;
            }
        });

        addressText.setText(address);

        return rootView;
    }
}

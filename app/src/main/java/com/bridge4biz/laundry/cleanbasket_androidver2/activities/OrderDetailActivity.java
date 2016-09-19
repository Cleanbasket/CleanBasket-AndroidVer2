package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.CleanBasketApplication;
import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.dialog.PaymentSelectDialog;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnCardSelectedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.MyInfoManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.OrderManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.MoneyStringUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailActivity extends AppCompatActivity {

    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.dropOffTime)
    TextView dropOffTime;
    @BindView(R.id.pickUpTime)
    TextView pickUpTime;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.mileage)
    EditText mileageEdit;

    OrderInfo order;

    private CleanBasketApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ButterKnife.bind(this);

        application = ((CleanBasketApplication) getApplication());

        order = application.getNewOrder();

        address.setText(order.getFullAddress());
        pickUpTime.setText(order.getPrettyPickUpDate());
        dropOffTime.setText(order.getPrettyDropOffDate());
        phone.setText(order.phone);
        getMileage();

    }

    private int mileage = 0;

    private void getMileage() {
        MyInfoManager myInfoManager = new MyInfoManager();
        myInfoManager.getMileage(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == Constants.SUCCESS) {
                    mileage = Integer.parseInt(jsonData.data);
                    mileageEdit.setHint("사용 가능 마일리지 총 " + MoneyStringUtil.makeMoneyString(mileage) + "점");
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.orderBar)
    void onOrderBarClicked() {
        sendOrderAfterCheck((Order) order);
    }

    private void sendOrderAfterCheck(Order order) {
        if (checkMileage(order) && checkPrice(order)) {
            sendOrder();
        }
    }

    private boolean checkMileage(Order order) {
        String userMileageString = mileageEdit.getText().toString();
        if (userMileageString.equals("") || userMileageString.isEmpty() || userMileageString == null)
            return true;

        int userMileage = Integer.parseInt(userMileageString);

        if (userMileage > mileage) {
            Toast.makeText(this, "총 사용 가능 마일리지는 " + MoneyStringUtil.makeMoneyString(mileage) + "점 입니다,", Toast.LENGTH_LONG).show();
            return false;
        } else {
            order.mileage = userMileage;
            return true;
        }
    }

    private boolean checkPrice(Order order) {
        //현장수거에 대한 예외처리
        if (order.getItem().get(0).item_code == 10) {
            return true;
        }

        if (order.price < 10000) {
            Toast.makeText(this, "10,000원 이상만 주문 가능합니다.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void sendOrder() {
        OrderManager orderManager = new OrderManager();
        orderManager.addOrder((Order) order, new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == Constants.SUCCESS) {
                    application.setNewOrder(new Order());
                    Intent intent = new Intent(OrderDetailActivity.this, OrderCompleteActivity.class);
                    startActivity(intent);
                }
                setResult(2);
                finish();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }
    @BindView(R.id.paymentMethod)
    TextView paymentMethodText;

    @OnClick(R.id.paymentBar)
    public void showPaymentDialog() {
        OnCardSelectedListener onCardSelectedListener = new OnCardSelectedListener() {
            @Override
            public void onCardSelected() {
                paymentMethodText.setText(application.getNewOrder().getPriceStatus());
            }
        };

        PaymentSelectDialog paymentSelectDialog = PaymentSelectDialog.newInstance(onCardSelectedListener);
        paymentSelectDialog.show(getFragmentManager(), "결제 수단");
    }


}

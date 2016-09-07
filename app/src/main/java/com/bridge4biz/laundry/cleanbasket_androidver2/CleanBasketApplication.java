package com.bridge4biz.laundry.cleanbasket_androidver2;

import android.app.Application;

import com.bridge4biz.laundry.cleanbasket_androidver2.utils.SharedPreferenceBase;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.HappyHour;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.OrderInfo;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.TimeDiscount;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;


/**
 * Created by gingeraebi on 2016. 6. 17..
 */
public class CleanBasketApplication extends Application {
    //HappyHour에 대한 관리를 어디서 하는게 맞을것인가?
    public HashMap<Integer, Integer> discountHours = new HashMap();

    public Order newOrder;
    public OrderInfo detailOrder;

    @Override
    public void onCreate() {
        super.onCreate();
        //fabricSetting();
        newOrder = new Order();
        addHours();
        SharedPreferenceBase.init(this);
//        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public OrderInfo getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(OrderInfo detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Order getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Order order) {
        this.newOrder = order;
    }

    private void addHours() {
        for (int i = 10; i < 24; i++) {
            discountHours.put(i, 0);
        }
    }

    public void setHappyHours(HappyHour happyHour) {
        for (TimeDiscount timeDiscount : happyHour.getHappyHours()) {
            discountHours.remove(timeDiscount.getTime());
            discountHours.put(timeDiscount.getTime(), timeDiscount.getDiscount());
        }
        ;
    }

    public ArrayList<TimeDiscount> getHappyHours() {
        ArrayList<TimeDiscount> happyHours = new ArrayList<>();
        for (int key : discountHours.keySet()) {
            if (discountHours.get(key) > 0) {
                happyHours.add(new TimeDiscount(key, discountHours.get(key)));
            }
        }
        Collections.sort(happyHours, new Comparator<TimeDiscount>() {
            @Override
            public int compare(TimeDiscount first, TimeDiscount second) {
                return first.getTime() < second.getTime() ? -1 : first.getTime() > second.getTime() ? 1 : 0;
            }
        });
        return happyHours;
    }

    public ArrayList<Integer> getCommonHours() {
        ArrayList<Integer> commonHours = new ArrayList<>();
        for (int key : discountHours.keySet()) {
            if (discountHours.get(key) == 0) {
                commonHours.add(key);
            }
        }
        Collections.sort(commonHours);
        return commonHours;
    }

    //For Crash Check
    private void fabricSetting() {
        Fabric.with(this, new Crashlytics());
    }


}

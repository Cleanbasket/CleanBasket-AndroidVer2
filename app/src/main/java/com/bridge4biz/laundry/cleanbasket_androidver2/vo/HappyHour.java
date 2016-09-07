package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

import java.util.ArrayList;

/**
 * Created by gingeraebi on 2016. 7. 4..
 */
public class HappyHour {
    public ArrayList<TimeDiscount> happyHours;

    public HappyHour() {
    }

    public HappyHour(ArrayList<TimeDiscount> happyHours) {
        this.happyHours = happyHours;
    }

    public ArrayList<TimeDiscount> getHappyHours() {
        return happyHours;
    }
}

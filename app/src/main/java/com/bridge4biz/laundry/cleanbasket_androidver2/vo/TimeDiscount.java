package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

/**
 * Created by gingeraebi on 2016. 6. 30..
 */
public class TimeDiscount {
    private int time;
    private int discount;
    private int backgorundColor;

    public TimeDiscount(int time, int discount) {
        this.time = time;
        this.discount = discount;
    }

    public TimeDiscount(int time, int discount, int backgorundColor) {
        this.time = time;
        this.discount = discount;
        this.backgorundColor = backgorundColor;

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getBackgorundColor() {
        return backgorundColor;
    }

    public void setBackgorundColor(int backgorundColor) {
        this.backgorundColor = backgorundColor;
    }


}

package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gingeraebi on 2016. 7. 28..
 */
public class ItemCode {
    @SerializedName("item_code")
    public int itemCode;
    public String name;
    public int category;
    public String descr;
    public int price;
    public int scope;
    public String img;

    public ItemCode(int itemCode, String name) {
        this.itemCode = itemCode;
        this.name = name;
    }

    public ItemCode(int itemCode, String name, int category, String descr, int price, int scope, String img) {
        this.itemCode = itemCode;
        this.name = name;
        this.category = category;
        this.descr = descr;
        this.price = price;
        this.scope = scope;
        this.img = img;
    }

    public int getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }

    public String getDescr() {
        return descr;
    }

    public int getPrice() {
        return price;
    }

    public int getScope() {
        return scope;
    }

    public String getImg() {
        return img;
    }
}

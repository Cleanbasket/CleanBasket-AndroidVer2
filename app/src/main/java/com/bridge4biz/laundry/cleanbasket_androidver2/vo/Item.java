package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

/**
 * Item.java
 * CleanBasket Deliverer Android
 * <p>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class Item {
    public Integer itid = null;
    public Integer oid;
    public Integer item_code;
    public String name = null;
    public String descr = null;
    public Integer price = null;
    public Integer count;
    public String img = null;
    public String rdate = null;

    public Item(Integer item_code, Integer count) {
        this.item_code = item_code;
        this.count = count;
    }

    public Item(int oid, int item_code, int count) {
        this.oid = oid;
        this.item_code = item_code;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itid=" + itid +
                ", oid=" + oid +
                ", item_code=" + item_code +
                ", name='" + name + '\'' +
                ", descr='" + descr + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", img='" + img + '\'' +
                ", rdate='" + rdate + '\'' +
                '}';
    }
}

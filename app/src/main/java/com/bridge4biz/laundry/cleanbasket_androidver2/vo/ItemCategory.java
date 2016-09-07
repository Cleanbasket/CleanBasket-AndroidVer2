package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

/**
 * Created by gingeraebi on 2016. 7. 28..
 */
public class ItemCategory {
    public int id;
    public String name;
    public String img;

    public ItemCategory(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }
}

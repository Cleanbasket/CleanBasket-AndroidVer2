package com.bridge4biz.laundry.cleanbasket_androidver2.event;

import java.util.HashMap;

/**
 * Created by gingeraebi on 2016. 7. 5..
 */
public class ItemCountMapEvent {
    private HashMap<Integer, Integer> itemCountMap = new HashMap<>();

    public ItemCountMapEvent(HashMap<Integer, Integer> itemCountMap) {
        this.itemCountMap = itemCountMap;
    }

    public HashMap<Integer, Integer>  getItemCountMap() {
        return itemCountMap;
    }

}

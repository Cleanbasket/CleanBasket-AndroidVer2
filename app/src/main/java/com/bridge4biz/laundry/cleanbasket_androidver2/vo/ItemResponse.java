package com.bridge4biz.laundry.cleanbasket_androidver2.vo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gingeraebi on 2016. 7. 28..
 */
public class ItemResponse {
    ArrayList<ItemCategory> categories;
    ArrayList<ItemCode> orderItems;



    public ItemResponse(ArrayList<ItemCategory> categories, ArrayList<ItemCode> orderItems) {
        this.categories = categories;
        this.orderItems = orderItems;
    }

    public ArrayList<ItemCategory> getCategories() {
        return categories;
    }

    public ArrayList<ItemCode> getOrderItems() {
        return orderItems;
    }

    public ArrayList<ItemCode> getItemsByCategory(int categoryNum) {
        ArrayList<ItemCode> itemCodes = new ArrayList<>();
        for (ItemCode itemCode : orderItems) {
            if (itemCode.category == categoryNum) {
                itemCodes.add(itemCode);
            }
        }
        return itemCodes;
    }

    public String getItemCategoryName(int categoryNum) {
        for (ItemCategory category : categories) {
            if (category.getId() == categoryNum) {
                return category.getName();
            }
        }
        return null;
    }

    public HashMap<Integer, Integer> getItemPriceMap() {
        HashMap<Integer, Integer> itemPriceMap = new HashMap<>();
        for (ItemCode itemCode : orderItems) {
            itemPriceMap.put(itemCode.itemCode, itemCode.price);
        }

        return itemPriceMap;
    }


}

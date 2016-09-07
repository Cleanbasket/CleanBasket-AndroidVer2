package com.bridge4biz.laundry.cleanbasket_androidver2.listeners;


import com.bridge4biz.laundry.cleanbasket_androidver2.vo.ItemCode;

/**
 * Created by gingeraebi on 2016. 8. 2..
 */
public interface OnItemListClickedListener {
    public void onIncreaseClicked(ItemCode itemCode);
    public void onDecreaseClicked(ItemCode itemCode);
}

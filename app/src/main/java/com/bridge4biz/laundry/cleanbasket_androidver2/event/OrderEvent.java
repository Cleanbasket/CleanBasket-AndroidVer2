package com.bridge4biz.laundry.cleanbasket_androidver2.event;


import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Order;

/**
 * Created by gingeraebi on 2016. 7. 5..
 */
public class OrderEvent {
    private Order order;

    public OrderEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

}

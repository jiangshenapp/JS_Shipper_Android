package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class OrderList {
    private int state;//状态 0全部 1发布中，2待接单，3待确认，4待支付，5代配送, 6待送达，7待收货，8已取消，9已完成

    public OrderList(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

package com.js.driver.model.request;

/**
 * Created by huyg on 2019-05-30.
 */
public class OrderStatus {

    /**
     * state : "6,7"
     */

    private String stateList;

    public OrderStatus(String stateList) {
        this.stateList = stateList;
    }

    public String getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }
}

package com.js.shipper.model.bean;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/17
 * desc   :
 * version: 3.0.0
 */
public class FeeBean {

    /**
     * totalFee : 25
     * calculateNo : 12
     */

    private double totalFee;
    private String calculateNo;

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getCalculateNo() {
        return calculateNo;
    }

    public void setCalculateNo(String calculateNo) {
        this.calculateNo = calculateNo;
    }
}

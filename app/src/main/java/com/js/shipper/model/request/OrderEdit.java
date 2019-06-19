package com.js.shipper.model.request;

/**
 * Created by huyg on 2019-06-17.
 */
public class OrderEdit {


    /**
     * fee : 0
     * feeType : 0
     * payType : 0
     * payWay : 0
     * receiveAddress : string
     * receiveMobile : string
     * receiveName : string
     */

    private double fee;
    private int feeType;
    private int payType;
    private int payWay;
    private String receiveAddress;
    private String receiveMobile;
    private String receiveName;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
}

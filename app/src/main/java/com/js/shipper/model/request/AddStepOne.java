package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class AddStepOne {

    /**
     * carLength : string
     * carModel : string
     * receiveAddress : string
     * receiveAddressCode : string
     * receiveMobile : string
     * receivePosition : string
     * sendAddress : string
     * sendAddressCode : string
     * sendMobile : string
     * sendName : string
     * sendPosition : string
     */

    private String carLength;//车长，多选，逗号分隔
    private String carModel;//车型，字典
    private String receiveAddress;//收货地地址
    private String receiveAddressCode;//收货地区域代码
    private String receiveMobile;//收货人手机号
    private String receivePosition;//收货地坐标
    private String sendAddress;//发货地地址
    private String sendAddressCode;//发货地区域代码
    private String sendMobile;//发货人手机号
    private String sendName;//发货人姓名
    private String sendPosition;//发货地坐标

    public AddStepOne(String carLength, String carModel, String receiveAddress, String receiveAddressCode, String receiveMobile, String receivePosition, String sendAddress, String sendAddressCode, String sendMobile, String sendName, String sendPosition) {
        this.carLength = carLength;
        this.carModel = carModel;
        this.receiveAddress = receiveAddress;
        this.receiveAddressCode = receiveAddressCode;
        this.receiveMobile = receiveMobile;
        this.receivePosition = receivePosition;
        this.sendAddress = sendAddress;
        this.sendAddressCode = sendAddressCode;
        this.sendMobile = sendMobile;
        this.sendName = sendName;
        this.sendPosition = sendPosition;
    }



    public String getCarLength() {
        return carLength;
    }

    public void setCarLength(String carLength) {
        this.carLength = carLength;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveAddressCode() {
        return receiveAddressCode;
    }

    public void setReceiveAddressCode(String receiveAddressCode) {
        this.receiveAddressCode = receiveAddressCode;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceivePosition() {
        return receivePosition;
    }

    public void setReceivePosition(String receivePosition) {
        this.receivePosition = receivePosition;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendAddressCode() {
        return sendAddressCode;
    }

    public void setSendAddressCode(String sendAddressCode) {
        this.sendAddressCode = sendAddressCode;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPosition() {
        return sendPosition;
    }

    public void setSendPosition(String sendPosition) {
        this.sendPosition = sendPosition;
    }
}

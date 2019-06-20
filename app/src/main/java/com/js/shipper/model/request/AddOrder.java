package com.js.shipper.model.request;

/**
 * Created by huyg on 2019-06-18.
 */
public class AddOrder {


    /**
     * carLength : string
     * carModel : string
     * fee : 0
     * feeType : 0
     * goodsType : string
     * goodsVolume : 0
     * goodsWeight : 0
     * image1 : string
     * image2 : string
     * loadingTime : 2019-06-18T07:52:39.799Z
     * matchId : 0
     * payType : 0
     * payWay : 0
     * receiveAddress : string
     * receiveAddressCode : string
     * receiveMobile : string
     * receiveName : string
     * receivePosition : string
     * remark : string
     * sendAddress : string
     * sendAddressCode : string
     * sendMobile : string
     * sendName : string
     * sendPosition : string
     * useCarType : string
     */

    private String carLength;
    private String carModel;
    private double fee;
    private int feeType;
    private String goodsType;
    private int goodsVolume;
    private int goodsWeight;
    private String image1;
    private String image2;
    private String loadingTime;
    private String matchId;
    private int payType;
    private int payWay;
    private String receiveAddress;
    private String receiveAddressCode;
    private String receiveMobile;
    private String receiveName;
    private String receivePosition;
    private String remark;
    private String sendAddress;
    private String sendAddressCode;
    private String sendMobile;
    private String sendName;
    private String sendPosition;
    private String useCarType;

    public void setCarLength(String carLength) {
        this.carLength = carLength;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsVolume(int goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public void setReceiveAddressCode(String receiveAddressCode) {
        this.receiveAddressCode = receiveAddressCode;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setReceivePosition(String receivePosition) {
        this.receivePosition = receivePosition;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public void setSendAddressCode(String sendAddressCode) {
        this.sendAddressCode = sendAddressCode;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public void setSendPosition(String sendPosition) {
        this.sendPosition = sendPosition;
    }

    public void setUseCarType(String useCarType) {
        this.useCarType = useCarType;
    }
}

package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class AddStepTwo {

    /**
     * fee : 0
     * feeType : 0
     * goodsType : string
     * goodsVolume : 0
     * goodsWeight : 0
     * id : 0
     * image1 : string
     * image2 : string
     * loadingTime : 2019-05-18T14:20:27.120Z
     * payType : 0
     * payWay : 0
     * remark : string
     * useCarType : string
     */

    private long fee;//运费
    private int feeType;//运费类型，1自己出价，2电议
    private String goodsType;//货物类型,字典表，多个
    private int goodsVolume;//货物体积，单位立方米
    private int goodsWeight;//货物重量、吨
    private long id;//订单号
    private String image1;//图片1
    private String image2;//图片2
    private String loadingTime;//装货时间
    private int payType;//付款方式，1到付，2现付
    private int payWay;//支付方式，1线上支付，2线下支付
    private String remark;//备注
    private String useCarType;//用车类型，字典

    public AddStepTwo(){

    }

    public AddStepTwo(int fee, int feeType, String goodsType, int goodsVolume, int goodsWeight, int id, String image1, String image2, String loadingTime, int payType, int payWay, String remark, String useCarType) {
        this.fee = fee;
        this.feeType = feeType;
        this.goodsType = goodsType;
        this.goodsVolume = goodsVolume;
        this.goodsWeight = goodsWeight;
        this.id = id;
        this.image1 = image1;
        this.image2 = image2;
        this.loadingTime = loadingTime;
        this.payType = payType;
        this.payWay = payWay;
        this.remark = remark;
        this.useCarType = useCarType;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(int goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public int getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUseCarType() {
        return useCarType;
    }

    public void setUseCarType(String useCarType) {
        this.useCarType = useCarType;
    }
}

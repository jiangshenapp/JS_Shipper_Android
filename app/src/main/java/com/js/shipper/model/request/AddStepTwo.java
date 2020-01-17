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

    private double fee;//运费
    private int feeType;//运费类型，1自己出价，2电议
    private double goodsVolume;//货物体积，单位立方米
    private double goodsWeight;//货物重量、吨
    private long id;//订单号
    private String image1;//图片1
    private String image2;//图片2
    private String loadingTime;//装货时间
    private int payType;//付款方式，1到付，2现付
    private int payWay;//支付方式，1线上支付，2线下支付
    private String remark;//备注
    private String useCarType;//用车类型，字典
    private double deposit;//保证金
    private boolean requireDeposit;//是否开启保证金
    private String goodsName;//货物名称
    private String packType;//包装类型
    private String calculateNo;//专线费用No

    public AddStepTwo(){

    }

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


    public double getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(double goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
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

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public void setRequireDeposit(boolean requireDeposit) {
        this.requireDeposit = requireDeposit;
    }

    public double getDeposit() {
        return deposit;
    }

    public boolean getRequireDeposit() {
        return requireDeposit;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getCalculateNo() {
        return calculateNo;
    }

    public void setCalculateNo(String calculateNo) {
        this.calculateNo = calculateNo;
    }
}

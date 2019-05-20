package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/5/18.
 * 路线
 */
public class LineBean {


    /**
     * arriveAddressCode : string
     * carLength : string
     * carModel : string
     * classic : 0
     * cphm : string
     * driverName : string
     * driverPhone : string
     * id : 0
     * remark : string
     * startAddressCode : string
     * state : 0
     * subscriberId : 0
     */

    private String arriveAddressCode;//目的地编码
    private String carLength;//车长,多选，逗号分隔
    private String carModel;//车型,多选，逗号分隔
    private int classic;//精品线路
    private String cphm;//车牌
    private String driverName;//司机名
    private String driverPhone;//司机手机号
    private int id;//主键
    private String remark;//简介
    private String startAddressCode;//出发地编码
    private int state;//状态，0未启用，1启用
    private int subscriberId;//会员id

    public String getArriveAddressCode() {
        return arriveAddressCode;
    }

    public void setArriveAddressCode(String arriveAddressCode) {
        this.arriveAddressCode = arriveAddressCode;
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

    public int getClassic() {
        return classic;
    }

    public void setClassic(int classic) {
        this.classic = classic;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartAddressCode() {
        return startAddressCode;
    }

    public void setStartAddressCode(String startAddressCode) {
        this.startAddressCode = startAddressCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }
}

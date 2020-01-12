package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarBean {

    /**
     * carId : 0
     * carLengthId : string
     * carLengthName : string
     * carModelId : string
     * carModelName : string
     * cooperated : 0
     * cphm : string
     * id : 0
     * mobile : string
     * nickName : string
     * remark : string
     * subscriberId : 0
     * type : 0
     */

    private int added; //是否添加，0未添加，非0已添加
    private int carId; //车辆id
    private String carLengthId; //车长id
    private String carLengthName; //车长中文
    private String carModelId; //车型id
    private String carModelName; //车型中文
    private int cooperated; //合作次数
    private String cphm; //车牌号
    private int id; //ID
    private String mobile; //手机号
    private String nickName; //昵称
    private String remark; //描述
    private int subscriberId; //货主会员id
    private int type; //类型，1自有车辆，2外调车辆

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarLengthId() {
        return carLengthId;
    }

    public void setCarLengthId(String carLengthId) {
        this.carLengthId = carLengthId;
    }

    public String getCarLengthName() {
        return carLengthName;
    }

    public void setCarLengthName(String carLengthName) {
        this.carLengthName = carLengthName;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public int getCooperated() {
        return cooperated;
    }

    public void setCooperated(int cooperated) {
        this.cooperated = cooperated;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

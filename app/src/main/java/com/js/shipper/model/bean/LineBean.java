package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/5/18.
 * 路线
 */
public class LineBean {


    /**
     * id : 4
     * startAddressCode : 130000
     * arriveAddressCode : 190000
     * carLength : 111
     * carModel : 111
     * remark : null
     * state : 1
     * classic : 0
     * subscriberId : null
     * driverName : null
     * driverPhone : null
     * cphm : null
     * carLengthName :
     * carModelName :
     * arriveAddressCodeName :
     * startAddressCodeName : 河北省
     * classicName : 普通
     */

    private int id;
    private String startAddressCode;
    private String arriveAddressCode;
    private String carLength;
    private String carModel;
    private String remark;
    private int state;
    private int classic;
    private long subscriberId;
    private String driverName;
    private String driverPhone;
    private String cphm;
    private String carLengthName;
    private String carModelName;
    private String arriveAddressCodeName;
    private String startAddressCodeName;
    private String classicName;
    private boolean isCollect;
    private String receiveAddressCodeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartAddressCode() {
        return startAddressCode;
    }

    public void setStartAddressCode(String startAddressCode) {
        this.startAddressCode = startAddressCode;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getClassic() {
        return classic;
    }

    public void setClassic(int classic) {
        this.classic = classic;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
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

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getCarLengthName() {
        return carLengthName;
    }

    public void setCarLengthName(String carLengthName) {
        this.carLengthName = carLengthName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getArriveAddressCodeName() {
        return arriveAddressCodeName;
    }

    public void setArriveAddressCodeName(String arriveAddressCodeName) {
        this.arriveAddressCodeName = arriveAddressCodeName;
    }

    public String getStartAddressCodeName() {
        return startAddressCodeName;
    }

    public void setStartAddressCodeName(String startAddressCodeName) {
        this.startAddressCodeName = startAddressCodeName;
    }

    public String getClassicName() {
        return classicName;
    }

    public void setClassicName(String classicName) {
        this.classicName = classicName;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getReceiveAddressCodeName() {
        return receiveAddressCodeName;
    }

    public void setReceiveAddressCodeName(String receiveAddressCodeName) {
        this.receiveAddressCodeName = receiveAddressCodeName;
    }
}

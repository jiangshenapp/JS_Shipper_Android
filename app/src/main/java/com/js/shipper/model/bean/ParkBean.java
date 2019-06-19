package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/5/18.
 * 园区
 */
public class ParkBean {


    /**
     * id : 1
     * companyName : 1
     * companyType : 1
     * registrationNumber : 1
     * address : 北京市北京市东城区
     * detailAddress : 1
     * businessLicenceImage : 7a15c2177b6e44df8dfe67f854159aef.png
     * auditId : 37
     * remark : null
     * contactName : null
     * contactLocation : null
     * contactAddress : null
     * contractPhone : null
     * image1 : null
     * image2 : null
     * image3 : null
     * image4 : null
     * state : 1
     * subscriberId : 3
     * ts : 2019-05-15 14:31:31
     * valid : 1
     */

    private int id;
    private int parkId;
    private String companyName;
    private int companyType;
    private String registrationNumber;
    private String address;
    private String detailAddress;
    private String businessLicenceImage;
    private int auditId;
    private String remark;
    private String contactName;
    private String contactLocation;
    private String contactAddress;
    private String contractPhone;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private int state;
    private int subscriberId;
    private String ts;
    private int valid;
    private boolean isCollect;
    private boolean isRemark;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getBusinessLicenceImage() {
        return businessLicenceImage;
    }

    public void setBusinessLicenceImage(String businessLicenceImage) {
        this.businessLicenceImage = businessLicenceImage;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactLocation() {
        return contactLocation;
    }

    public void setContactLocation(String contactLocation) {
        this.contactLocation = contactLocation;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
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

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
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

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public boolean isRemark() {
        return isRemark;
    }

    public void setRemark(boolean remark) {
        isRemark = remark;
    }
}

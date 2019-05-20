package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/5/18.
 * 园区
 */
public class ParkBean {


    /**
     * address : string
     * auditId : 0
     * businessLicenceImage : string
     * companyName : string
     * companyType : 0
     * contactAddress : string
     * contactLocation : string
     * contactName : string
     * contractPhone : string
     * detailAddress : string
     * id : 0
     * image1 : string
     * image2 : string
     * image3 : string
     * image4 : string
     * registrationNumber : string
     * remark : string
     * state : 0
     * subscriberId : 0
     * ts : 2019-05-18T14:25:05.320Z
     * valid : 0
     */

    private String address;//所在地
    private int auditId;//审核流id
    private String businessLicenceImage;//营业执照图片
    private String companyName;//机构名称
    private int companyType;//机构类型
    private String contactAddress;//联系地址
    private String contactLocation;//园区经纬度
    private String contactName;//联系人
    private String contractPhone;//联系人电话
    private String detailAddress;//详细地址
    private int id;//主键
    private String image1;//图片1
    private String image2;//图片2
    private String image3;//图片3
    private String image4;//图片4
    private String registrationNumber;//统一信用社代码
    private String remark;//描述
    private int state;//状态，1启用，0未启用
    private int subscriberId;//会员id
    private String ts;//编辑时间
    private int valid;//是否有效，1是，0否

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getBusinessLicenceImage() {
        return businessLicenceImage;
    }

    public void setBusinessLicenceImage(String businessLicenceImage) {
        this.businessLicenceImage = businessLicenceImage;
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

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactLocation() {
        return contactLocation;
    }

    public void setContactLocation(String contactLocation) {
        this.contactLocation = contactLocation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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
}

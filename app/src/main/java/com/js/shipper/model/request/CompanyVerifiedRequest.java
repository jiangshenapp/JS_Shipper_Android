package com.js.shipper.model.request;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/26
 * desc   :
 * version: 3.0.0
 */
public class CompanyVerifiedRequest {

    private String companyName;
    private String registrationNumber;
    private String address;
    private String detailAddress;
    private String businessLicenceImage;

    public CompanyVerifiedRequest(String companyName, String registrationNumber, String address, String detailAddress, String businessLicenceImage) {
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.businessLicenceImage = businessLicenceImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}

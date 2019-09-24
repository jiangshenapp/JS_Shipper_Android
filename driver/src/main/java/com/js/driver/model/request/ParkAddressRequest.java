package com.js.driver.model.request;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/24
 * desc   :
 * version: 3.0.0
 */
public class ParkAddressRequest {

    private String contactName;
    private String contractPhone;
    private String contactLocation;
    private String contactAddress;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public ParkAddressRequest(String contactName, String contractPhone, String contactLocation, String contactAddress,
                              String image1, String image2, String image3, String image4) {
        this.contactName = contactName;
        this.contractPhone = contractPhone;
        this.contactLocation = contactLocation;
        this.contactAddress = contactAddress;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
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
}

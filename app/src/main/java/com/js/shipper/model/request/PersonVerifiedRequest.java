package com.js.shipper.model.request;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/26
 * desc   :
 * version: 3.0.0
 */
public class PersonVerifiedRequest {

    private String idImage;
    private String idBackImage;
    private String idHandImage;
    private String personName;
    private String idCode;
    private String address;

    public PersonVerifiedRequest(String idImage, String idBackImage, String idHandImage, String personName, String idCode, String address) {
        this.idImage = idImage;
        this.idBackImage = idBackImage;
        this.idHandImage = idHandImage;
        this.personName = personName;
        this.idCode = idCode;
        this.address = address;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getIdBackImage() {
        return idBackImage;
    }

    public void setIdBackImage(String idBackImage) {
        this.idBackImage = idBackImage;
    }

    public String getIdHandImage() {
        return idHandImage;
    }

    public void setIdHandImage(String idHandImage) {
        this.idHandImage = idHandImage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

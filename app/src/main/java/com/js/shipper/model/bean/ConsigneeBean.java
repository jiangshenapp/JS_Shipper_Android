package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019-05-21.
 */
public class ConsigneeBean {

    /**
     * address : string
     * addressCode : string
     * id : 0
     * isDefault : 0
     * mobile : string
     * name : string
     * position : string
     * subscriberId : 0
     * tags : string
     */

    private String address;
    private String addressCode;
    private int id;
    private int isDefault;
    private String mobile;
    private String name;
    private String position;
    private int subscriberId;
    private String tags;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

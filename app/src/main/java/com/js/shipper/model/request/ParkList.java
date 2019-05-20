package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class ParkList {

    /**
     * addressCode : string
     */

    private String addressCode;//所在地

    public ParkList(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
}

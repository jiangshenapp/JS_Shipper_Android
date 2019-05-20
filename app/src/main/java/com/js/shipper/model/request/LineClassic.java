package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class LineClassic {

    /**
     * arriveAddressCode : string
     * startAddressCode : string
     */

    private String arriveAddressCode;//目的地
    private String startAddressCode;//出发地

    public LineClassic(String arriveAddressCode, String startAddressCode) {
        this.arriveAddressCode = arriveAddressCode;
        this.startAddressCode = startAddressCode;
    }

    public String getArriveAddressCode() {
        return arriveAddressCode;
    }

    public void setArriveAddressCode(String arriveAddressCode) {
        this.arriveAddressCode = arriveAddressCode;
    }

    public String getStartAddressCode() {
        return startAddressCode;
    }

    public void setStartAddressCode(String startAddressCode) {
        this.startAddressCode = startAddressCode;
    }
}

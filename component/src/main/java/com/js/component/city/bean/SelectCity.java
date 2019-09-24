package com.js.component.city.bean;

/**
 * Created by huyg on 2019-06-06.
 */
public class SelectCity {


    /**
     * code : 110100
     * address : 北京市
     * parentCode : 110000
     * areaIndex : 34
     * lng : 116.405285
     * lat : 39.904989
     */

    private String code;
    private String address;
    private String parentCode;
    private int areaIndex;
    private String lng;
    private String lat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getAreaIndex() {
        return areaIndex;
    }

    public void setAreaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}

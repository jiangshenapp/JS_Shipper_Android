package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/5/18.
 */
public class ParkList {


    /**
     * addressCode : string
     * companyType : 0
     * sort : 0
     */

    private String addressCode;
    private String companyType;
    private int sort;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}

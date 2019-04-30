package com.js.shipper.model.request;

/**
 * Created by huyg on 2019/4/28.
 */
public class DriverAuth {

    /**
     * address : string
     * auditId : 0
     * driverImage : string
     * driverLevel : string
     * id : 0
     * idBackImage : string
     * idCode : string
     * idHandImage : string
     * idImage : string
     * personName : string
     * subscriberId : 0
     * ts : 2019-04-28T05:11:50.276Z
     * valid : 0
     */

    private String address;
    private int auditId;
    private String driverImage;
    private String driverLevel;
    private int id;
    private String idBackImage;
    private String idCode;
    private String idHandImage;
    private String idImage;
    private String personName;
    private int subscriberId;
    private String ts;
    private int valid;

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

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public String getDriverLevel() {
        return driverLevel;
    }

    public void setDriverLevel(String driverLevel) {
        this.driverLevel = driverLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdBackImage() {
        return idBackImage;
    }

    public void setIdBackImage(String idBackImage) {
        this.idBackImage = idBackImage;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getIdHandImage() {
        return idHandImage;
    }

    public void setIdHandImage(String idHandImage) {
        this.idHandImage = idHandImage;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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

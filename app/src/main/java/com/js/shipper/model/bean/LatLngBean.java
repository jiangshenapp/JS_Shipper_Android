package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019-06-08.
 */
public class LatLngBean {
    private double latitude;
    private double longitude;


    public LatLngBean(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

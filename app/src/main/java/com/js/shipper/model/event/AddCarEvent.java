package com.js.shipper.model.event;

import retrofit2.http.Query;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/16
 * desc   :
 * version: 3.0.0
 */
public class AddCarEvent {

    public long carId;
    public String remark;
    public long type;

    public AddCarEvent(long carId, String remark, long type) {
        this.carId = carId;
        this.remark = remark;
        this.type = type;
    }
}

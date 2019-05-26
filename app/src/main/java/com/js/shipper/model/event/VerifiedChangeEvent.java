package com.js.shipper.model.event;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/26
 * desc   :
 * version: 3.0.0
 */
public class VerifiedChangeEvent {

    // 0：个人认证 1：公司认证
    public static final int VERIFIED_PERSON = 0;
    public static final int VERIFIED_COMPANY = 1;

    public int index;

    public VerifiedChangeEvent(int index) {
        this.index = index;
    }
}

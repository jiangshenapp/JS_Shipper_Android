package com.js.login.model.event;

/**
 * Created by huyg on 2018/12/17.
 */
public class WxCodeEvent {
    public String code;
    public String status;

    public WxCodeEvent(String code, String status) {
        this.code = code;
        this.status = status;
    }
}

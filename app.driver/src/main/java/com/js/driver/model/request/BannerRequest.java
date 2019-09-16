package com.js.driver.model.request;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/15
 * desc   :
 * version: 3.0.0
 */
public class BannerRequest {

    private long type; //类型 1服务页广告、2发货页广告、3启动页广告

    public BannerRequest(long type) {
        this.type = type;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}

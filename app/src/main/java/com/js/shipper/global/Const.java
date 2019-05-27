package com.js.shipper.global;

/**
 * Created by huyg on 2019/4/21.
 */
public class Const {

    public static final int AUTH_CARD = 1;
    public static final int AUTH_BODY = 2;
    public static final int AUTH_BEHIND_CARD = 3;
    public static final int AUTH_BUSINESS_LICENSE = 4;
    public static final int UPLOAD_HEADIMG = 5;

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NUM = 1;
    public static final int REFRESH = 0;
    public static final int MORE = 1;

    public static final String AuthStateStr[] = {"未提交", "认证中", "已认证", "认证失败"};
    public static final String AuthStateColor[] = {"#B4B4B4", "#0091FF", "#ECA73F", "#E02020"};
}

package com.base.http.global;


import android.text.TextUtils;
import android.widget.TextView;

import com.base.http.HttpApp;
import com.base.util.BuildConfig;
import com.base.util.manager.SpManager;

/**
 * Created by huyg on 2019/1/31.
 */
public class Const {

    public static String API_URL() {
        if (!BuildConfig.DEBUG) {
            return "https://gateway.jiangshen56.com/logistic-biz/";
        }
        String host = SpManager.getInstance(HttpApp.getInstance()).getSP("host");
        if (TextUtils.isEmpty(host)){
            host = "https://testway.jiangshen56.com/logistic-biz/";
        }
        return host;
    }


    public static String IMG_URL(){
        if (!BuildConfig.DEBUG) {
            return "https://gateway.jiangshen56.com/admin/file/download?fileName=";
        }
        if (API_URL().contains("gateway")){
            return "https://gateway.jiangshen56.com/admin/file/download?fileName=";
        } else {
            return "http://testway.jiangshen56.com/admin/file/download?fileName=";
        }
    }

    public static String UPLOAD_URL(){
        if (!BuildConfig.DEBUG) {
            return "https://gateway.jiangshen56.com/admin/file/upload";
        }
        if (API_URL().contains("gateway")){
            return "https://gateway.jiangshen56.com/admin/file/upload";
        } else {
            return "http://testway.jiangshen56.com/admin/file/upload";
        }
    }



}

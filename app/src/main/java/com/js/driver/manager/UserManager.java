package com.js.driver.manager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.js.driver.App;
import com.js.driver.ui.user.activity.LoginActivity;

import androidx.appcompat.app.AlertDialog;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/04/23
 * desc   : 用户相关的公共处理方法
 * version: 3.0.0
 */
public class UserManager {

    private static UserManager instance;

    private UserManager() {}

    /**
     * 单一实例
     */
    public static UserManager getUserManager() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * 判断登录状态
     */
    public boolean isLogin(boolean isJump) {
        if (TextUtils.isEmpty(App.getInstance().token)) {
            if (isJump) {
                LoginActivity.action(App.getInstance());
            }
            return false;
        }
        return true;
    }

    /**
     * 判断认证状态
     */
    public boolean isVerified(Activity activity) {
        return true;
    }
}

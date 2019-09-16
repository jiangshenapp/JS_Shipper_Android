package com.js.driver.util;

import android.content.Context;

import com.js.driver.App;
import com.js.driver.ui.main.activity.MainActivity;

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
     * 退出登录
     */
    public void logout(Context context) {
        App.getInstance().clearUserInfo();
        //EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGOUT_SUCCESS));
        MainActivity.action(context);
    }

    /**
     * 判断认证状态
     */
    public boolean isVerified() {
        if (App.getInstance().driverVerified != 2
                && App.getInstance().parkVerified != 2 ) {
            return false;
        } else {
            return true;
        }
    }
}

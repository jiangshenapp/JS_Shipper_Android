package com.js.shipper.manager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.js.shipper.App;
import com.js.shipper.model.event.UserStatusChangeEvent;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.user.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

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
    public boolean isVerified(Activity activity) {
        return true;
    }
}

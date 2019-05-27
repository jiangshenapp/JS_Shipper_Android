package com.js.shipper.manager;

import android.app.Activity;
import android.text.TextUtils;

import com.js.shipper.App;
import com.js.shipper.model.event.UserStatusChangeEvent;
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
     * 判断是否登录
     * @param isJumpLogin 是否跳转登录页面
     * @param isBackHome 登录页面返回的时候是否返回到首页 还是上一个页面
     * @return
     */
    public boolean isLogin(boolean isJumpLogin, boolean isBackHome) {
        if (TextUtils.isEmpty(SpManager.getInstance(App.getInstance()).getSP("token"))) {
            if (isJumpLogin) {
                LoginActivity.action(App.getInstance(), isBackHome);
            }
            return false;
        }
        return true;
    }

    /**
     * 退出登录
     */
    public void logout() {
        App.getInstance().clearUserInfo();
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGOUT_SUCCESS));
        LoginActivity.action(App.getInstance(),true);
    }

    /**
     * 判断认证状态
     */
    public boolean isVerified(Activity activity) {
        return true;
    }
}

package com.plugin.im;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hyphenate.EMError;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;

/**
 * Created by huyg on 2019-09-23.
 */
public class IMHelper {
    private static final String TAG = "IMHelper";

    public static IMHelper instance = new IMHelper();

    /**
     * kefuChat.MessageListener
     */
    protected ChatManager.MessageListener messageListener = null;

    /**
     * ChatClient.ConnectionListener
     */
    private ChatClient.ConnectionListener connectionListener;

    private UIProvider _uiProvider;

    public boolean isVideoCalling;
    private Context appContext;

    private IMHelper() {
    }

    public synchronized static IMHelper getInstance() {
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(final Context context) {
        appContext = context;
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1114190326030612#android-driver");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("71051");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(context, options)) {
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(context);
    }


    public void register(final String userName, final String password) {
        ChatClient.getInstance().register("username", "password", new Callback() {
            @Override
            public void onSuccess() {
                login(userName, password);
            }

            @Override
            public void onError(int code, String error) {
                //ErrorCode:
                //Error.NETWORK_ERROR 网络不可用
                //Error.USER_ALREADY_EXIST  用户已存在
                //Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
                //Error.USER_ILLEGAL_ARGUMENT 用户名非法
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }


    public void login(final String username, final String password) {
        ChatClient.getInstance().login(username, password, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, username + "   onSuccess");
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, username + "onError  " + error);
                switch (code) {
                    case EMError.USER_NOT_FOUND:
                        register(username, password);
                        break;
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, username + "   onProgress   " + status);
            }
        });
    }

    public void loginWithToken(String username, String token) {
        ChatClient.getInstance().loginWithToken(username, token, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    public void logout() {
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    public void goIm(Context context) {

        if (ChatClient.getInstance().isLoggedInBefore()) {
            Intent intent = new IntentBuilder(context).setServiceIMNumber(
                    "kefuchannelimid_484880") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                    .build();
            context.startActivity(intent);
        } else {
            ARouter.getInstance().build("/user/login").navigation();
        }

    }


}

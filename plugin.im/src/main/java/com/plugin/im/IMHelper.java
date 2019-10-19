package com.plugin.im;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.global.Const;
import com.base.util.manager.CommonGlideImageLoader;
import com.base.util.manager.SpManager;
import com.bumptech.glide.Glide;
import com.hyphenate.EMError;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.Message;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.easeui.util.UserUtil;

import org.json.JSONObject;

import java.util.List;

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

        EMOptions emOptions = new EMOptions();
        EaseUI.getInstance().init(context, emOptions);
        //setEaseUIProvider(context);
    }


    private void setEaseUIProvider(Context context) {
        //设置头像和昵称 某些控件可能没有头像和昵称，需要注意
        UIProvider.getInstance().setUserProfileProvider(new UIProvider.UserProfileProvider() {
            @Override
            public void setNickAndAvatar(Context context, Message message, ImageView userAvatarView, TextView usernickView) {
                if (message.direct() == Message.Direct.RECEIVE) {
                    String nickName = message.getStringAttribute("nickName",null);
                    String avatar = message.getStringAttribute("avatar",null);

                    if (!TextUtils.isEmpty(avatar)) {
                        CommonGlideImageLoader.getInstance()
                                .displayNetImageWithCircle(context,avatar,userAvatarView,context.getResources().getDrawable(com.hyphenate.easeui.R.drawable.ease_default_avatar));
                    } else {
                        Glide.with(context).load(com.hyphenate.easeui.R.drawable.ease_default_avatar).into(userAvatarView);
                    }
                    usernickView.setText(message.getStringAttribute("nickName",nickName));
                    //设置接收方的昵称和头像
//                    UserUtil.setAgentNickAndAvatar(context, message, userAvatarView, usernickView);
                } else {
                    //此处设置当前登录用户的头像，
                    if (userAvatarView != null) {
                        CommonGlideImageLoader.getInstance()
                                .displayNetImageWithCircle(context, Const.IMG_URL+ SpManager.getInstance(context).getSP("avatar"),userAvatarView,context.getResources().getDrawable(com.hyphenate.easeui.R.drawable.hd_default_avatar));
//                        Glide.with(context).load("http://oev49clxj.bkt.clouddn.com/7a8aed7bjw1f32d0cumhkj20ey0mitbx.png").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.hd_default_avatar).into(userAvatarView);
//                        如果用圆角，可以采用此方案：http://blog.csdn.net/weidongjian/article/details/47144549
                    }
                }
            }
        });
    }

    public void register(final String userName, final String password) {
        ChatClient.getInstance().register(userName, password, new Callback() {
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

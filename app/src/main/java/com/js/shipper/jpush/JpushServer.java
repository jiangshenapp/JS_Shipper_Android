package com.js.shipper.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import cn.jpush.android.api.JPushInterface;

/**
 * Create by Weixf
 * Date on 2017/6/22
 * Description 极光推送的自定义接收器
 * * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */

public class JpushServer extends BroadcastReceiver {

    private static final String TAG = "JpushServer";
    private JpushMessage message;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    message = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), JpushMessage.class);
                    LogUtil.d("[MyReceiver] 推送内容：" + message);
                }
            }
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                new AlertDialog.Builder(context).setMessage("您有一条新的推送消息").setPositiveButton("取消", null).setNegativeButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = message.getType();
                        String value = message.getValue();
                        handlePushMsg(context,type,value); //处理推送消息
                    }
                }).show();

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) { //用户点击了通知
                String type = message.getType();
                String value = message.getValue();
                handlePushMsg(context,type,value); //处理推送消息
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }
    }

    // 处理推送消息
    private void handlePushMsg(Context context, String type, String value) {
//        if (type.equals("h5_myteacher")) {
//            if (TextUtils.isEmpty(App.getInstance().getToken())) {
//                Intent loginIntent = new Intent(context, LoginActivity.class);
//                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(loginIntent);
//            } else {
//                Intent webIntent = new Intent(context, WebDetailActivity.class);
//                webIntent.putExtra("url", value+"?token="+App.getInstance().getToken());
//                webIntent.putExtra("title", "我的班主任");
//                webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(webIntent);
//            }
//        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                        String s = json.optString(myKey);
                        JSONObject jsonObject = new JSONObject(s);
                        JpushMessage message = new Gson().fromJson(s, JpushMessage.class);
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}

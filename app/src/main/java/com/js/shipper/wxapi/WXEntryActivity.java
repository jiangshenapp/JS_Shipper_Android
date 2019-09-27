package com.js.shipper.wxapi;

import android.content.Intent;
import android.os.Bundle;


import com.base.frame.view.SimpleActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.model.event.WxCodeEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;


public class WXEntryActivity extends SimpleActivity implements IWXAPIEventHandler {


    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxcallback);
    }

    @Override
    public void setActionBar() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()){
                    case RETURN_MSG_TYPE_LOGIN:
                        String code = ((SendAuth.Resp) baseResp).code;
                        String state = ((SendAuth.Resp) baseResp).state;
                        switch (state){
                            case "wx_bind":
                                EventBus.getDefault().postSticky(new WxCodeEvent(code,"wx_bind"));
                                break;
                            case "wx_login":
                                EventBus.getDefault().postSticky(new WxCodeEvent(code,"wx_login"));
                                break;
                        }
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }


    //在onResume中处理从微信授权通过以后不会自动跳转的问题，手动结束该页面
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}

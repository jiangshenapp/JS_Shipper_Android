package com.js.login.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.js.login.LoginApp;
import com.js.login.R;
import com.js.login.R2;
import com.js.login.di.componet.DaggerActivityComponent;
import com.js.login.di.module.ActivityModule;
import com.js.login.model.bean.BindStatus;
import com.js.login.model.bean.WxLogin;
import com.js.login.model.event.WxCodeEvent;
import com.js.login.ui.presenter.BindStatusPresenter;
import com.js.login.ui.presenter.contract.BindStatusContract;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-10-06.
 */
public class BindStatusActivity extends BaseActivity<BindStatusPresenter> implements BindStatusContract.View {

    @BindView(R2.id.wx_bind_status)
    TextView mWxBind;


    @OnClick(R2.id.wechat_layout)
    public void onClick() {
        switch (wxStatus) {
            case 0://未绑定
                if (!LoginApp.getInstance().getApi().isWXAppInstalled()) {
                    toast("请先安装微信客户端。");
                    return;
                }
                showBindDialog();
                break;
            case 1://已绑定
                showUnBindDialog();
                break;
        }
    }


    private void wechatAuth() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_bind";
        LoginApp.getInstance().getApi().sendReq(req);
    }

    private int wxStatus;

    public static void action(Context context) {
        context.startActivity(new Intent(context, BindStatusActivity.class));
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mPresenter.getWxBindInfo();
    }

    private void initView() {

    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(LoginApp.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_status;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("社交账号");
    }


    @Override
    public void onWxBindInfo(BindStatus bindStatus) {
        wxStatus = bindStatus.getStatus();
        if (wxStatus == 1) {
            mWxBind.setText(bindStatus.getNickname());
        } else if (wxStatus == 0) {
            mWxBind.setText("未绑定");
        }
    }

    @Override
    public void onWxBind() {
        initData();
    }

    @Override
    public void onUnBindWx(Boolean b) {
        if (b) {
            initData();
        }
    }

    @Override
    public void onRebindWx(WxLogin wxLogin) {
        showReBindDialog(wxLogin);
    }

    @Override
    public void onRebindWx() {
        initData();
    }

    @Subscribe(sticky = true)
    public void onEvent(WxCodeEvent event) {
        String wxCode = event.code;
        if (!TextUtils.isEmpty(wxCode) && "wx_bind".equals(event.status)) {
            mPresenter.wxBind(wxCode);
        }
        EventBus.getDefault().removeStickyEvent(event);
    }


    private void showBindDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("温馨提示")
                .setMessage("确定要绑定微信吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wechatAuth();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showUnBindDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("您是否要解除与微信账号绑定？")
                .setMessage("解除绑定后将不能使用微信账号快速登录")
                .setPositiveButton("解除绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.unBindWx();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showReBindDialog(WxLogin wxLogin) {
        new AlertDialog.Builder(mContext)
                .setTitle("温馨提示")
                .setMessage("该微信号已被其他账号绑定,如果继续,原账号将自动解绑")
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.reBindWx(wxLogin.getHeadimgurl(), wxLogin.getHeadimgurl(), wxLogin.getOpenid(), wxLogin.getUnionid());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

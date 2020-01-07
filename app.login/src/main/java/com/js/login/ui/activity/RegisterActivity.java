package com.js.login.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.view.BaseActivity;
import com.base.frame.view.SimpleWebActivity;
import com.base.util.RegexUtils;
import com.base.util.manager.SpManager;
import com.js.login.LoginApp;
import com.js.login.R;
import com.js.login.R2;
import com.js.login.di.componet.DaggerActivityComponent;
import com.js.login.di.module.ActivityModule;
import com.js.login.global.Const;
import com.js.login.model.bean.UserInfo;
import com.js.login.model.event.UserStatusChangeEvent;
import com.js.login.ui.presenter.RegisterPresenter;
import com.js.login.ui.presenter.SmsCodePresenter;
import com.js.login.ui.presenter.contract.RegisterContract;
import com.js.login.ui.presenter.contract.SmsCodeContract;
import com.plugin.im.IMHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/04/25
 * desc   : 注册
 * version: 3.0.0
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, SmsCodeContract.View {

    @BindView(R2.id.edit_phone)
    EditText mPhone;
    @BindView(R2.id.edit_pwd)
    EditText mpwd;
    @BindView(R2.id.edit_code)
    EditText mCode;
    @BindView(R2.id.tv_get_code)
    TextView mGetCode;
    @BindView(R2.id.btn_register)
    TextView mRegister;
    @BindView(R2.id.cb_agree)
    CheckBox mAgree;
    @BindView(R2.id.tv_protocal)
    TextView mProtocal;

    private String phone;
    private String pwd;
    private String code;
    private Disposable mDisposable;

    @Inject
    SmsCodePresenter mCodePresenter;

    public static void action(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void init() {
        mCodePresenter.attachView(this);
        mAgree.setChecked(true);
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
        return R.layout.activity_register;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("");
    }

    @OnClick({R2.id.tv_get_code, R2.id.btn_register, R2.id.tv_protocal})
    public void onClick(View view) {
        if (view.getId()==R.id.tv_get_code){
            phone = mPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                toast("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobile(phone)) {
                toast("请输入正确的手机号");
                return;
            }
            mCodePresenter.sendSmsCode(phone);
        }else if (view.getId()==R.id.btn_register){
            phone = mPhone.getText().toString().trim();
            pwd = mpwd.getText().toString().trim();
            code = mCode.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                toast("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobile(phone)) {
                toast("请输入正确的手机号");
                return;
            }
            if (pwd.length() < 6 || pwd.length() > 16) {
                toast("请设置6-16位密码（字母、数字）");
                return;
            }
            if (TextUtils.isEmpty(code)) {
                toast("请输入验证码");
                return;
            }
            if (!mAgree.isChecked()) {
                toast("请勾选用户协议");
                return;
            }
            mPresenter.register(phone, pwd, code);
        }else if (view.getId()==R.id.tv_protocal){
            if ("shipper".equals(LoginApp.getInstance().appType)) {
                SimpleWebActivity.action(mContext, Const.H5_RegisterProtocal_SHIPPER, "用户协议");
            } else {
                SimpleWebActivity.action(mContext, Const.H5_RegisterProtocal_DRIVER, "用户协议");
            }
        }
    }

    @Override
    public void onSmsCode() {
        mGetCode.setClickable(false);
        mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mGetCode.setText(String.format("%d秒", 60 - aLong));
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mGetCode.setClickable(true);
                        mGetCode.setText("重新获取");
                    }
                }).subscribe();
    }

    @Override
    public void onRegister(String token) {
//        LoginActivity.action(this);
//        finish();
        LoginApp.getInstance().putToken(token);
        mPresenter.getUserInfo();
    }

    @Override
    public void onUserInfo(UserInfo userInfo) {
        String typeStr = "";
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            typeStr = "shipper";
        } else {
            typeStr = "driver";
        }
        JPushInterface.setAlias(mContext, 0, userInfo.getMobile());
        IMHelper.getInstance().login(typeStr + userInfo.getMobile(), typeStr + userInfo.getMobile());
        SpManager.getInstance(mContext).putSP("loginPhone", userInfo.getMobile());
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGIN_SUCCESS));
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCodePresenter != null) {
            mCodePresenter.detachView();
        }
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}

package com.js.login.ui.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.view.BaseFragment;
import com.base.frame.view.SimpleWebActivity;
import com.base.util.RegexUtils;
import com.base.util.manager.SpManager;
import com.js.login.LoginApp;
import com.js.login.R;
import com.js.login.R2;
import com.js.login.di.componet.DaggerFragmentComponent;
import com.js.login.di.module.FragmentModule;
import com.js.login.global.Const;
import com.js.login.model.bean.UserInfo;
import com.js.login.model.bean.WxLogin;
import com.js.login.model.event.LoginChangeEvent;
import com.js.login.model.event.UserStatusChangeEvent;
import com.js.login.model.event.WxCodeEvent;
import com.js.login.ui.activity.RegisterActivity;
import com.js.login.ui.activity.WxBindActivity;
import com.js.login.ui.presenter.CodeLoginPresenter;
import com.js.login.ui.presenter.SmsCodePresenter;
import com.js.login.ui.presenter.contract.CodeLoginContract;
import com.js.login.ui.presenter.contract.SmsCodeContract;
import com.plugin.im.IMHelper;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/21.
 */
public class CodeLoginFragment extends BaseFragment<CodeLoginPresenter> implements CodeLoginContract.View, SmsCodeContract.View {

    @BindView(R2.id.edit_phone)
    EditText mPhone;
    @BindView(R2.id.edit_code)
    EditText mCode;
    @BindView(R2.id.tv_protocal)
    TextView tvProtocal;
    @BindView(R2.id.tv_get_code)
    TextView mGetCode;
    @BindView(R2.id.wechat_img)
    ImageView mWechatImg;

    private String phone;
    private String code;
    private Disposable mDisposable;
    private String wxCode;

    @Inject
    SmsCodePresenter mCodePresenter;

    public static CodeLoginFragment newInstance() {
        return new CodeLoginFragment();
    }

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(LoginApp.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_fragment_phonecode;
    }

    @Override
    protected void init() {

        mPhone.setText(SpManager.getInstance(mContext).getSP("loginPhone"));
        mCodePresenter.attachView(this);
    }

    @OnClick({R2.id.tv_register, R2.id.tv_get_code, R2.id.tv_protocal,R2.id.tv_privacy,
            R2.id.btn_login, R2.id.tv_login_password, R2.id.wechat_img})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_register) {
            RegisterActivity.action(getActivity());
        } else if (view.getId() == R.id.tv_get_code) {
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
        } else if (view.getId() == R.id.tv_protocal) {
            if ("shipper".equals(LoginApp.getInstance().appType)) {
                SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal_SHIPPER, "用户协议");
            } else {
                SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal_DRIVER, "用户协议");
            }
        }else if (view.getId() == R.id.tv_privacy) {
            if ("shipper".equals(LoginApp.getInstance().appType)) {
                SimpleWebActivity.action(getActivity(), Const.H5_PrivacyProtocal_SHIPPER, "隐私政策");
            } else {
                SimpleWebActivity.action(getActivity(), Const.H5_PrivacyProtocal_DRIVER, "隐私政策");
            }
        }

        else if (view.getId() == R.id.btn_login) {
            phone = mPhone.getText().toString().trim();
            code = mCode.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                toast("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobile(phone)) {
                toast("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(code)) {
                toast("请输入验证码");
                return;
            }
            mPresenter.login(phone, code);
        } else if (view.getId() == R.id.tv_login_password) {
            EventBus.getDefault().post(new LoginChangeEvent(0));
        } else if (view.getId() == R.id.wechat_img) {
            if (!LoginApp.getInstance().getApi().isWXAppInstalled()) {
                toast("请先安装微信客户端。");
                return;
            }
            wechatAuth();
        }

    }

    private void wechatAuth() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        LoginApp.getInstance().getApi().sendReq(req);
    }



    @Override
    public void onSmsCode() {
        mGetCode.setClickable(false);
        mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isVisible()) {
                            mGetCode.setText(String.format("%d秒", 60 - aLong));
                        }
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isVisible()) {
                            mGetCode.setClickable(true);
                            mGetCode.setText("重新获取");
                        }
                    }
                }).subscribe();
    }

    @Override
    public void onWxBind(WxLogin wxLogin) {
        WxBindActivity.action(mContext, wxLogin, wxCode);
    }

    @Override
    public void onUserInfo(UserInfo userInfo) {
        String typeStr = "";
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            typeStr = "shipper";
        } else {
            typeStr = "driver";
        }
        IMHelper.getInstance().login(typeStr + userInfo.getMobile(), typeStr + userInfo.getMobile());
        SpManager.getInstance(mContext).putSP("loginPhone", userInfo.getMobile());

        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGIN_SUCCESS));
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    public void onLogin(String token) {
        LoginApp.getInstance().putToken(token);
        mPresenter.getUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(WxCodeEvent event) {
        wxCode = event.code;
        if (!TextUtils.isEmpty(wxCode) && "wx_login".equals(event.status)) {
            Log.d(getClass().getSimpleName(), "wxCode--->" + wxCode);
            mPresenter.wxBind(wxCode);
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

}

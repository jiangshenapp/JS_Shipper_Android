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
import com.js.login.ui.activity.ForgetPwdActivity;
import com.js.login.ui.activity.RegisterActivity;
import com.js.login.ui.activity.WxBindActivity;
import com.js.login.ui.presenter.PwdLoginPresenter;
import com.js.login.ui.presenter.contract.PwdLoginContract;
import com.plugin.im.IMHelper;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/21.
 */
public class PwdLoginFragment extends BaseFragment<PwdLoginPresenter> implements PwdLoginContract.View {

    @BindView(R2.id.tv_register)
    TextView mRegister;
    @BindView(R2.id.edit_phone)
    EditText mPhone;
    @BindView(R2.id.edit_pwd)
    EditText mPwd;
    @BindView(R2.id.wechat_img)
    ImageView mWechatImg;

    private String phone;
    private String pwd;
    private String wxCode;

    public static PwdLoginFragment newInstance() {
        return new PwdLoginFragment();
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
        return R.layout.login_fragment_password;
    }

    @Override
    protected void init() {
        mPhone.setText(SpManager.getInstance(mContext).getSP("loginPhone"));
    }

    @OnClick({R2.id.tv_register, R2.id.tv_forget_pwd, R2.id.tv_protocal,
            R2.id.btn_login, R2.id.tv_login_phonecode, R2.id.wechat_img, R2.id.tv_privacy})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_register) {
            RegisterActivity.action(getActivity());
        } else if (view.getId() == R.id.tv_forget_pwd) {
            ForgetPwdActivity.action(getActivity());
        } else if (view.getId() == R.id.tv_protocal) {
            if ("shipper".equals(LoginApp.getInstance().appType)) {
                SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal_SHIPPER, "用户协议");
            } else {
                SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal_DRIVER, "用户协议");
            }
        } else if (view.getId() == R.id.tv_privacy) {
            if ("shipper".equals(LoginApp.getInstance().appType)) {
                SimpleWebActivity.action(getActivity(), Const.H5_PrivacyProtocal_SHIPPER, "隐私协议");
            } else {
                SimpleWebActivity.action(getActivity(), Const.H5_PrivacyProtocal_DRIVER, "隐私协议");
            }
        } else if (view.getId() == R.id.btn_login) {
            phone = mPhone.getText().toString().trim();
            pwd = mPwd.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                toast("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobile(phone)) {
                toast("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                toast("请输入密码");
                return;
            }
            mPresenter.login(phone, pwd);
        } else if (view.getId() == R.id.tv_login_phonecode) {
            EventBus.getDefault().post(new LoginChangeEvent(1));
        } else if (view.getId() == R.id.wechat_img) {
            if (!LoginApp.getInstance().getApi().isWXAppInstalled()) {
                toast("请先安装微信客户端。");
                return;
            }
            wechatAuth();
        }
    }

    @Override
    public void onLogin(String token) {
        LoginApp.getInstance().putToken(token);
        mPresenter.getUserInfo();
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

    private void wechatAuth() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        LoginApp.getInstance().getApi().sendReq(req);
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

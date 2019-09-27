package com.js.login.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.js.login.model.event.LoginChangeEvent;
import com.js.login.model.event.UserStatusChangeEvent;
import com.js.login.ui.activity.ForgetPwdActivity;
import com.js.login.ui.activity.RegisterActivity;
import com.js.login.ui.presenter.PwdLoginPresenter;
import com.js.login.ui.presenter.contract.PwdLoginContract;
import com.plugin.im.IMHelper;

import org.greenrobot.eventbus.EventBus;

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

    private String phone;
    private String pwd;

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
        return R.layout.fragment_login_password;
    }

    @Override
    protected void init() {
        mPhone.setText(SpManager.getInstance(mContext).getSP("loginPhone"));
    }

    @OnClick({R2.id.tv_register, R2.id.tv_forget_pwd, R2.id.tv_protocal, R2.id.btn_login, R2.id.tv_login_phonecode})
    public void onViewClicked(View view) {
        if (view.getId()==R.id.tv_register){
            RegisterActivity.action(getActivity());
        }else if (view.getId()==R.id.tv_forget_pwd){
            ForgetPwdActivity.action(getActivity());
        }else if (view.getId()==R.id.tv_protocal){
            SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal, "用户协议");
        }else if (view.getId()==R.id.btn_login){
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
        }else if (view.getId()==R.id.tv_login_phonecode){
            EventBus.getDefault().post(new LoginChangeEvent(1));
        }

    }

    @Override
    public void onLogin(String token) {
        toast("登录成功");
        IMHelper.getInstance().login(phone,phone);
        LoginApp.getInstance().putToken(token);
        SpManager.getInstance(mContext).putSP("loginPhone",phone);
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGIN_SUCCESS));
        getActivity().finish();
    }
}

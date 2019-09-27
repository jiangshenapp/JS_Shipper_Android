package com.js.login.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.js.login.model.event.LoginChangeEvent;
import com.js.login.model.event.UserStatusChangeEvent;
import com.js.login.ui.activity.RegisterActivity;
import com.js.login.ui.presenter.CodeLoginPresenter;
import com.js.login.ui.presenter.SmsCodePresenter;
import com.js.login.ui.presenter.contract.CodeLoginContract;
import com.js.login.ui.presenter.contract.SmsCodeContract;
import com.plugin.im.IMHelper;

import org.greenrobot.eventbus.EventBus;

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

    private String phone;
    private String code;
    private Disposable mDisposable;

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
        return R.layout.fragment_login_phonecode;
    }

    @Override
    protected void init() {

        mPhone.setText(SpManager.getInstance(mContext).getSP("loginPhone"));
        mCodePresenter.attachView(this);
    }

    @OnClick({R2.id.tv_register, R2.id.tv_get_code, R2.id.tv_protocal, R2.id.btn_login, R2.id.tv_login_password})
    public void onViewClicked(View view) {

        if (view.getId()==R.id.tv_register){
            RegisterActivity.action(getActivity());
        }else if (view.getId()==R.id.tv_get_code){
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
        }else if (view.getId()==R.id.tv_protocal){
            SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal, "用户协议");
        }else if (view.getId()==R.id.btn_login){
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
        }else if (view.getId()==R.id.tv_login_password){
            EventBus.getDefault().post(new LoginChangeEvent(0));
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
    public void onLogin(String token) {
        toast("登录成功");
        IMHelper.getInstance().login(phone,phone);
        LoginApp.getInstance().putToken(token);
        SpManager.getInstance(mContext).putSP("loginPhone",phone);
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.LOGIN_SUCCESS));
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}

package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/02
 * desc   : 忘记密码第一步
 * version: 3.0.0
 */
public interface ForgetPwdContract {

    interface View extends IBaseView {
        void onForgetPwd();
    }

    interface Presenter extends IPresenter<View> {
        void forgetPwd(String phone, String code);
    }
}
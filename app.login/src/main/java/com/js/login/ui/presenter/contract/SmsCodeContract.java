package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/04/25
 * desc   :
 * version: 3.0.0
 */
public interface SmsCodeContract {

    interface View extends IBaseView {
        void onSmsCode();
    }

    interface Presenter extends IPresenter<View> {
        void sendSmsCode(String phone);

    }
}
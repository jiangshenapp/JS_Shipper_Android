package com.js.driver.ui.user.presenter.contract;

import com.xlgcx.frame.mvp.IBaseView;
import com.xlgcx.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/21.
 */
public interface CodeLoginContract {


    interface View extends IBaseView {
        void onSmsCode();

        void onLogin(String token);

    }

    interface Presenter extends IPresenter<View> {
        void sendSmsCode(String phone);

        void login(String phone, String code);
    }
}

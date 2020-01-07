package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.login.model.bean.UserInfo;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/04/25
 * desc   :
 * version: 3.0.0
 */
public interface RegisterContract {

    interface View extends IBaseView {
        void onRegister(String token);

        void onUserInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter<View> {

        void register(String phone, String password, String code);

        void getUserInfo();
    }
}

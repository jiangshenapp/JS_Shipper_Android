package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.login.model.bean.UserInfo;
import com.js.login.model.bean.WxLogin;

/**
 * Created by huyg on 2019/4/21.
 */
public interface CodeLoginContract {

    interface View extends IBaseView {

        void onLogin(String token);

        void onWxBind(WxLogin wxLogin);

        void onUserInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter<View> {

        void login(String phone, String code);

        void wxBind(String code);

        void getUserInfo();
    }
}

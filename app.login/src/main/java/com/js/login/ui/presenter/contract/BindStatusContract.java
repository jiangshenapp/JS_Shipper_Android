package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.login.model.bean.BindStatus;
import com.js.login.model.bean.WxLogin;

/**
 * Created by huyg on 2019-10-06.
 */
public interface BindStatusContract {

    interface View extends IBaseView {
        void onWxBindInfo(BindStatus bindStatus);

        void onWxBind();

        void onUnBindWx(Boolean b);

        void onRebindWx(WxLogin wxLogin);

        void onRebindWx();

    }

    interface Presenter extends IPresenter<View> {
        void getWxBindInfo();

        void wxBind(String code);

        void unBindWx();

        void reBindWx(String headimgurl,String nickname,String openid,String unionid);
    }
}

package com.js.login.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019-09-30.
 */
public interface WxBindContract {

    interface View extends IBaseView{
        void onWxLogin(String token);
    }

    interface Presenter extends IPresenter<View>{

        void wxLogin(String code,String headimgurl,String mobile,String nickname,String openid,String unionid);

        void wxReBinding(String headimgurl,String nickname,String openid,String unionid);
    }
}

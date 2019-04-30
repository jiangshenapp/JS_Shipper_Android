package com.js.shipper.ui.main.presenter.contract;

import com.js.shipper.model.bean.UserInfo;
import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/1.
 */
public interface MineContract {

    interface View extends IBaseView {
        void onUserInfo(UserInfo userInfo);


        void finishRefresh();
    }

    interface Presenter extends IPresenter<View> {
        void getUserInfo();
    }
}

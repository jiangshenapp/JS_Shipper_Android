package com.js.driver.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.model.response.ListResponse;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public interface ServiceContract {

    interface View extends IBaseView {
        void onBannerList(List<BannerBean> mBannerBeans);
        void onServiceList(List<ServiceBean> mServiceBeans);
    }

    interface Presenter extends IPresenter<View> {
        void getBannerList(int type);
        void getServiceList();
    }

}

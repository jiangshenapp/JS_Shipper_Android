package com.js.driver.ui.center.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/23
 * desc   :
 * version: 3.0.0
 */
public interface ParkAddressContract {

    interface View extends IBaseView {
        void onSubmitParkAddress();
    }

    interface Presenter extends IPresenter<View> {
        void submitParkAddress(String contactName, String contractPhone, String contactLocation, String contactAddress,
                           String image1, String image2, String image3, String image4);
    }
}

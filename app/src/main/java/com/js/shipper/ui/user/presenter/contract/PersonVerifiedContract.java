package com.js.shipper.ui.user.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.AuthInfo;

/**
 * Created by huyg on 2019/4/24.
 */
public interface PersonVerifiedContract {

    interface View extends IBaseView{
        void onPersonVerifiedInfo(AuthInfo authInfo);
        void onSubmitPersonVerified();
    }

    interface Presenter extends IPresenter<View>{
        void getPersonVerifiedInfo();
        void submitPersonVerified(String idImage, String idBackImage, String idHandImage, String personName, String idCode, String address);
    }
}

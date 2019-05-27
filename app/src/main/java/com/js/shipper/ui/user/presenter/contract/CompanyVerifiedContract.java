package com.js.shipper.ui.user.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.AuthInfo;

/**
 * Created by huyg on 2019/4/24.
 */
public interface CompanyVerifiedContract {

    interface View extends IBaseView{
        void onCompanyVerifiedInfo(AuthInfo authInfo);
        void onSubmitCompanyVerified();
    }

    interface Presenter extends IPresenter<View>{
        void getCompanyVerifiedInfo();
        void submitCompanyVerified(String companyName, String registrationNumber, String address, String detailAddress, String businessLicenceImage);
    }
}

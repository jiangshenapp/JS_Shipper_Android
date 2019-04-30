package com.js.shipper.ui.user.presenter;

import com.js.shipper.ui.user.presenter.contract.UserCenterContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class UserCenterPresenter extends RxPresenter<UserCenterContract.View>  implements UserCenterContract.Presenter{


    @Inject
    public UserCenterPresenter(){

    }
}

package com.js.driver.ui.user.presenter;

import com.js.driver.ui.user.presenter.contract.UserCenterContract;
import com.xlgcx.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class UserCenterPresenter extends RxPresenter<UserCenterContract.View>  implements UserCenterContract.Presenter{


    @Inject
    public UserCenterPresenter(){

    }
}

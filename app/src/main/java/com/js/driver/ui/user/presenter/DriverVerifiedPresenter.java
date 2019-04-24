package com.js.driver.ui.user.presenter;

import com.js.driver.ui.user.presenter.contract.DriverVerifiedContract;
import com.xlgcx.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class DriverVerifiedPresenter extends RxPresenter<DriverVerifiedContract.View>  implements DriverVerifiedContract.Presenter{


    @Inject
    public DriverVerifiedPresenter(){

    }
}

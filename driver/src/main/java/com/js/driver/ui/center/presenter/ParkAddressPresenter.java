package com.js.driver.ui.center.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.driver.ui.center.presenter.contract.ParkAddressContract;

import javax.inject.Inject;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/23
 * desc   :
 * version: 3.0.0
 */
public class ParkAddressPresenter extends RxPresenter<ParkAddressContract.View> implements ParkAddressContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public ParkAddressPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }
}

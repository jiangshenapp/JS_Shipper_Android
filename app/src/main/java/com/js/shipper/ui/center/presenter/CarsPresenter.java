package com.js.shipper.ui.center.presenter;

import com.js.shipper.ui.center.presenter.contract.CarsContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsPresenter extends RxPresenter<CarsContract.View> implements CarsContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CarsPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }





}

package com.js.shipper.presenter;

import com.base.frame.mvp.RxPresenter;
import com.js.shipper.presenter.contract.SplashContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019-05-23.
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {


    @Inject
    public SplashPresenter() {

    }


}

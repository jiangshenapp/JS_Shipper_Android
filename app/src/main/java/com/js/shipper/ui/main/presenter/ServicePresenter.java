package com.js.shipper.ui.main.presenter;

import com.js.shipper.ui.main.presenter.contract.ServiceContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/1.
 */
public class ServicePresenter extends RxPresenter<ServiceContract.View> implements ServiceContract.Presenter {


    @Inject
    public ServicePresenter(){

    }

}

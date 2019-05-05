package com.js.shipper.ui.main.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.shipper.ui.center.presenter.contract.CarsContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/30.
 */
public class CarSourcePresenter extends RxPresenter<CarsContract.View> implements CarsContract.Presenter{


    @Inject
    public CarSourcePresenter(){

    }


}

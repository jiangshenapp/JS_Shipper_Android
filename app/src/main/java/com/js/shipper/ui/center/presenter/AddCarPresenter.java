package com.js.shipper.ui.center.presenter;

import com.js.shipper.ui.center.presenter.contract.AddCarContract;
import com.base.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/29.
 */
public class AddCarPresenter extends RxPresenter<AddCarContract.View> implements AddCarContract.Presenter {


    @Inject
    public AddCarPresenter(){

    }
}

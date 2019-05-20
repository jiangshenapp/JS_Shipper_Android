package com.js.shipper.ui.ship.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.shipper.ui.ship.presenter.contract.ReceiverInfoContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/5/20.
 */
public class ReceiverInfoPresenter extends RxPresenter<ReceiverInfoContract.View> implements ReceiverInfoContract.Presenter {


    @Inject
    public ReceiverInfoPresenter(){

    }
}

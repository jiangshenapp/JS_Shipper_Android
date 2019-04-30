package com.js.shipper.ui.order.presenter;

import com.js.shipper.ui.order.presenter.contract.OrderContract;
import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderPresenter extends RxPresenter<OrderContract.View> implements OrderContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public OrderPresenter(){

    }

}

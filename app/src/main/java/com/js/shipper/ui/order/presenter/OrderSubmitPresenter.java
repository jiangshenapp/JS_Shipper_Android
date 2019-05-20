package com.js.shipper.ui.order.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitPresenter extends RxPresenter<OrderSubmitContract.View> implements OrderSubmitContract.Presenter {


    @Inject
    public OrderSubmitPresenter(){

    }
}

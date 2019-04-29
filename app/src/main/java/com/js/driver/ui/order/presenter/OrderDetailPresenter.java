package com.js.driver.ui.order.presenter;

import com.js.driver.ui.order.presenter.contract.OrderDetailContract;
import com.xlgcx.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderDetailPresenter extends RxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {


    @Inject
    public OrderDetailPresenter(){

    }

}

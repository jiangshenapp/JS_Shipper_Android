package com.js.shipper.ui.main.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.shipper.ui.main.presenter.contract.DeliveryContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/30.
 */
public class DeliveryPresenter extends RxPresenter<DeliveryContract.View> implements DeliveryContract.Presenter {




    @Inject
    public DeliveryPresenter(){
    }

}

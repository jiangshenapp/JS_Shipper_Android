package com.js.shipper.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.ui.message.presenter.contract.MessageDetailContract;
import com.js.shipper.ui.message.presenter.contract.PushDetailContract;

import javax.inject.Inject;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/08
 * desc   :
 * version: 3.0.0
 */
public class PushDetailPresenter extends RxPresenter<PushDetailContract.View> implements PushDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public PushDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }
}

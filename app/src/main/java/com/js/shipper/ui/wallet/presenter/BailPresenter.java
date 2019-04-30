package com.js.shipper.ui.wallet.presenter;

import com.js.shipper.ui.wallet.presenter.contract.BailContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class BailPresenter extends RxPresenter<BailContract.View> implements BailContract.Presenter {

    @Inject
    public BailPresenter(){

    }

}

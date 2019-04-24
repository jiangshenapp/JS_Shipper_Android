package com.js.driver.ui.wallet.presenter;

import com.js.driver.ui.wallet.presenter.contract.BillContract;
import com.xlgcx.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class BillPresenter extends RxPresenter<BillContract.View> implements BillContract.Presenter {

    @Inject
    public BillPresenter() {

    }
}

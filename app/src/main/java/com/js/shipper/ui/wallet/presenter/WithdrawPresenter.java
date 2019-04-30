package com.js.shipper.ui.wallet.presenter;

import com.js.shipper.ui.wallet.presenter.contract.WithdrawContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class WithdrawPresenter extends RxPresenter<WithdrawContract.View> implements WithdrawContract.Presenter {


    @Inject
    public WithdrawPresenter(){

    }

}

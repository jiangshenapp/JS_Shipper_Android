package com.js.driver.ui.wallet.presenter;

import com.js.driver.ui.wallet.presenter.contract.RechargeContract;
import com.js.driver.ui.wallet.presenter.contract.WithdrawContract;
import com.xlgcx.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class RechargePresenter extends RxPresenter<RechargeContract.View> implements RechargeContract.Presenter {

    @Inject
    public RechargePresenter(){

    }

}

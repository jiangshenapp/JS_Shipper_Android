package com.js.shipper.ui.wallet.presenter;

import com.js.shipper.ui.wallet.presenter.contract.WalletContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/24.
 */
public class WalletPresenter extends RxPresenter<WalletContract.View> implements WalletContract.Presenter {


    @Inject
    public WalletPresenter(){

    }
}

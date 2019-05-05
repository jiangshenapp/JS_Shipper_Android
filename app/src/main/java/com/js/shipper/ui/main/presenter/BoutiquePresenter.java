package com.js.shipper.ui.main.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.shipper.ui.main.presenter.contract.BoutiqueContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/30.
 */
public class BoutiquePresenter extends RxPresenter<BoutiqueContract.View> implements BoutiqueContract.Presenter {


    @Inject
    public BoutiquePresenter(){

    }
}

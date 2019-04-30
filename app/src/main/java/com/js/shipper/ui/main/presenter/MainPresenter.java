package com.js.shipper.ui.main.presenter;

import com.js.shipper.ui.main.presenter.contract.MainContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/1.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(){

    }
}

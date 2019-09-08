package com.js.shipper.ui.main.presenter;

import com.js.shipper.ui.main.presenter.contract.FindOrderContract;
import com.base.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/1.
 */
public class FindOrderPresenter extends RxPresenter<FindOrderContract.View> implements FindOrderContract.Presenter {


    @Inject
    public FindOrderPresenter(){

    }
}

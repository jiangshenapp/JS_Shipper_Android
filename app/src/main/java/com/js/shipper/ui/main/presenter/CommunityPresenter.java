package com.js.shipper.ui.main.presenter;

import com.js.shipper.ui.main.presenter.contract.CommunityContract;
import com.base.frame.mvp.RxPresenter;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/1.
 */
public class CommunityPresenter extends RxPresenter<CommunityContract.View> implements CommunityContract.Presenter {

    @Inject
    public CommunityPresenter(){

    }
}

package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.community.ui.presenter.contract.PublishPostContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019-09-11.
 */
public class PublishPostPresenter extends RxPresenter<PublishPostContract.View> implements PublishPostContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public PublishPostPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }



}

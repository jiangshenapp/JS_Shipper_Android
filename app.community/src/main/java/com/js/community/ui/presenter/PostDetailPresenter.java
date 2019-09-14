package com.js.community.ui.presenter;

import androidx.annotation.UiThread;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.community.ui.presenter.contract.PostDetailContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostDetailPresenter extends RxPresenter<PostDetailContract.View> implements PostDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public PostDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }






}

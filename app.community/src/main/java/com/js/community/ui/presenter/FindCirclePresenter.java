package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.community.api.CircleApi;
import com.js.community.model.bean.CircleBean;
import com.js.community.ui.presenter.contract.FindCircleContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-09.
 */
public class FindCirclePresenter extends RxPresenter<FindCircleContract.View> implements FindCircleContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public FindCirclePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getAllCircle(String city, int showSide) {
        Disposable disposable = mApiFactory.getApi(CircleApi.class)
                .getAllCircle(city, showSide)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<CircleBean>>() {
                    @Override
                    public void accept(List<CircleBean> circleBeans) throws Exception {
                        mView.finishRefresh();
                        mView.onAllCircle(circleBeans);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void applyCircle(long id) {
        Disposable disposable = mApiFactory.getApi(CircleApi.class)
                .applyCircle(id)
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()) {
                            mView.onApplayCircle(true);
                        } else {
                            mView.onApplayCircle(false);
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

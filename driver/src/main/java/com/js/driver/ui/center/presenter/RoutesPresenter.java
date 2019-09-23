package com.js.driver.ui.center.presenter;

import com.js.driver.api.RouteApi;
import com.js.driver.model.bean.RouteBean;
import com.js.driver.model.request.RouteRequest;
import com.js.driver.model.response.ListResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.driver.ui.center.presenter.contract.RoutesContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.bean.BaseHttpResponse;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/06/11
 * desc   :
 * version: 3.0.0
 */
public class RoutesPresenter extends RxPresenter<RoutesContract.View> implements RoutesContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public RoutesPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getRouteList() {
        Disposable disposable = mApiFactory.getApi(RouteApi.class).getRouteList(new RouteRequest())
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<RouteBean>>() {
                    @Override
                    public void accept(ListResponse<RouteBean> response) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onRouteList(response);
                    }
                },new RxException<>(e->{
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }

    @Override
    public void removeRoute(long id) {
        Disposable disposable = mApiFactory.getApi(RouteApi.class)
                .removeRoute(id)
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
                        if (response.isSuccess()){
                            mView.onRemoveRoute();
                        } else {
                            mView.toast(response.getMsg());
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

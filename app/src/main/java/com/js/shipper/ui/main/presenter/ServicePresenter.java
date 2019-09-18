package com.js.shipper.ui.main.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.ServiceApi;
import com.js.shipper.model.bean.BannerBean;
import com.js.shipper.model.bean.ServiceBean;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.main.presenter.contract.ServiceContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public class ServicePresenter extends RxPresenter<ServiceContract.View> implements ServiceContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public ServicePresenter(ApiFactory mApiFactory) {
        this.mApiFactory = mApiFactory;
    }

    @Override
    public void getBannerList(int type) {
        Disposable disposable = mApiFactory.getApi(ServiceApi.class)
                .getBannerList(type)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<List<BannerBean>>() {
                    @Override
                    public void accept(List<BannerBean> bannerBeans) throws Exception {
                        mView.closeProgress();
                        mView.onBannerList(bannerBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.onBannerListFail();
                }));
        addDispose(disposable);
    }

    @Override
    public void getServiceList() {
        Disposable disposable = mApiFactory.getApi(ServiceApi.class)
                .getSysServiceList()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<List<ServiceBean>>() {
                    @Override
                    public void accept(List<ServiceBean> serviceBeans) throws Exception {
                        mView.closeProgress();
                        mView.onServiceList(serviceBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

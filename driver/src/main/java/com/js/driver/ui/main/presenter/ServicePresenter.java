package com.js.driver.ui.main.presenter;

import com.base.http.ApiFactory;
import com.base.http.HttpResponse;
import com.js.driver.api.ServiceApi;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.model.request.BannerRequest;
import com.js.driver.model.response.ListResponse;
import com.js.driver.rx.RxException;
import com.js.driver.rx.RxResult;
import com.js.driver.rx.RxSchedulers;
import com.js.driver.ui.main.presenter.contract.ServiceContract;
import com.base.frame.mvp.RxPresenter;

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
                    mView.onBannerListFail();
                    mView.closeProgress();
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

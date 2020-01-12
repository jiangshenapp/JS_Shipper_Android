package com.js.shipper.ui.car.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.shipper.api.CarApi;
import com.js.shipper.api.OrderApi;
import com.js.shipper.api.PayApi;
import com.js.shipper.model.bean.BillBean;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.ui.car.presenter.contract.CarContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public class CarPresenter extends RxPresenter<CarContract.View> implements CarContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CarPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCarList(long type) {
        Disposable disposable = mApiFactory.getApi(CarApi.class)
                .getMyCarList(type)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<CarBean>>() {
                    @Override
                    public void accept(List<CarBean> carBeans) throws Exception {
                        mView.closeProgress();
                        mView.finishRefreshAndLoadMore();
                        mView.onCarList(carBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.finishRefreshAndLoadMore();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void removeCar(long carId) {
        Disposable disposable = mApiFactory.getApi(CarApi.class)
                .removeCar(carId)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.closeProgress();
                        mView.onRemoveCar(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

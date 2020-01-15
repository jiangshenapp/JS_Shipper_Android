package com.js.shipper.ui.car.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.shipper.api.CarApi;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.ui.car.presenter.contract.AddCarContract;

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
public class AddCarPresenter extends RxPresenter<AddCarContract.View> implements AddCarContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public AddCarPresenter(ApiFactory mApiFactory) {
        this.mApiFactory = mApiFactory;
    }

    @Override
    public void queryCarList(String input) {
        Disposable disposable = mApiFactory.getApi(CarApi.class)
                .queryCarList(input)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<CarBean>>() {
                    @Override
                    public void accept(List<CarBean> carBeans) throws Exception {
                        mView.closeProgress();
                        mView.onQueryCarList(carBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void addCar(long carId, String remark, long type) {
        Disposable disposable = mApiFactory.getApi(CarApi.class)
                .addCar(carId, remark, type)
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
                        mView.onAddCar(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

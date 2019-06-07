package com.js.shipper.ui.ship.presenter;

import com.js.frame.mvp.BasePresenter;
import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.shipper.api.AreaApi;
import com.js.shipper.model.bean.SelectCity;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.ship.presenter.contract.SelectCityContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-06.
 */
public class SelectCityPresenter extends RxPresenter<SelectCityContract.View> implements SelectCityContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public SelectCityPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getCityList() {
        Disposable disposable = mApiFactory.getApi(AreaApi.class)
                .getCityList()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<List<SelectCity>>() {
                    @Override
                    public void accept(List<SelectCity> selectCities) throws Exception {
                        mView.closeProgress();
                        mView.onCityList(selectCities);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

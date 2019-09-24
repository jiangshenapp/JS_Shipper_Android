package com.js.driver.ui.center.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.driver.api.AreaApi;
import com.js.driver.model.bean.SelectCity;
import com.js.driver.ui.center.presenter.contract.SelectCityContract;

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

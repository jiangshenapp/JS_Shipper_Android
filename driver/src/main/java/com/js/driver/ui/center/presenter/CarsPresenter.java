package com.js.driver.ui.center.presenter;

import com.js.driver.api.CarApi;
import com.js.driver.model.bean.CarBean;
import com.js.driver.model.response.ListResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.driver.ui.center.presenter.contract.CarsContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsPresenter extends RxPresenter<CarsContract.View> implements CarsContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CarsPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCarList() {
        Disposable disposable = mApiFactory.getApi(CarApi.class).getCarList()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<CarBean>>() {
                    @Override
                    public void accept(ListResponse<CarBean> response) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onCarList(response);
                    }
                },new RxException<>(e->{
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

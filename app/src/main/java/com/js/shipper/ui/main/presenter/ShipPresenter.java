package com.js.shipper.ui.main.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.AddStepOne;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.main.presenter.contract.ShipContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class ShipPresenter extends RxPresenter<ShipContract.View> implements ShipContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public ShipPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void addStepOne(AddStepOne addStepOne) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class).addStepOne(addStepOne)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.onStepOne(aLong);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);
    }
}

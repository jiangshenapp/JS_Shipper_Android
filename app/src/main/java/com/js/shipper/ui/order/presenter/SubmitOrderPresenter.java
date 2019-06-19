package com.js.shipper.ui.order.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.AddOrder;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.SubmitOrderContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-18.
 */
public class SubmitOrderPresenter extends RxPresenter<SubmitOrderContract.View> implements SubmitOrderContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public SubmitOrderPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void submitOrder(AddOrder addOrder) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class).submitOrder(addOrder)
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
                        mView.onSubmitOrder(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

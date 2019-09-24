package com.js.shipper.ui.order.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.AddStepTwo;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitPresenter extends RxPresenter<OrderSubmitContract.View> implements OrderSubmitContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public OrderSubmitPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void submit(AddStepTwo addStepTwo) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class).addStepTwo(addStepTwo)
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
                        mView.onSubmit(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

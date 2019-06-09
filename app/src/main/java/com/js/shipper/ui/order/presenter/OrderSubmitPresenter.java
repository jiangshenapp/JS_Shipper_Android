package com.js.shipper.ui.order.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.http.BaseHttpResponse;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.AddStepTwo;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
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
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()) {
                            mView.onSubmit();
                        }
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

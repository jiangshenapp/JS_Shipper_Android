package com.js.shipper.ui.order.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.OrderEdit;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.OrderEditContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-17.
 */
public class OrderEditPresenter extends RxPresenter<OrderEditContract.View> implements OrderEditContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public OrderEditPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void editOrder(OrderEdit orderEdit, long id) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class)
                .editOrder(orderEdit, id)
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
                        mView.onEditOrder(aBoolean);

                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

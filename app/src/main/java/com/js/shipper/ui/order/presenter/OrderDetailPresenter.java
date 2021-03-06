package com.js.shipper.ui.order.presenter;

import com.js.http.ApiFactory;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.OrderDetailContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderDetailPresenter extends RxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public OrderDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getOrderDetail(long id) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class)
                .getOrderDetail(id)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<OrderBean>() {
                    @Override
                    public void accept(OrderBean orderBean) throws Exception {
                        mView.finishRefresh();
                        mView.onOrderDetail(orderBean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void cancelOrder(long id) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class)
                .cancelOrder(id)
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
                        mView.onCancelOrder(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void confirmOrder(long id) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class)
                .confirmOrder(id)
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
                        mView.onConfirmOrder(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void receiptOrder(long id) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class)
                .receiptOrder(id)
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
                        mView.onReceiptOrder(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

}

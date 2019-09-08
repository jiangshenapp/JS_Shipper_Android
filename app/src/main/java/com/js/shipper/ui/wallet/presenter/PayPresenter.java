package com.js.shipper.ui.wallet.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.js.shipper.api.PayApi;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.PayInfo;
import com.js.shipper.model.bean.PayRouter;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.wallet.presenter.contract.PayContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-13.
 */
public class PayPresenter extends RxPresenter<PayContract.View> implements PayContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public PayPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void payAccount(String orderNo) {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .payAccount(orderNo)
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
                        if (response.isSuccess()){
                            mView.onPayAccount(true);
                        }else {
                            mView.onPayAccount(false);
                        }
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void payOrder(int tradeType, int channelType, double money, int routeId,String orderNo) {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .recharge(tradeType, channelType, money, routeId,orderNo)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<PayInfo>() {
                    @Override
                    public void accept(PayInfo payInfo) throws Exception {
                        mView.closeProgress();
                        mView.onPayOrder(payInfo);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }


    @Override
    public void getPayRouter() {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .getPayRouter(Const.BUSINESS_ID, Const.MERCHANT_ID)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<PayRouter>>() {
                    @Override
                    public void accept(List<PayRouter> payRouters) throws Exception {
                        mView.closeProgress();
                        mView.onPayRouter(payRouters);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }


}




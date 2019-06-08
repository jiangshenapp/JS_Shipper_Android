package com.js.shipper.ui.wallet.presenter;

import com.js.http.ApiFactory;
import com.js.shipper.api.PayApi;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.PayInfo;
import com.js.shipper.model.bean.PayRouter;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.wallet.presenter.contract.RechargeContract;
import com.js.frame.mvp.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class RechargePresenter extends RxPresenter<RechargeContract.View> implements RechargeContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public RechargePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void payOrder(int channelType, double money, int routeId) {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .recharge(Const.BUSINESS_ID, channelType, money, routeId)
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

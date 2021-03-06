package com.js.shipper.ui.wallet.presenter;

import com.js.http.ApiFactory;
import com.js.shipper.api.PayApi;
import com.js.shipper.model.bean.BillBean;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.wallet.presenter.contract.BillContract;
import com.js.frame.mvp.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class BillPresenter extends RxPresenter<BillContract.View> implements BillContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public BillPresenter(ApiFactory mApiFactory) {
        this.mApiFactory = mApiFactory;
    }

    @Override
    public void getBillList(int status) {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .getTradeRecord(status)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<BillBean>>() {
                    @Override
                    public void accept(List<BillBean> billBeans) throws Exception {
                        mView.closeProgress();
                        mView.finishRefreshAndLoadMore();
                        mView.onBillList(billBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.finishRefreshAndLoadMore();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

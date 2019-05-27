package com.js.shipper.ui.main.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.http.BaseHttpResponse;
import com.js.shipper.api.OrderApi;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
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
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        if (response.isSuccess()) {
                            mView.onStepOne();
                        } else {
                            mView.toast(response.getMsg());
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);
    }
}

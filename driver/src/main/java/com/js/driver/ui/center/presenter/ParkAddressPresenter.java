package com.js.driver.ui.center.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.js.driver.api.UserApi;
import com.js.driver.model.request.ParkAddressRequest;
import com.js.driver.model.request.ParkVerifiedRequest;
import com.js.driver.rx.RxException;
import com.js.driver.rx.RxSchedulers;
import com.js.driver.ui.center.presenter.contract.ParkAddressContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/23
 * desc   :
 * version: 3.0.0
 */
public class ParkAddressPresenter extends RxPresenter<ParkAddressContract.View> implements ParkAddressContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public ParkAddressPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void submitParkAddress(String contactName, String contractPhone, String contactLocation, String contactAddress,
                                  String image1, String image2, String image3, String image4) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .parkSupplement(new ParkAddressRequest(contactName, contractPhone, contactLocation, contactAddress,
                        image1, image2, image3, image4))
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
                            mView.onSubmitParkAddress();
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

package com.js.shipper.ui.user.presenter;

import com.js.shipper.api.UserApi;
import com.js.shipper.ui.user.presenter.contract.SmsCodeContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/04/25
 * desc   :
 * version: 3.0.0
 */
public class SmsCodePresenter extends RxPresenter<SmsCodeContract.View> implements SmsCodeContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public SmsCodePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void sendSmsCode(String phone) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .sendSmsCode(phone)
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
                        mView.toast(response.getMsg());
                        if (response.isSuccess()){
                            mView.onSmsCode();
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

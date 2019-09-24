package com.js.shipper.ui.user.presenter;

import com.js.shipper.api.UserApi;
import com.js.shipper.ui.user.presenter.contract.PwdLoginContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/21.
 */
public class PwdLoginPresenter extends RxPresenter<PwdLoginContract.View> implements PwdLoginContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public PwdLoginPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void login(String phone, String pwd) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .login(1,phone, pwd)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.closeProgress();
                        mView.onLogin(s);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

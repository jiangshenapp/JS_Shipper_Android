package com.js.shipper.ui.user.presenter;

import com.js.shipper.api.UserApi;
import com.js.shipper.ui.user.presenter.contract.CodeLoginContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/21.
 */
public class CodeLoginPresenter extends RxPresenter<CodeLoginContract.View> implements CodeLoginContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CodeLoginPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void login(String phone, String code) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .smsLogin(phone, code)
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

package com.js.login.ui.presenter;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.login.api.UserApi;
import com.js.login.model.bean.UserInfo;
import com.js.login.ui.presenter.contract.RegisterContract;

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
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public RegisterPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void register(String phone, String password, String code) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .registry(phone, password, code)
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
                        mView.onRegister(s);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void getUserInfo() {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .profile()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        mView.onUserInfo(userInfo);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

package com.js.login.ui.presenter;

import com.base.frame.bean.HttpResponse;
import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.google.gson.Gson;
import com.js.login.LoginApp;
import com.js.login.api.UserApi;
import com.js.login.api.WxApi;
import com.js.login.model.bean.WxLogin;
import com.js.login.ui.presenter.contract.PwdLoginContract;

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
        int appType = "shipper".equals(LoginApp.getInstance().appType) ? 1 : 2;
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .login(appType, phone, pwd)
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

    @Override
    public void wxBind(String code) {
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .wxCodeLogin(code)
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Consumer<HttpResponse<String>>() {
                        @Override
                        public void accept(HttpResponse<String> response) throws Exception {
                            mView.closeProgress();
                            if (response.isSuccess()) {
                                mView.onLogin(response.getData());
                            } else if (response.getCode() == 3) {
                                WxLogin wxLogin = new Gson().fromJson(response.getData(), WxLogin.class);
                                mView.onWxBind(wxLogin);
                            }
                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        } else {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .wxCodeLoginDriver(code)
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Consumer<HttpResponse<String>>() {
                        @Override
                        public void accept(HttpResponse<String> response) throws Exception {
                            mView.closeProgress();
                            if (response.isSuccess()) {
                                mView.onLogin(response.getData());
                            } else if (response.getCode() == 3) {
                                WxLogin wxLogin = new Gson().fromJson(response.getData(), WxLogin.class);
                                mView.onWxBind(wxLogin);
                            }
                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }
}

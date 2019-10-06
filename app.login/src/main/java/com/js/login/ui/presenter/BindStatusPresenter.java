package com.js.login.ui.presenter;

import com.base.frame.bean.BaseHttpResponse;
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
import com.js.login.model.bean.BindStatus;
import com.js.login.model.bean.WxLogin;
import com.js.login.ui.presenter.contract.BindStatusContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-10-06.
 */
public class BindStatusPresenter extends RxPresenter<BindStatusContract.View> implements BindStatusContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public BindStatusPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getWxBindInfo() {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .getWxBindInfo()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<BindStatus>() {
                    @Override
                    public void accept(BindStatus bindStatus) throws Exception {
                        mView.closeProgress();
                        mView.onWxBindInfo(bindStatus);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);

    }

    @Override
    public void wxBind(String code) {
        Observable<HttpResponse<WxLogin>> observable;
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            observable = mApiFactory.getApi(WxApi.class).bindWxInfo(code);
        } else {
            observable = mApiFactory.getApi(WxApi.class).bindWxInfoDriver(code);
        }
        Disposable disposable = observable.compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<HttpResponse<WxLogin>>() {
                    @Override
                    public void accept(HttpResponse<WxLogin> response) throws Exception {
                        if (response.isSuccess()) {
                            mView.onWxBind();
                        } else if (response.getCode() == 2) {
                            mView.onRebindWx(response.getData());
                        }

                    }
                },new RxException<>(e->{
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);

    }

    @Override
    public void unBindWx() {
        Observable<HttpResponse<Boolean>> observable;
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            observable = mApiFactory.getApi(WxApi.class).unBindWx();
        } else {
            observable = mApiFactory.getApi(WxApi.class).unBindWxDriver();
        }
        Disposable disposable = observable.compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.closeProgress();
                        mView.onUnBindWx(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);
    }

    @Override
    public void reBindWx(String headimgurl, String nickname, String openid, String unionid) {
        Observable<BaseHttpResponse> observable;
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            observable = mApiFactory.getApi(WxApi.class).reBindWxInfo(headimgurl,nickname,openid,unionid);
        } else {
            observable = mApiFactory.getApi(WxApi.class).reBindWxInfoDriver(headimgurl,nickname,openid,unionid);
        }
        Disposable disposable = observable.compose(RxSchedulers.io_main())
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
                        mView.onRebindWx();
                    }
                },new RxException<>(e->{
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);
    }

}

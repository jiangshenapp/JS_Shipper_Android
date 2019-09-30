package com.js.login.ui.presenter;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.login.LoginApp;
import com.js.login.api.WxApi;
import com.js.login.ui.presenter.contract.WxBindContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-30.
 */
public class WxBindPresenter extends RxPresenter<WxBindContract.View> implements WxBindContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public WxBindPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void wxLogin(String code, String headimgurl, String mobile, String nickname, String openid, String unionid) {
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .wxLogin(code, headimgurl, mobile, nickname, openid, unionid)
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
                            mView.onWxLogin(s);
                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        } else {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .wxLoginDriver(code, headimgurl, mobile, nickname, openid, unionid)
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Consumer<BaseHttpResponse>() {
                        @Override
                        public void accept(BaseHttpResponse baseHttpResponse) throws Exception {

                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }

    @Override
    public void wxReBinding(String headimgurl, String nickname, String openid, String unionid) {
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .reBindWxInfo(headimgurl, nickname, openid, unionid)
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Consumer<BaseHttpResponse>() {
                        @Override
                        public void accept(BaseHttpResponse baseHttpResponse) throws Exception {

                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        } else {
            Disposable disposable = mApiFactory.getApi(WxApi.class)
                    .reBindWxInfoDriver(headimgurl, nickname, openid, unionid)
                    .compose(RxSchedulers.io_main())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Consumer<BaseHttpResponse>() {
                        @Override
                        public void accept(BaseHttpResponse baseHttpResponse) throws Exception {

                        }
                    }, new RxException<>(e -> {
                        mView.closeProgress();
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }
}

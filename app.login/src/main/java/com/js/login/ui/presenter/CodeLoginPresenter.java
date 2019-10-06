package com.js.login.ui.presenter;

import android.text.TextUtils;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;
import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.js.login.LoginApp;
import com.js.login.api.UserApi;
import com.js.login.api.WxApi;
import com.js.login.model.bean.UserInfo;
import com.js.login.model.bean.WxLogin;
import com.js.login.ui.presenter.contract.CodeLoginContract;

import java.io.StringReader;

import javax.inject.Inject;

import io.reactivex.Observable;
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

    @Override
    public void wxBind(String code) {
        Observable<String> observable;
        if ("shipper".equals(LoginApp.getInstance().appType)) {
            observable = mApiFactory.getApi(WxApi.class)
                    .wxCodeLogin(code);
        } else {
            observable = mApiFactory.getApi(WxApi.class)
                    .wxCodeLoginDriver(code);
        }
        Disposable disposable = observable
                .compose(RxSchedulers.io_main())
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
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(s).getAsJsonObject();
                        int code = jsonObject.get("code").getAsInt();
                        if (code == 0) {
                            mView.onLogin(jsonObject.get("data").getAsString());
                        } else if (code == 3) {
                            JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();
                            Gson gson = new Gson();
                            mView.onWxBind(gson.fromJson(jsonObject1, WxLogin.class));
                        }
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

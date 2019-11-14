package com.js.login.ui.presenter;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.login.api.UserApi;
import com.js.login.ui.presenter.contract.ResetPwdContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/02
 * desc   :
 * version: 3.0.0
 */
public class ResetPwdPresenter extends RxPresenter<ResetPwdContract.View> implements ResetPwdContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public ResetPwdPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void resetPwd(String phone, String pwd) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .resetPwdStep2(phone, pwd)
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
                            mView.onResetPwd();
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
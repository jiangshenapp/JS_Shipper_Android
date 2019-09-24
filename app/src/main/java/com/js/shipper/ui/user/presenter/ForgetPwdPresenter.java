package com.js.shipper.ui.user.presenter;

import com.js.shipper.api.UserApi;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.user.presenter.contract.ForgetPwdContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.bean.BaseHttpResponse;

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
public class ForgetPwdPresenter extends RxPresenter<ForgetPwdContract.View> implements ForgetPwdContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public ForgetPwdPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void forgetPwd(String phone, String code) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .resetPwdStep1(phone, code)
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
                            mView.onForgetPwd();
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

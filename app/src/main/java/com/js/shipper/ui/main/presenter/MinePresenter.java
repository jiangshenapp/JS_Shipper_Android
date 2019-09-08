package com.js.shipper.ui.main.presenter;

import com.js.shipper.api.PayApi;
import com.js.shipper.api.UserApi;
import com.js.shipper.model.bean.AccountInfo;
import com.js.shipper.model.bean.UserInfo;
import com.js.shipper.ui.main.presenter.contract.MineContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/1.
 */
public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public MinePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
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
                        mView.finishRefresh();
                        mView.onUserInfo(userInfo);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void getAccountInfo() {
        Disposable disposable = mApiFactory.getApi(PayApi.class)
                .getBySubscriber()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<AccountInfo>() {
                    @Override
                    public void accept(AccountInfo accountInfo) throws Exception {
                        mView.finishRefresh();
                        mView.onAccountInfo(accountInfo);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

package com.js.driver.ui.wallet.presenter;

import android.text.TextUtils;

import com.js.driver.App;
import com.js.driver.api.PayApi;
import com.js.driver.model.bean.AccountInfo;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.driver.ui.wallet.presenter.contract.BailContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class BailPresenter extends RxPresenter<BailContract.View> implements BailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public BailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getAccountInfo() {
        if (!TextUtils.isEmpty(App.getInstance().token)) {
            Disposable disposable = mApiFactory.getApi(PayApi.class)
                    .getBySubscriber()
                    .compose(RxSchedulers.io_main())
                    .compose(RxResult.handleResult())
                    .subscribe(new Consumer<AccountInfo>() {
                        @Override
                        public void accept(AccountInfo accountInfo) throws Exception {
                            mView.onAccountInfo(accountInfo);
                        }
                    }, new RxException<>(e -> {
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }
}
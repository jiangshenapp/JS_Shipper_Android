package com.js.shipper.ui.wallet.presenter;

import android.text.TextUtils;

import com.js.http.ApiFactory;
import com.js.shipper.App;
import com.js.shipper.api.PayApi;
import com.js.shipper.model.bean.AccountInfo;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.wallet.presenter.contract.WalletContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class WalletPresenter extends RxPresenter<WalletContract.View> implements WalletContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public WalletPresenter(ApiFactory apiFactory) {
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
                            mView.finishRefresh();
                            mView.onAccountInfo(accountInfo);
                        }
                    }, new RxException<>(e -> {
                        mView.toast(e.getMessage());
                    }));
            addDispose(disposable);
        }
    }
}

package com.js.driver.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.driver.api.MessageApi;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.bean.PushBean;
import com.js.driver.model.response.ListResponse;
import com.js.driver.ui.message.presenter.contract.PushContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/07
 * desc   :
 * version: 3.0.0
 */
public class PushPresenter extends RxPresenter<PushContract.View> implements PushContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public PushPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getPushMessage(int pushSide) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).getPushMessage(pushSide)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<PushBean>>() {
                    @Override
                    public void accept(List<PushBean> response) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onPushMessage(response);
                    }
                },new RxException<>(e->{
                    mView.finishRefreshAndLoadMore();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

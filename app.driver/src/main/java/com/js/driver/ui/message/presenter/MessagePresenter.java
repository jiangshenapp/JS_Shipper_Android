package com.js.driver.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.driver.api.MessageApi;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.response.ListResponse;
import com.js.driver.ui.message.presenter.contract.MessageContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public MessagePresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getMessage(int type, int current, int size) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).getMessage(type, current,
                size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<MessageBean>>() {
                    @Override
                    public void accept(ListResponse<MessageBean> response) throws Exception {

                        mView.finishRefreshAndLoadMore();
                        mView.onMessage(response);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

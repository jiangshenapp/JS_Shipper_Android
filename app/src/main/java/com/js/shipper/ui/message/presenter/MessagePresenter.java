package com.js.shipper.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.api.MessageApi;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.message.presenter.contract.MessageContract;

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

    @Override
    public void readMessage(long id) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).readMessage(id)
                .compose(RxSchedulers.io_main())
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
                        mView.onReadMessage(aBoolean);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void readAllMessage() {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).readAllMessage()
                .compose(RxSchedulers.io_main())
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
                        mView.onReadAllMessage(aBoolean);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

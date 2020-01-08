package com.js.shipper.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.shipper.api.MessageApi;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.bean.PushBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.message.presenter.contract.PushContract;

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

    @Override
    public void readPushLog(long id) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).readPushLog(id)
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
                        mView.onReadPushLog(aBoolean);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void readAllPushLog() {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).readAllPushLog()
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
                        mView.onReadAllPushLog(aBoolean);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

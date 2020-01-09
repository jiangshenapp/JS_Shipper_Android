package com.js.message.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.message.api.MessageApi;
import com.js.message.ui.presenter.contract.InformationContract;
import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/1.
 */
public class InformationPresenter extends RxPresenter<InformationContract.View> implements InformationContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public InformationPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getUnreadMessageCount(int pushSide) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).getUnreadMessageCount(pushSide)
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
                    public void accept(String result) throws Exception {
                        mView.closeProgress();
                        mView.onGetUnreadMessageCount(result);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void getUnreadPushLogCount(int pushSide) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class).getUnreadPushLogCount(pushSide)
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
                    public void accept(String result) throws Exception {
                        mView.closeProgress();
                        mView.onGetUnreadPushLogCount(result);
                    }
                },new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

package com.js.shipper.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.api.LineApi;
import com.js.shipper.api.MessageApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.ui.message.presenter.contract.MessageDetailContract;

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
public class MessageDetailPresenter extends RxPresenter<MessageDetailContract.View> implements MessageDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public MessageDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getMessageDetail(long id) {
        Disposable disposable = mApiFactory.getApi(MessageApi.class)
                .getMessageDetail(id)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<MessageBean>() {
                    @Override
                    public void accept(MessageBean messageBean) throws Exception {
                        mView.closeProgress();
                        mView.onMessageDetail(messageBean);
                    }
                },new RxException<>(e->{
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}

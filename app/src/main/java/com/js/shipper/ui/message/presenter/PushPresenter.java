package com.js.shipper.ui.message.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.ApiFactory;
import com.js.shipper.api.MessageApi;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.message.presenter.contract.PushContract;

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

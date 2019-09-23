package com.js.shipper.ui.park.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.CollectApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.park.presenter.contract.CollectCarSourceContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class CollectCarSourcePresenter extends RxPresenter<CollectCarSourceContract.View> implements CollectCarSourceContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public CollectCarSourcePresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCollectLines(int current, int size) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class).getLines(current, size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<LineBean>>() {
                    @Override
                    public void accept(ListResponse<LineBean> response) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onCollectLines(response);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

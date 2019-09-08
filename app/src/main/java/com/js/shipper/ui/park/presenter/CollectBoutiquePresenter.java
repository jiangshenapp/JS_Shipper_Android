package com.js.shipper.ui.park.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.CollectApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.park.presenter.contract.CollectBoutiqueContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class CollectBoutiquePresenter extends RxPresenter<CollectBoutiqueContract.View> implements CollectBoutiqueContract.Presenter {

    private ApiFactory mApiFactory;
    @Inject
    public CollectBoutiquePresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCollectClassics(int current, int size) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class).getClassicLines(current, size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<LineBean>>() {
                    @Override
                    public void accept(ListResponse<LineBean> response) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onCollectClassics(response);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

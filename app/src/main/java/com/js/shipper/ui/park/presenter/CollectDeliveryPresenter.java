package com.js.shipper.ui.park.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.CollectApi;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.response.ListResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.park.presenter.contract.CollectDeliveryContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class CollectDeliveryPresenter extends RxPresenter<CollectDeliveryContract.View> implements CollectDeliveryContract.Presenter {


    private ApiFactory mApiFactory;


    @Inject
    public CollectDeliveryPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }



    @Override
    public void getCollectBranchs(int current, int size) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class)
                .getCollectParks(current, size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<ParkBean>>() {
                    @Override
                    public void accept(ListResponse<ParkBean> listResponse) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onCollectBranchs(listResponse);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefreshAndLoadMore();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

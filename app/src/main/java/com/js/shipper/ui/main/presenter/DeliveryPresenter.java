package com.js.shipper.ui.main.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.ParkApi;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.ParkList;
import com.js.shipper.model.response.ListResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.main.presenter.contract.DeliveryContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class DeliveryPresenter extends RxPresenter<DeliveryContract.View> implements DeliveryContract.Presenter {


    private ApiFactory mApiFactory;


    @Inject
    public DeliveryPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getParkList(int current, int size, ParkList parkList) {
        Disposable disposable = mApiFactory.getApi(ParkApi.class)
                .getParks(current, parkList, size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<ParkBean>>() {
                    @Override
                    public void accept(ListResponse<ParkBean> listResponse) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onParkList(listResponse);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefreshAndLoadMore();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

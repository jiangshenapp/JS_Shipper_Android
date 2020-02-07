package com.js.shipper.ui.order.presenter;

import com.js.shipper.api.OrderApi;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.request.OrderList;
import com.js.shipper.model.response.ListResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.OrderContract;
import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderPresenter extends RxPresenter<OrderContract.View> implements OrderContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public OrderPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getOrderList(String state, int current, int size) {
        Disposable disposable = mApiFactory.getApi(OrderApi.class).getOrderList(current, new OrderList(state), size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<OrderBean>>() {
                    @Override
                    public void accept(ListResponse<OrderBean> orderBeanListResponse) throws Exception {
                        mView.finishRefreshAndLoadMore();
                        mView.onOrderList(orderBeanListResponse);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

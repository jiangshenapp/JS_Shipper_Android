package com.js.shipper.ui.main.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.LineApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.response.ListResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.main.presenter.contract.BoutiqueContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/30.
 */
public class BoutiquePresenter extends RxPresenter<BoutiqueContract.View> implements BoutiqueContract.Presenter {

    private ApiFactory mApiFactory;
    @Inject
    public BoutiquePresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getClassicLine(int current, LineAppFind lineAppFind, int size) {
        Disposable disposable = mApiFactory.getApi(LineApi.class).getClassicLine(current,
                lineAppFind,
                size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<LineBean>>() {
                    @Override
                    public void accept(ListResponse<LineBean> response) throws Exception {

                        mView.finishRefreshAndLoadMore();
                        mView.onClassicLine(response);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

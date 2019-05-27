package com.js.shipper.ui.main.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.shipper.App;
import com.js.shipper.api.LineApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.LineClassic;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
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
    public void getClassicLine(int current, String arriveAddress, String startAddress, int size) {
        Disposable disposable = mApiFactory.getApi(LineApi.class).getClassicLine(current,
                new LineClassic(arriveAddress,startAddress),
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

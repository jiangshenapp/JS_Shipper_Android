package com.js.shipper.ui.park.presenter;

import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.shipper.api.CollectApi;
import com.js.shipper.api.LineApi;
import com.js.shipper.api.ParkApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.CollectLine;
import com.js.shipper.model.request.CollectPark;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.park.presenter.contract.BoutiqueDetailContract;
import com.js.shipper.ui.park.presenter.contract.BranchDetailContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-16.
 */
public class BranchDetailPresenter extends RxPresenter<BranchDetailContract.View> implements BranchDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public BranchDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getBranchDetail(long id) {
        Disposable disposable = mApiFactory.getApi(ParkApi.class)
                .getParkInfo(id)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<ParkBean>() {
                    @Override
                    public void accept(ParkBean parkBean) throws Exception {
                        mView.closeProgress();
                        mView.onBranchDetail(parkBean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void addCollect(CollectPark collectPark) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class)
                .addParkCollect(collectPark)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.closeProgress();
                        mView.onAddCollect(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void removeCollect(CollectPark collectPark) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class)
                .removeParkCollect(collectPark)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.closeProgress();
                        mView.onRemoveCollect(aBoolean);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }
}


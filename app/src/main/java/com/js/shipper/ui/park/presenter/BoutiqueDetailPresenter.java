package com.js.shipper.ui.park.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.CollectApi;
import com.js.shipper.api.LineApi;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.CollectLine;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.park.presenter.contract.BoutiqueDetailContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-16.
 */
public class BoutiqueDetailPresenter extends RxPresenter<BoutiqueDetailContract.View> implements BoutiqueDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public BoutiqueDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getLineDetail(long id) {
        Disposable disposable = mApiFactory.getApi(LineApi.class)
                .getLineDetail(id)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<LineBean>() {
                    @Override
                    public void accept(LineBean lineBean) throws Exception {
                        mView.closeProgress();
                        mView.onLineDetail(lineBean);
                    }
                },new RxException<>(e->{
                    mView.closeProgress();
                }));
        addDispose(disposable);
    }

    @Override
    public void addCollect(CollectLine collectLine) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class)
                .addLineCollect(collectLine)
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
    public void removeCollect(CollectLine collectLine) {
        Disposable disposable = mApiFactory.getApi(CollectApi.class)
                .removeCollectLine(collectLine)
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


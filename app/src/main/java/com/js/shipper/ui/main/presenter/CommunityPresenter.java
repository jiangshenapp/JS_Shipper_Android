package com.js.shipper.ui.main.presenter;

import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.model.bean.CircleBean;
import com.js.shipper.api.CircleApi;
import com.js.shipper.ui.main.presenter.contract.CommunityContract;
import com.base.frame.mvp.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/1.
 */
public class CommunityPresenter extends RxPresenter<CommunityContract.View> implements CommunityContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public CommunityPresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCircles() {
        Disposable disposable = mApiFactory.getApi(CircleApi.class)
                .getCircles()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<CircleBean>>() {
                    @Override
                    public void accept(List<CircleBean> circleBeans) throws Exception {
                        mView.finishRefresh();
                        mView.onCircles(circleBeans);
                    }
                },new RxException<>(e->{
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

package com.js.shipper.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.DictApi;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.presenter.contract.DictContract;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-06-09.
 */
public class DictPresenter extends RxPresenter<DictContract.View> implements DictContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public DictPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getDictByType(String type) {
        Disposable disposable = mApiFactory.getApi(DictApi.class)
                .getDictList(type)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<DictBean>>() {
                    @Override
                    public void accept(List<DictBean> dictBeans) throws Exception {
                        mView.onDictByType(type,dictBeans);
                    }
                }, new RxException<>(e -> {

                }));
        addDispose(disposable);
    }
}

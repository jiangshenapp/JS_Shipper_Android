package com.js.shipper.ui.order.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.js.shipper.api.DictApi;
import com.js.shipper.model.bean.DictBean;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.js.shipper.ui.order.presenter.contract.TypeInputContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/29.
 */
public class TypeInputPresenter extends RxPresenter<TypeInputContract.View> implements TypeInputContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public TypeInputPresenter(ApiFactory apiFactory) {
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

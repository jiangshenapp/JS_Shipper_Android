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
import com.js.shipper.ui.center.presenter.contract.CarsContract;
import com.js.shipper.ui.main.presenter.contract.CarSourceContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by huyg on 2019/4/30.
 */
public class CarSourcePresenter extends RxPresenter<CarSourceContract.View> implements CarSourceContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public CarSourcePresenter(ApiFactory apiFactory){
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getCarSource(int current, String arriveAddress, String startAddress, int size) {
        Disposable disposable = mApiFactory.getApi(LineApi.class).getCarLine(current,
                new LineClassic(arriveAddress,startAddress),
                size)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<ListResponse<LineBean>>() {
                    @Override
                    public void accept(ListResponse<LineBean> response) throws Exception {

                        mView.finishRefreshAndLoadMore();
                        mView.onCarSource(response);
                    }
                },new RxException<>(e->{
                    mView.toast(e.getMessage());
                    mView.finishRefreshAndLoadMore();
                }));
        addDispose(disposable);
    }
}

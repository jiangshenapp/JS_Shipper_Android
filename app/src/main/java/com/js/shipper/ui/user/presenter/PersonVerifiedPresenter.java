package com.js.shipper.ui.user.presenter;

import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.js.shipper.api.UserApi;
import com.js.shipper.model.bean.AuthInfo;
import com.js.shipper.model.request.PersonVerifiedRequest;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.shipper.ui.user.presenter.contract.PersonVerifiedContract;
import com.base.frame.mvp.RxPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class PersonVerifiedPresenter extends RxPresenter<PersonVerifiedContract.View>  implements PersonVerifiedContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public PersonVerifiedPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getPersonVerifiedInfo() {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .getPersonVerifiedInfo()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<AuthInfo>() {
                    @Override
                    public void accept(AuthInfo authInfo) throws Exception {
                        mView.onPersonVerifiedInfo(authInfo);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void submitPersonVerified(String idImage, String idBackImage, String idHandImage, String personName, String idCode, String address) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .personVerified(new PersonVerifiedRequest(idImage, idBackImage, idHandImage, personName, idCode, address))
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        mView.closeProgress();
                        if (response.isSuccess()){
                            mView.onSubmitPersonVerified();
                        } else {
                            mView.toast(response.getMsg());
                        }
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

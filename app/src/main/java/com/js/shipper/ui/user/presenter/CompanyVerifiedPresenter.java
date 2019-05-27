package com.js.shipper.ui.user.presenter;

import com.js.http.ApiFactory;
import com.js.http.BaseHttpResponse;
import com.js.shipper.api.UserApi;
import com.js.shipper.model.bean.AuthInfo;
import com.js.shipper.model.request.CompanyVerifiedRequest;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;
import com.js.shipper.ui.user.presenter.contract.CompanyVerifiedContract;
import com.js.frame.mvp.RxPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019/4/24.
 */
public class CompanyVerifiedPresenter extends RxPresenter<CompanyVerifiedContract.View>  implements CompanyVerifiedContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public CompanyVerifiedPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getCompanyVerifiedInfo() {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .getCompanyVerifiedInfo()
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<AuthInfo>() {
                    @Override
                    public void accept(AuthInfo authInfo) throws Exception {
                        mView.onCompanyVerifiedInfo(authInfo);
                    }
                }, new RxException<>(e -> {
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void submitCompanyVerified(String companyName, String registrationNumber, String address, String detailAddress, String businessLicenceImage) {
        Disposable disposable = mApiFactory.getApi(UserApi.class)
                .companyVerified(new CompanyVerifiedRequest(companyName, registrationNumber, address, detailAddress, businessLicenceImage))
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
                            mView.onSubmitCompanyVerified();
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

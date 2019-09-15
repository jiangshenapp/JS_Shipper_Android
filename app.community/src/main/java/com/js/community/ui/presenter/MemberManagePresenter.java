package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.api.CircleApi;
import com.js.community.api.PostApi;
import com.js.community.model.bean.Member;
import com.js.community.ui.presenter.contract.MemberManageContract;
import com.js.community.ui.presenter.contract.PostDetailContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-11.
 */
public class MemberManagePresenter extends RxPresenter<MemberManageContract.View> implements MemberManageContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public MemberManagePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getMembers(long circleId) {
        Disposable disposable = mApiFactory.getApi(CircleApi.class)
                .getCircleMembers(circleId)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<Member>>() {
                    @Override
                    public void accept(List<Member> members) throws Exception {
                        mView.finishRefresh();
                        mView.onMembers(members);
                    }
                },new RxException<>(e->{
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

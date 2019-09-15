package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.api.PostApi;
import com.js.community.model.bean.PostBean;
import com.js.community.ui.presenter.contract.CircleIndexContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-09.
 */
public class CircleIndexPresenter extends RxPresenter<CircleIndexContract.View> implements CircleIndexContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CircleIndexPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getPosts(long circleId, String subject) {
        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .getPostList(circleId, subject)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<List<PostBean>>() {
                    @Override
                    public void accept(List<PostBean> postBeans) throws Exception {
                        mView.closeProgress();
                        mView.onPosts(postBeans);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

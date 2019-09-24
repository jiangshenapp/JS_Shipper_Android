package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxSchedulers;
import com.js.community.api.PostApi;
import com.js.community.ui.presenter.contract.CommentContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-19.
 */
public class CommentPresenter extends RxPresenter<CommentContract.View> implements CommentContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public CommentPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void comment(long postId, String comment) {
        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .commentPost(comment,postId)
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
                            mView.onComment();
                        }else {
                            mView.toast(response.getMsg());
                        }
                    }
                },new RxException<>(e->{
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

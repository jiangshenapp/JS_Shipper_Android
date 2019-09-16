package com.js.community.ui.presenter;

import androidx.annotation.UiThread;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.api.PostApi;
import com.js.community.model.bean.Comment;
import com.js.community.ui.presenter.contract.PostDetailContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostDetailPresenter extends RxPresenter<PostDetailContract.View> implements PostDetailContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public PostDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getCommentList(long postId) {
        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .getComments(postId)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Exception {
                        mView.closeProgress();
                        mView.onCommentList(comments);
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void likePost(long postId) {
        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .likePost(postId)
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
                            mView.onLikePost();
                        }else {
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

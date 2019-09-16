package com.js.community.ui.presenter;

import android.text.TextUtils;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.api.PostApi;
import com.js.community.model.bean.PostBean;
import com.js.community.ui.presenter.contract.PostContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2019-09-16.
 */
public class PostPresenter extends RxPresenter<PostContract.View> implements PostContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public PostPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getPosts(long circleId, String subject, boolean commentFlag, boolean likeFlag, boolean myFlag) {

        Map<String, Object> params = new HashMap<>();
        if (circleId != -1) {
            params.put("circleId", circleId);
        }
        if (!TextUtils.isEmpty(subject)) {
            params.put("subject", subject);
        }
        params.put("commentFlag", commentFlag);
        params.put("likeFlag", likeFlag);
        params.put("myFlag", myFlag);

        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .getPostList(params)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<List<PostBean>>() {
                    @Override
                    public void accept(List<PostBean> postBeans) throws Exception {
                        mView.finishRefresh();
                        mView.onPosts(postBeans);
                    }
                }, new RxException<>(e -> {
                    mView.finishRefresh();
                    mView.toast(e.getMessage());
                }));
        addDispose(disposable);
    }
}

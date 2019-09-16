package com.js.community.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.http.BaseHttpResponse;
import com.base.http.rx.RxException;
import com.base.http.rx.RxResult;
import com.base.http.rx.RxSchedulers;
import com.js.community.api.PostApi;
import com.js.community.ui.presenter.contract.PublishPostContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-11.
 */
public class PublishPostPresenter extends RxPresenter<PublishPostContract.View> implements PublishPostContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public PublishPostPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void addPost(long circleId, String content, String image, String subject) {
        Disposable disposable = mApiFactory.getApi(PostApi.class)
                .addPost(circleId, content, image, subject)
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
                        if (response.isSuccess()) {
                            mView.onAddPost();
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

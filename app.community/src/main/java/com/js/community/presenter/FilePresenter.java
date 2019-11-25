package com.js.community.presenter;

import com.base.frame.mvp.RxPresenter;
import com.base.http.ApiFactory;
import com.base.frame.rx.RxException;
import com.base.frame.rx.RxResult;
import com.base.frame.rx.RxSchedulers;
import com.base.http.global.Const;
import com.js.community.api.FileApi;
import com.js.community.presenter.contract.FileContract;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by huyg on 2019/4/26.
 */
public class FilePresenter extends RxPresenter<FileContract.View> implements FileContract.Presenter {

    ApiFactory mApiFactory;

    @Inject
    public FilePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void uploadFile(File file) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody resourceId = RequestBody.create(MediaType.parse("multipart/form-data"), "pigx");
        Disposable disposable = mApiFactory.getApi(FileApi.class).upload(Const.UPLOAD_URL(),resourceId,part)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.onUploadFile(s);
                        mView.closeProgress();
                    }
                }, new RxException<>(e -> {
                    mView.closeProgress();
                    mView.toast(e.getMessage());
                }));

        addDispose(disposable);
    }
}

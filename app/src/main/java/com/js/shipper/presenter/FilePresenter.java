package com.js.shipper.presenter;

import com.js.shipper.App;
import com.js.shipper.api.FileApi;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.presenter.contract.FileContract;
import com.js.frame.mvp.RxPresenter;
import com.js.http.ApiFactory;
import com.js.shipper.rx.RxException;
import com.js.shipper.rx.RxResult;
import com.js.shipper.rx.RxSchedulers;

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
        Disposable disposable = mApiFactory.getApi(FileApi.class).upload(resourceId,part)
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

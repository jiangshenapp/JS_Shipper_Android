package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.base.http.global.Const;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by huyg on 2019/4/26.
 */
public interface FileApi {


    @Multipart
    @POST
    Observable<HttpResponse<String>> upload(@Url String url,
                                            @Part("resourceId") RequestBody body,
                                            @Part MultipartBody.Part file);


}

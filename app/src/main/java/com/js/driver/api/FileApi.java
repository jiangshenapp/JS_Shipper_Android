package com.js.driver.api;

import com.xlgcx.http.BaseHttpResponse;
import com.xlgcx.http.HttpResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by huyg on 2019/4/26.
 */
public interface FileApi {


    @Multipart
    @POST("http://47.96.122.74:9999/admin/file/upload")
    Observable<HttpResponse<String>> upload(@Part("resourceId")RequestBody body,
                                    @Part MultipartBody.Part file);


}

package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.bean.DictBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-05-28.
 */
public interface DictApi {

    @POST("app/dict/getDictByType")
    Observable<HttpResponse<List<DictBean>>> getDictList(@Query("type") String type);

    @POST("app/dict/getFirstDictByType")
    Observable<HttpResponse<DictBean>> getFirstDict(@Query("type") String type);
}

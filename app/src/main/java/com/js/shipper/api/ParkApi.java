package com.js.shipper.api;

import com.js.http.HttpResponse;
import com.js.shipper.di.FragmentScope;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-05-21.
 */
public interface ParkApi {


    /**
     * 园区详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/park/get")
    Observable<HttpResponse<ParkBean>> getParkInfo(@Field("id") long id);


    @FormUrlEncoded
    @POST("app/park/list")
    Observable<HttpResponse<ListResponse<ParkBean>>> getParks(@Field("current") int current,
                                                              @Field("parkAppFindListDTO") String data,
                                                              @Field("size") int size);
}

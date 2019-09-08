package com.js.shipper.api;

import com.base.http.HttpResponse;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.ParkList;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-05-21.
 */
public interface ParkApi {


    /**
     * 园区详情
     * @param id
     * @return
     */
    @POST("app/park/get")
    Observable<HttpResponse<ParkBean>> getParkInfo(@Query("id") long id);


    /**
     * 找城市配送
     * @param current
     * @param size
     * @return
     */
    @POST("app/park/list")
    Observable<HttpResponse<ListResponse<ParkBean>>> getParks(@Query("current") int current,
                                                              @Body ParkList parkList,
                                                              @Query("size") int size);
}

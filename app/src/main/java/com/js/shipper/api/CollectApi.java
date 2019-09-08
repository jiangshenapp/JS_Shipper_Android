package com.js.shipper.api;

import com.base.http.HttpResponse;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.CollectLine;
import com.js.shipper.model.request.CollectPark;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-06-16.
 */
public interface CollectApi {


    /**
     * 线路收藏
     * @param collectLine
     * @return
     */
    @POST("app/collect/line/add")
    Observable<HttpResponse<Boolean>> addLineCollect(@Body CollectLine collectLine);


    /**
     * 我的精品线路收藏
     * @param current
     * @param size
     * @return
     */
    @POST("app/collect/line/classicList")
    Observable<HttpResponse<ListResponse<LineBean>>> getClassicLines(@Query("current") int current,
                                                                     @Query("size") int size);


    /**
     * 我的线路收藏
     * @param current
     * @param size
     * @return
     */
    @POST("app/collect/line/list")
    Observable<HttpResponse<ListResponse<LineBean>>> getLines(@Query("current") int current,
                                                              @Query("size") int size);

    /**
     *  取消线路收藏
     * @param collectLine
     * @return
     */
    @POST("app/collect/line/remove")
    Observable<HttpResponse<Boolean>> removeCollectLine(@Body CollectLine collectLine);


    /**
     * 添加园区收藏
     * @param collectPark
     * @return
     */
    @POST("app/collect/park/add")
    Observable<HttpResponse<Boolean>> addParkCollect(@Body CollectPark collectPark);


    /**
     * 取消园区收藏
     * @param collectPark
     * @return
     */
    @POST("app/collect/park/remove")
    Observable<HttpResponse<Boolean>> removeParkCollect(@Body CollectPark collectPark);


    /**
     * 我园区收藏
     * @param current
     * @param size
     * @return
     */
    @POST("app/collect/park/list")
    Observable<HttpResponse<ListResponse<ParkBean>>> getCollectParks(@Query("current") int current,
                                                                     @Query("size") int size);

}

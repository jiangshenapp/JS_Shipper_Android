package com.js.driver.api;

import com.js.driver.model.bean.OrderBean;
import com.js.driver.model.request.LineAppFind;
import com.js.driver.model.request.OrderComment;
import com.js.driver.model.request.OrderDistribution;
import com.js.driver.model.request.OrderStatus;
import com.js.driver.model.response.ListResponse;
import com.js.http.BaseHttpResponse;
import com.js.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-05-30.
 */
public interface OrderApi {


    /**
     * 拒绝配送
     *
     * @param id 订单id
     * @return
     */
    @POST("app/driver/order/cancelDistribution/{id}")
    Observable<HttpResponse<Boolean>> cancelDistribution(@Path("id") long id);


    /**
     * 取消接货
     */
    @POST("/app/driver/order/cancelReceive/{id}")
    Observable<HttpResponse<Boolean>> cancelReceive(@Path("id") long id);


    /**
     * 回执评价
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/comment/{id}")
    Observable<BaseHttpResponse> comment(@Path("id") long id);


    /**
     * 完成配送
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/completeDistribution/{id}")
    Observable<HttpResponse<Boolean>> completeDistribution(@Path("id") long id);


    /**
     * 等待货主接货
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/consignorReceive/{id}")
    Observable<BaseHttpResponse> consignorReceive(@Path("id") long id);


    /**
     * 开始配送
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/distribution/{id}")
    Observable<HttpResponse<Boolean>> distribution(@Path("id") long id,
                                                   @Body OrderDistribution orderDistribution);


    /**
     * 找货 所有待分配订单
     *
     * @param current
     * @param size
     * @param findOrder
     * @return
     */
    @POST("app/driver/order/find")
    Observable<HttpResponse<ListResponse<OrderBean>>> find(@Query("current") int current,
                                                           @Query("size") int size,
                                                           @Body LineAppFind lineAppFind);


    /**
     * 我的运单
     *
     * @param current
     * @param size
     * @param orderStatus
     * @return
     */
    @POST("app/driver/order/list")
    Observable<HttpResponse<ListResponse<OrderBean>>> getOrderList(@Query("current") int current,
                                                                   @Query("size") int size,
                                                                   @Body OrderStatus orderStatus);


    /**
     * 接单
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/receive/{id}")
    Observable<HttpResponse<Boolean>> receive(@Path("id") long id);


    /**
     * 拒绝接单
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/refuse/{id}")
    Observable<HttpResponse<Boolean>> refuse(@Path("id") long id);


    /**
     * 获取订单详情
     *
     * @param id
     * @return
     */
    @POST("app/order/get/{id}")
    Observable<HttpResponse<OrderBean>> getOrderDetail(@Path("id") long id);


    /**
     * 取消确认
     *
     * @param id
     * @return
     */
    @POST("app/driver/order/cancelConfirm/{id}")
    Observable<HttpResponse<Boolean>> cancelConfirmOrder(@Path("id") long id);


    @POST("app/driver/order/confirm/{id}")
    Observable<HttpResponse<Boolean>> confirmOrder(@Path("id") long id);


    //回执
    @POST("app/driver/order/comment")
    Observable<HttpResponse<Boolean>> commentOrder(@Body OrderComment orderComment);


}

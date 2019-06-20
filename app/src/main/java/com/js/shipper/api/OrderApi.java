package com.js.shipper.api;

import com.js.http.BaseHttpResponse;
import com.js.http.HttpResponse;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.request.AddOrder;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.model.request.AddStepTwo;
import com.js.shipper.model.request.OrderEdit;
import com.js.shipper.model.request.OrderList;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-05-21.
 * 订单相关接口
 */
public interface OrderApi {

    /**
     * 发货 --创建订单
     * @param data
     * @return
     */
    @POST("app/order/addStepOne")
    Observable<HttpResponse<Long>> addStepOne(@Body AddStepOne data);


    /**
     * 发货 --确认订单
     * @param data
     * @return
     */
    @POST("app/order/addStepTwo")
    Observable<HttpResponse<Boolean>> addStepTwo(@Body AddStepTwo data);


    /**
     * 重新发货
     * @param id
     * @return
     */
    @POST("app/order/again/{id}")
    Observable<HttpResponse<Boolean>> againOrder(@Path("id") long id);


    /**
     * 取消订单
     * @param id
     * @return
     */
    @POST("app/order/cancel/{id}")
    Observable<HttpResponse<Boolean>> cancelOrder(@Path("id") long id);


    /**
     * 获取订单详情
     * @param id
     * @return
     */
    @POST("app/order/get/{id}")
    Observable<HttpResponse<OrderBean>> getOrderDetail(@Path("id") long id);



    @POST("app/order/list")
    Observable<HttpResponse<ListResponse<OrderBean>>> getOrderList(@Query("current") int current,
                                                                   @Body OrderList data,
                                                                   @Query("size") int size);




    @POST("app/order/edit/{id}")
    Observable<HttpResponse<Boolean>> editOrder(@Body OrderEdit orderEdit,
                                                @Path("id") long id);



    @POST("app/order/addOrder")
    Observable<HttpResponse<Boolean>> submitOrder(@Body AddOrder addOrder);


    @POST("app/order/confirm/{id}")
    Observable<HttpResponse<Boolean>> confirmOrder(@Path("id") long id);

}

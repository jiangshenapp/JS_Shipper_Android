package com.js.shipper.api;

import com.js.http.BaseHttpResponse;
import com.js.http.HttpResponse;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @FormUrlEncoded
    @POST("app/order/addStepOne")
    Observable<BaseHttpResponse> addStepOne(@Field("orderAppAddStepOneDTO") String data);


    /**
     * 发货 --确认订单
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("app/order/addStepTwo")
    Observable<BaseHttpResponse> addStepTwo(@Field("orderAppAddStepTwoDTO") String data);


    /**
     * 订单详情
     * @param id
     * @return
     */
    @GET("app/order/get/{id}")
    Observable<HttpResponse<OrderBean>> getOrderDetail(@Query("id") long id);

    /**
     * 我的运单
     * @param current
     * @param data
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("app/order/list")
    Observable<ListResponse<OrderBean>> getOrders(@Field("current") int current,
                                                  @Field("orderAppMyListDTO") String data,
                                                  @Field("size") int size);



}

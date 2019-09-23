package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public interface MessageApi {


    /**
     * 消息列表
     * @param type 类型 1所有 2司机 3货主
     * @param current
     * @param size
     * @return
     */
    @POST("app/message/page/{type}")
    Observable<HttpResponse<ListResponse<MessageBean>>> getMessage(@Path("type") long type,
                                                                   @Query("current") int current,
                                                                   @Query("size") int size);


    /**
     * 消息详情
     */
    @GET("app/message/{id}")
    Observable<HttpResponse<MessageBean>> getMessageDetail(@Path("id") long id);
}

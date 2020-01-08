package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.bean.PushBean;
import com.js.shipper.model.response.ListResponse;

import java.util.List;

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
     *
     * @param type    类型 1所有 2司机 3货主
     * @param current the current
     * @param size    the size
     * @return message message
     */
    @POST("app/message/page/{type}")
    Observable<HttpResponse<ListResponse<MessageBean>>> getMessage(@Path("type") long type,
                                                                   @Query("current") int current,
                                                                   @Query("size") int size);


    /**
     * 推送通知列表
     *
     * @param pushSide 推送来源，1运力，2货主
     * @return push message
     */
    @GET("app/message/getPushLog")
    Observable<HttpResponse<List<PushBean>>> getPushMessage(@Query("pushSide") int pushSide);


    /**
     * 消息详情
     *
     * @param id the id
     * @return the message detail
     */
    @GET("app/message/{id}")
    Observable<HttpResponse<MessageBean>> getMessageDetail(@Path("id") long id);


    /**
     * 标记推送消息为已读
     *
     * @param id the id
     * @param pushSide 推送来源，1运力，2货主
     * @return the observable
     */
    @POST("app/message/readPushLog")
    Observable<HttpResponse<Boolean>> readPushLog(@Query("id") long id,
                                                  @Query("pushSide") int pushSide);


    /**
     * 标记全部推送消息为已读
     *
     * @param pushSide 推送来源，1运力，2货主
     * @return the observable
     */
    @POST("app/message/readAllPushLog")
    Observable<HttpResponse<Boolean>> readAllPushLog(@Query("pushSide") int pushSide);


    /**
     * 标记系统消息为已读
     *
     * @param id the id
     * @param pushSide 推送来源，1运力，2货主
     * @return the observable
     */
    @POST("app/message/readMessage")
    Observable<HttpResponse<Boolean>> readMessage(@Query("messageId") long id,
                                                  @Query("pushSide") int pushSide);


    /**
     * 标记全部系统消息为已读
     *
     * @param pushSide 推送来源，1运力，2货主
     * @return the observable
     */
    @POST("app/message/readAllMessage")
    Observable<HttpResponse<Boolean>> readAllMessage(@Query("pushSide") int pushSide);


    /**
     * 获取系统消息未读数
     *
     * @param pushSide 推送来源，1运力，2货主
     * @return the unread message count
     */
    @GET("app/message/getUnreadMessageCount")
    Observable<HttpResponse<String>> getUnreadMessageCount(@Query("pushSide") int pushSide);


    /**
     * 获取推送消息未读数
     *
     * @param pushSide 推送来源，1运力，2货主
     * @return the unread push log count
     */
    @GET("app/message/getUnreadPushLogCount")
    Observable<HttpResponse<String>> getUnreadPushLogCount(@Query("pushSide") int pushSide);
}

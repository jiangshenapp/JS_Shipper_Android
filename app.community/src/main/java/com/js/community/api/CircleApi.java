package com.js.community.api;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.Member;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-09.
 */
public interface CircleApi {


    /**
     * 所有圈子
     *
     * @param city 城市编码
     * @param showSide 1货主APP,2司机APP
     * @return
     */
    @FormUrlEncoded
    @POST("app/circle/all")
    Observable<HttpResponse<List<CircleBean>>> getAllCircle(@Field("city") String city,
                                                            @Field("showSide") int showSide);


    /**
     * 申请加入
     *
     * @param circleId
     * @return
     */
    @FormUrlEncoded
    @POST("app/circle/apply")
    Observable<BaseHttpResponse> applyCircle(@Field("circleId") long circleId);


    /**
     * 入圈审核
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("app/circle/auditApply")
    Observable<HttpResponse<Boolean>> auditApplyCircle(@Field("id") long id,
                                                       @Field("status") String status);

    /**
     * 删除成员
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/circle/deleteSubscriber")
    Observable<HttpResponse<Boolean>> deleteSbscriber(@Field("id") long id);


    /**
     * 我的圈子
     *
     * @return
     */
    @POST("app/circle/list")
    Observable<HttpResponse<List<CircleBean>>> getCircles();


    /**
     * 获取圈子成员列表
     *
     * @param circleId
     * @return
     */
    @FormUrlEncoded
    @POST("app/circle/memberList")
    Observable<HttpResponse<List<Member>>> getCircleMembers(@Field("circleId") long circleId);



    @FormUrlEncoded
    @POST("app/circle/existCircle")
    Observable<HttpResponse<Boolean>> existCircle(@Field("circleId") long circleId);

}

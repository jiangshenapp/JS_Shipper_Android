package com.js.login.api;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019/4/21.
 */
public interface UserApi {

    /**
     * 绑定手机号
     *
     * @param mobile
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/bindMobile")
    Observable<BaseHttpResponse> bindMobile(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 短信验证码登录
     *
     * @param mobile
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/smsLogin")
    Observable<HttpResponse<String>> smsLogin(@Field("mobile") String mobile, @Field("code") String code);


    /**
     * 密码登录
     *
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/login2")
    Observable<HttpResponse<String>> login(@Field("appType") int appType,
                                           @Field("mobile") String mobile,
                                           @Field("password") String password);


    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/logout")
    Observable<BaseHttpResponse> logout(@Field("token") String token);



    /**
     * 会员注册
     *
     * @param mobile
     * @param password
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/registry")
    Observable<BaseHttpResponse> registry(@Field("mobile") String mobile,
                                          @Field("password") String password,
                                          @Field("code") String code);


    /**
     * 重置密码步骤1
     *
     * @param mobile
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/resetPwdStep1")
    Observable<BaseHttpResponse> resetPwdStep1(@Field("mobile") String mobile, @Field("code") String code);


    /**
     * 重置密码步骤2
     *
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/resetPwdStep2")
    Observable<BaseHttpResponse> resetPwdStep2(@Field("mobile") String mobile, @Field("password") String password);


    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/sendSmsCode")
    Observable<BaseHttpResponse> sendSmsCode(@Field("mobile") String mobile);


    /**
     * 设置密码
     *
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/setPwd")
    Observable<BaseHttpResponse> setPwd(@Field("password") String password);


}

package com.js.driver.api;

import com.xlgcx.http.BaseHttpResponse;
import com.xlgcx.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019/4/21.
 */
public interface UserApi {

    /**
     * 绑定手机号
     * @param code
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/bindMobile")
    Observable<BaseHttpResponse> bindMobile(@Field("code") String code,
                                            @Field("mobile") String mobile);

    /**
     * 短信验证码登录
     * @param code
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/smsLogin")
    Observable<HttpResponse<String>> smsLogin(@Field("code") String code,
                                              @Field("mobile") String mobile);


    /**
     * 密码登录
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/login")
    Observable<HttpResponse<String>> login(@Field("mobile") String mobile,
                                       @Field("password") String password);


    /**
     * 退出登录
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/logout")
    Observable<BaseHttpResponse> logout(@Field("token") String token);


    /**
     * 获取当前登录人信息
     * @return
     */
    @FormUrlEncoded
    @POST("/app/subscriber/profile")
    Observable<BaseHttpResponse> profile(@Field("token") String token);


    /**
     * 会员注册
     * @param code
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/registry")
    Observable<BaseHttpResponse> registry(@Field("code") String code,
                                          @Field("mobile") String mobile,
                                          @Field("password") String password);


    /**
     * 重置密码步骤1
     * @param code
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/resetPwdStep1")
    Observable<BaseHttpResponse> resetPwdStep1(@Field("code") String code,
                                               @Field("mobile") String mobile);


    /**
     * 重置密码步骤2
     * @param password
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/resetPwdStep2")
    Observable<BaseHttpResponse> resetPwdStep2(@Field("password") String password,
                                               @Field("mobile") String mobile);


    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/sendSmsCode")
    Observable<BaseHttpResponse> sendSmsCode(@Field("mobile") String mobile);


    /**
     * 设置密码
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/setPwd")
    Observable<BaseHttpResponse> setPwd(@Field("password") String password);


    /**
     * 个人司机认证
     * @param driverVerifiedInfo
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/verify/driverVerified")
    Observable<BaseHttpResponse> driverVerified(@Field("driverVerifiedInfo") String driverVerifiedInfo);


    /**
     * 获取司机认证信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/verify/getDriverVerifiedInfo")
    Observable<BaseHttpResponse> getDriverVerifiedInfo(@Field("token") String token);


}

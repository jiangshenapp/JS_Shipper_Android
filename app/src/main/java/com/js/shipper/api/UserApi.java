package com.js.shipper.api;

import com.js.shipper.model.bean.AuthInfo;
import com.js.shipper.model.bean.UserInfo;
import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.request.CompanyVerifiedRequest;
import com.js.shipper.model.request.PersonVerifiedRequest;

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
     * 获取当前登录人信息
     *
     * @return
     */
    @GET("app/subscriber/profile")
    Observable<HttpResponse<UserInfo>> profile();


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


    /**
     * 修改头像
     *
     * @param avatar
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/changeAvatar")
    Observable<BaseHttpResponse> changeAvatar(@Field("avatar") String avatar);


    /**
     * 修改昵称
     *
     * @param nickname
     * @return
     */
    @FormUrlEncoded
    @POST("app/subscriber/changeNickname")
    Observable<BaseHttpResponse> changeNickname(@Field("nickname") String nickname);


    /**
     * 个人货主认证
     *
     * @param data
     * @return
     */
    @POST("app/subscriber/verify/personConsignorVerified")
    Observable<BaseHttpResponse> personVerified(@Body PersonVerifiedRequest data);


    /**
     * 获取个人货主认证信息
     *
     * @return
     */
    @POST("app/subscriber/verify/getPersonConsignorVerifiedInfo")
    Observable<HttpResponse<AuthInfo>> getPersonVerifiedInfo();


    /**
     * 企业货主认证
     *
     * @param data
     * @return
     */
    @POST("app/subscriber/verify/companyConsignorVerified")
    Observable<BaseHttpResponse> companyVerified(@Body CompanyVerifiedRequest data);


    /**
     * 获取企业货主认证信息
     *
     * @return
     */
    @POST("app/subscriber/verify/getCompanyConsignorVerifiedInfo")
    Observable<HttpResponse<AuthInfo>> getCompanyVerifiedInfo();

}

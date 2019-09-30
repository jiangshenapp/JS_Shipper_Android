package com.js.login.api;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;
import com.js.login.model.bean.WxLogin;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-30.
 */
public interface WxApi {

    @FormUrlEncoded
    @POST("app/driverWx/wxCodeLogin")
    Observable<HttpResponse<String>> wxCodeLoginDriver(@Field("code") String code);


    @FormUrlEncoded
    @POST("app/wx/wxCodeLogin")
    Observable<HttpResponse<String>> wxCodeLogin(@Field("code") String code);


    @FormUrlEncoded
    @POST("app/wx/wxLogin")
    Observable<HttpResponse<String>> wxLogin(@Field("code") String code,
                                         @Field("headimgurl") String headimgurl,
                                         @Field("mobile") String mobile,
                                         @Field("nickname") String nickname,
                                         @Field("openid") String openid,
                                         @Field("unionid") String unionid
    );


    /**
     * 解绑原账号，绑定当前新账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/wx/rebindingWxInfo")
    Observable<BaseHttpResponse> reBindWxInfo(@Field("headimgurl") String headimgurl,
                                              @Field("nickname") String nickname,
                                              @Field("openid") String openid,
                                              @Field("unionid") String unionid);

    @FormUrlEncoded
    @POST("app/driverWx/rebindingWxInfo")
    Observable<BaseHttpResponse> wxLoginDriver(@Field("code") String code,
                                               @Field("headimgurl") String headimgurl,
                                               @Field("mobile") String mobile,
                                               @Field("nickname") String nickname,
                                               @Field("openid") String openid,
                                               @Field("unionid") String unionid);


    /**
     * 解绑原账号，绑定当前新账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/driverWx/wxLogin")
    Observable<BaseHttpResponse> reBindWxInfoDriver(@Field("headimgurl") String headimgurl,
                                                    @Field("nickname") String nickname,
                                                    @Field("openid") String openid,
                                                    @Field("unionid") String unionid);


}

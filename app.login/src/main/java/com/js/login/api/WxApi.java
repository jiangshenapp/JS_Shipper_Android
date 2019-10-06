package com.js.login.api;

import com.base.frame.bean.BaseHttpResponse;
import com.base.frame.bean.HttpResponse;
import com.base.http.TypeString;
import com.js.login.model.bean.WxLogin;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-30.
 */
public interface WxApi {

    @TypeString
    @FormUrlEncoded
    @POST("app/driverWx/wxCodeLogin")
    Observable<String> wxCodeLoginDriver(@Field("code") String code);

    @TypeString
    @FormUrlEncoded
    @POST("app/wx/wxCodeLogin")
    Observable<String> wxCodeLogin(@Field("code") String code);


    @FormUrlEncoded
    @POST("app/wx/wxLogin")
    Observable<HttpResponse<String>> wxLogin(@Field("code") String code,
                                         @Field("headimgurl") String headimgurl,
                                         @Field("mobile") String mobile,
                                         @Field("nickname") String nickname,
                                         @Field("openid") String openid,
                                         @Field("unionid") String unionid
    );


    @FormUrlEncoded
    @POST("app/driverWx/wxLogin")
    Observable<HttpResponse<String>> wxLoginDriver(@Field("code") String code,
                                             @Field("headimgurl") String headimgurl,
                                             @Field("mobile") String mobile,
                                             @Field("nickname") String nickname,
                                             @Field("openid") String openid,
                                             @Field("unionid") String unionid
    );


    /**
     * 登录后解绑
     * @return
     */
    @POST("app/driverWx/unbindingWxInfo")
    Observable<HttpResponse<Boolean>> unBindWxDriver();

    /**
     * 登录后解绑
     * @return
     */
    @POST("app/wx/unbindingWxInfo")
    Observable<HttpResponse<Boolean>> unBindWx();


    /**
     * 登录后绑定
     * @return
     */
    @FormUrlEncoded
    @POST("app/driverWx/bindingWxInfo")
    Observable<HttpResponse<WxLogin>> bindWxInfoDriver(@Field("code") String code);


    /**
     * 登录后绑定
     * @return
     */
    @FormUrlEncoded
    @POST("app/wx/bindingWxInfo")
    Observable<HttpResponse<WxLogin>> bindWxInfo(@Field("code") String code);



    /**
     * 解绑原账号，绑定当前新账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/driverWx/rebindingWxInfo")
    Observable<BaseHttpResponse> reBindWxInfoDriver(@Field("headimgurl") String headimgurl,
                                                    @Field("nickname") String nickname,
                                                    @Field("openid") String openid,
                                                    @Field("unionid") String unionid);

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

}

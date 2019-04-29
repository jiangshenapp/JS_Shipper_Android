package com.xlgcx.http.interceptor;

import android.text.TextUtils;

import com.xlgcx.http.HttpApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huyg on 2019/4/29.
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();//获取请求
        Request tokenRequest = null;
        if (TextUtils.isEmpty(HttpApp.getApp().token)) {//对 token 进行判空，如果为空，则不进行修改
            return chain.proceed(originalRequest);
        }
        tokenRequest = originalRequest.newBuilder()//往请求头中添加 token 字段
                .header("token", HttpApp.getApp().token)
                .build();
        return chain.proceed(tokenRequest);
    }
}

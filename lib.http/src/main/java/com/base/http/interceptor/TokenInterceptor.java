package com.base.http.interceptor;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.base.frame.bean.BaseHttpResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by huyg on 2019-06-05.
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String body = response.body().string();
        if (!TextUtils.isEmpty(body)) {
            BaseHttpResponse baseHttpResponse = new Gson().fromJson(body, BaseHttpResponse.class);
            if (baseHttpResponse.getCode() == -1) {
                ARouter.getInstance().build("/user/login").navigation();
                return response.newBuilder()
                        .body(ResponseBody.create(MediaType.parse("UTF-8"), body))
                        .build();
            }
        }
        //body只能读取一次
        return response.newBuilder()
                .body(ResponseBody.create(MediaType.parse("UTF-8"), body))
                .build();
    }
}

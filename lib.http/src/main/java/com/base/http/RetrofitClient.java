package com.base.http;

import android.content.Context;

import com.base.http.interceptor.LoggingInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.base.http.converter.StringConverterFactory;
import com.base.http.global.Const;
import com.base.http.interceptor.AuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Weixf
 * Date on 2017-11-14
 * Description retrofit的基类
 **/

public class RetrofitClient {

    public Context context;
    private Retrofit mRetrofit;


    private static final class RetrofitClientHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return RetrofitClientHolder.INSTANCE;
    }


    public RetrofitClient() {
        createRetrofit();
    }

    private void createRetrofit() {
        OkHttpClient.Builder clientBuild = (new OkHttpClient.Builder())
                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new AuthInterceptor())
                ;
        if (BuildConfig.DEBUG) {
            clientBuild.addInterceptor(new LoggingInterceptor());
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Const.API_URL())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuild.build())
                .build();
    }

    public <T> T create(Class<?> clazz) {
        return (T) mRetrofit.create(clazz);
    }

}

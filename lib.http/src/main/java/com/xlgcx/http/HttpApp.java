package com.xlgcx.http;

import android.app.Application;
import android.content.Context;

import com.xlgcx.frame.module.IAppLife;

/**
 * Created by huyg on 2019/1/31.
 */
public class HttpApp implements IAppLife {

    private static Application mHttpApp;

    public static Application getInstance() {
        return mHttpApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mHttpApp = application;
    }

    @Override
    public void onTerminate(Application application) {

    }

}

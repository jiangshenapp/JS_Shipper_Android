package com.plugin.im;

import android.app.Application;
import android.content.Context;

import com.base.frame.module.IAppLife;

/**
 * Created by huyg on 2019/1/31.
 */
public class ImApp implements IAppLife {

    private static Application mImApp;
    public String token;
    private static ImApp mApp;
    private ImApp mAppComponent;


    public static Application getInstance() {
        return mImApp;
    }

    public static ImApp getApp() {
        return mApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mImApp = application;
        mApp = this;
        IMHelper.getInstance().init(mImApp);
    }

    @Override
    public void onTerminate(Application application) {

    }

}

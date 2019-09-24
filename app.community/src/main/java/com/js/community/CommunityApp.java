package com.js.community;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.base.frame.module.IAppLife;
import com.js.community.di.componet.AppComponent;
import com.js.community.di.componet.DaggerAppComponent;
import com.js.community.di.module.AppModule;

/**
 * Created by huyg on 2019/1/31.
 */
public class CommunityApp implements IAppLife {

    private static Application mCommunityApp;
    public String token;
    private static CommunityApp mApp;
    private AppComponent mAppComponent;
    public BDLocation mLocation;

    public static Application getInstance() {
        return mCommunityApp;
    }

    public static CommunityApp getApp() {
        return mApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mCommunityApp = application;
        mApp = this;
        initDaggerComponent();
    }

    @Override
    public void onTerminate(Application application) {

    }

    /**
     * 获取连接器
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    /**
     * 初始化Dagger所使用的连接器
     */
    private void initDaggerComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();
    }

}

package com.js.message;

import android.app.Application;
import android.content.Context;
import com.base.frame.module.IAppLife;
import com.js.message.di.componet.AppComponent;
import com.js.message.di.componet.DaggerAppComponent;
import com.js.message.di.module.AppModule;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/08
 * desc   :
 * version: 3.0.0
 */
public class MessageApp implements IAppLife {

    private static Application mMessageApp;
    public String token;
    private static MessageApp mApp;
    private AppComponent mAppComponent;
    public String appType;
    public int driverVerified;
    public int parkVerified;
    public int companyConsignorVerified;
    public int personConsignorVerified;
    
    public static MessageApp getInstance() {
        return mApp;
    }

    public static Application getApp() {
        return mMessageApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mMessageApp = application;
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

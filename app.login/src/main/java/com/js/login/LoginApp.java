package com.js.login;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.base.frame.module.IAppLife;
import com.base.http.HttpApp;
import com.base.util.manager.SpManager;
import com.js.login.di.componet.AppComponent;
import com.js.login.di.componet.DaggerAppComponent;
import com.js.login.di.module.AppModule;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by huyg on 2019/1/31.
 */
public class LoginApp implements IAppLife {

    private static Application mLoginApp;
    public String token;
    private static LoginApp mApp;
    private AppComponent mAppComponent;
    public BDLocation mLocation;
    public String appType;
    public IWXAPI api;
    public static LoginApp getInstance() {
        return mApp;
    }

    public static Application getApp() {
        return mLoginApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mLoginApp = application;
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

    /**
     * 存储token
     */
    public void putToken(String token) {
        HttpApp.getApp().token = token;
        SpManager.getInstance(mLoginApp).putSP("token",token);
        this.token = token;
    }

    public IWXAPI getApi() {
        return api;
    }
}

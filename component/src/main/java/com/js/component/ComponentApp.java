package com.js.component;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.base.frame.module.IAppLife;
import com.js.component.di.componet.AppComponent;
import com.js.component.di.componet.DaggerAppComponent;
import com.js.component.di.module.AppModule;

/**
 * Created by huyg on 2019/1/31.
 */
public class ComponentApp implements IAppLife {

    private static Application mComponentApp;
    public String token;
    private static ComponentApp mApp;
    private AppComponent mAppComponent;
    public BDLocation mLocation;

    public static Application getInstance() {
        return mComponentApp;
    }

    public static ComponentApp getApp() {
        return mApp;
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        mComponentApp = application;
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

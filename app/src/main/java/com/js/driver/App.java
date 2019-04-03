package com.js.driver;

import com.js.driver.di.componet.AppComponent;
import com.js.driver.di.module.AppModule;
import com.xlgcx.frame.BaseApplication;

/**
 * Created by huyg on 2019/4/1.
 */
public class App extends BaseApplication {

    private AppComponent mAppComponent;
    private static App mApp;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initDaggerComponent();
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

    public static App getInstance() {
        return mApp;
    }

}

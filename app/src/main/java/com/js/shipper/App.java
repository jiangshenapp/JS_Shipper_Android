package com.js.shipper;

import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.js.shipper.di.componet.AppComponent;
import com.js.shipper.di.componet.DaggerAppComponent;
import com.js.shipper.di.module.AppModule;
import com.js.shipper.manager.SpManager;
import com.js.frame.BaseApplication;
import com.js.http.HttpApp;

/**
 * Created by huyg on 2019/4/1.
 */
public class App extends BaseApplication {

    private AppComponent mAppComponent;
    private static App mApp;
    public String token;
    public Gson mGson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Stetho.initializeWithDefaults(this);
        initDaggerComponent();
        initToken("");
        HttpApp.getApp().token = token;
    }

    public void initToken(String token) {
        if (TextUtils.isEmpty(token)) {
            this.token = SpManager.getInstance(this).getSP("token");
        } else {
            this.token = token;
        }
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


    public String gsonFormat(Object clazz) {
        return mGson.toJson(clazz);
    }

}

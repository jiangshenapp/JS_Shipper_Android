package com.js.shipper;


import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.js.login.LoginApp;
import com.js.shipper.di.componet.AppComponent;
import com.js.shipper.di.componet.DaggerAppComponent;
import com.js.shipper.di.module.AppModule;
import com.base.util.manager.SpManager;
import com.base.frame.BaseApplication;
import com.base.http.HttpApp;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.UserInfo;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by huyg on 2019/4/1.
 * 接口地址【文档：http://testway.jiangshen56.com/swagger-ui.html?urls.primaryName=logistic-biz、
 * https://gateway.jiangshen56.com/swagger-ui.html?urls.primaryName=logistic-biz】
 */
public class App extends BaseApplication {

    private AppComponent mAppComponent;
    private static App mApp;
    public String token;
    public Gson mGson = new Gson();
    public BDLocation mLocation;
    public String avatar;
    public String mobile;
    public String nickName;
    public int driverVerified;
    public int parkVerified;
    public int companyConsignorVerified;
    public int personConsignorVerified;
    private IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SDKInitializer.initialize(this);
        Stetho.initializeWithDefaults(this);
        initDaggerComponent();
        getUserInfo();
        LoginApp.getInstance().appType = BuildConfig.appType;
        registerWx();
        closeAndroidPDialog();
        initCrash();
        //极光推送初始化
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(getApplicationContext());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        this.token = SpManager.getInstance(this).getSP("token");
        this.avatar = SpManager.getInstance(this).getSP("avatar");
        this.mobile = SpManager.getInstance(this).getSP("mobile");
        this.nickName = SpManager.getInstance(this).getSP("nickName");
        this.driverVerified = SpManager.getInstance(this).getIntSP("driverVerified");
        this.parkVerified = SpManager.getInstance(this).getIntSP("parkVerified");
        this.companyConsignorVerified = SpManager.getInstance(this).getIntSP("companyConsignorVerified");
        this.personConsignorVerified = SpManager.getInstance(this).getIntSP("personConsignorVerified");

        HttpApp.getApp().token = this.token;
    }

    /**
     * 存储用户信息
     */
    public void putUserInfo(UserInfo userInfo) {
        SpManager.getInstance(this).putSP("avatar", userInfo.getAvatar());
        SpManager.getInstance(this).putSP("mobile", userInfo.getMobile());
        SpManager.getInstance(this).putSP("nickName", userInfo.getNickName());
        SpManager.getInstance(this).putIntSP("driverVerified", userInfo.getDriverVerified());
        SpManager.getInstance(this).putIntSP("parkVerified", userInfo.getParkVerified());
        SpManager.getInstance(this).putIntSP("companyConsignorVerified", userInfo.getCompanyConsignorVerified());
        SpManager.getInstance(this).putIntSP("personConsignorVerified", userInfo.getPersonConsignorVerified());
        SpManager.getInstance(this).putIntSP("id", userInfo.getId());

        getUserInfo();
    }

    /**
     * 清空用户信息
     */
    public void clearUserInfo() {
        String loginPhone = SpManager.getInstance(App.getInstance()).getSP("loginPhone");
        SpManager.getInstance(App.getInstance()).clear();
        SpManager.getInstance(App.getInstance()).putSP("loginPhone", loginPhone); //登录手机号不清空
        getUserInfo();
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

    private void registerWx() {
        api = WXAPIFactory.createWXAPI(this, Const.APP_ID, false);
        api.registerApp(Const.APP_ID);
        LoginApp.getInstance().api = api;
    }

    public IWXAPI getApi() {
        return api;
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCrash() {
        CrashReport.initCrashReport(getApplicationContext(), com.base.frame.global.Const.BUGLY_APP_SHIPPER_ID, true);
    }

}

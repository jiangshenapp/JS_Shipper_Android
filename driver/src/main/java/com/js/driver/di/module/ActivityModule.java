package com.js.driver.di.module;

import android.app.Activity;


import com.js.driver.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/8/22.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}

package com.js.driver.di.module;

import com.js.driver.ui.main.activity.MainActivity;
import com.xlgcx.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/8/22.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiFactory provideApiFactory() {
        return new ApiFactory();
    }



}

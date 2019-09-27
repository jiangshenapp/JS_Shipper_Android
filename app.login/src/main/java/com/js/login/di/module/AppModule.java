package com.js.login.di.module;

import com.base.http.ApiFactory;

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

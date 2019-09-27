package com.js.login.di.componet;


import com.base.http.ApiFactory;
import com.js.login.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    ApiFactory ApiFactory();
}

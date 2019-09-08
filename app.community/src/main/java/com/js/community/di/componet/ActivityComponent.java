package com.js.community.di.componet;

import android.app.Activity;

import com.js.community.di.ActivityScope;
import com.js.community.di.module.ActivityModule;

import dagger.Component;


/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();


}


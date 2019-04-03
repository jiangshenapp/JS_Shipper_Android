package com.js.driver.di.componet;

import android.app.Activity;

import com.js.driver.di.ActivityScope;
import com.js.driver.di.module.ActivityModule;
import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

}

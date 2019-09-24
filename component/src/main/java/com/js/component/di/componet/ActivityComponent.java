package com.js.component.di.componet;

import android.app.Activity;


import com.js.component.city.SelectCityActivity;
import com.js.component.di.ActivityScope;
import com.js.component.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SelectCityActivity selectCityActivity);
}


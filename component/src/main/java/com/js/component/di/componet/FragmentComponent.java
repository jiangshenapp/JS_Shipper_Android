package com.js.component.di.componet;

import android.app.Activity;


import com.js.component.di.FragmentScope;
import com.js.component.di.module.FragmentModule;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

}

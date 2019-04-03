package com.js.driver.di.componet;

import android.app.Activity;

import com.js.driver.di.FragmentScope;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.ui.main.fragment.CommunityFragment;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(CommunityFragment communityFragment);


}

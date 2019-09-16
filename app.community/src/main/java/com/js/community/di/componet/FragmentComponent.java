package com.js.community.di.componet;

import android.app.Activity;


import com.js.community.di.FragmentScope;
import com.js.community.di.module.FragmentModule;
import com.js.community.ui.fragment.PostFragment;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(PostFragment postFragment);

}

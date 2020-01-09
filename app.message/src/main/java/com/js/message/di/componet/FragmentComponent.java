package com.js.message.di.componet;

import android.app.Activity;
import com.js.message.di.FragmentScope;
import com.js.message.di.module.FragmentModule;
import com.js.message.ui.fragment.InformationFragment;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(InformationFragment informationFragment);
}

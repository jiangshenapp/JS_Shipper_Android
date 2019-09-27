package com.js.login.di.componet;

import android.app.Activity;

import com.js.login.di.FragmentScope;
import com.js.login.di.module.FragmentModule;
import com.js.login.ui.fragment.CodeLoginFragment;
import com.js.login.ui.fragment.PwdLoginFragment;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(CodeLoginFragment codeLoginFragment);

    void inject(PwdLoginFragment pwdLoginFragment);
}

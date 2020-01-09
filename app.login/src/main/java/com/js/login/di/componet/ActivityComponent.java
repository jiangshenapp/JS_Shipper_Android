package com.js.login.di.componet;

import android.app.Activity;


import com.js.login.di.ActivityScope;
import com.js.login.di.module.ActivityModule;
import com.js.login.ui.activity.BindStatusActivity;
import com.js.login.ui.activity.ForgetPwdActivity;
import com.js.login.ui.activity.RegisterActivity;
import com.js.login.ui.activity.ResetPwdActivity;
import com.js.login.ui.activity.WxBindActivity;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(ForgetPwdActivity forgetPwdActivity);

    void inject(RegisterActivity registerActivity);

    void inject(ResetPwdActivity resetPwdActivity);

    void inject(WxBindActivity wxBindActivity);

    void inject(BindStatusActivity bindStatusActivity);
}


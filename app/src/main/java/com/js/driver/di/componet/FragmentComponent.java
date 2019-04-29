package com.js.driver.di.componet;

import android.app.Activity;

import com.js.driver.di.FragmentScope;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.ui.main.fragment.CommunityFragment;
import com.js.driver.ui.main.fragment.FindOrderFragment;
import com.js.driver.ui.main.fragment.InformationFragment;
import com.js.driver.ui.main.fragment.MineFragment;
import com.js.driver.ui.main.fragment.ServiceFragment;
import com.js.driver.ui.order.fragment.OrderFragment;
import com.js.driver.ui.user.fragment.CodeLoginFragment;
import com.js.driver.ui.user.fragment.PwdLoginFragment;
import com.js.driver.ui.wallet.fragment.BillFragment;

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

    void inject(CommunityFragment communityFragment);

    void inject(FindOrderFragment findOrderFragment);

    void inject(InformationFragment informationFragment);

    void inject(ServiceFragment serviceFragment);

    void inject(MineFragment mineFragment);

    void inject(BillFragment billFragment);

    void inject(OrderFragment orderFragment);


}

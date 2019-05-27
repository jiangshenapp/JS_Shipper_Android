package com.js.shipper.di.componet;

import android.app.Activity;

import com.js.shipper.di.FragmentScope;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.fragment.BoutiqueFragment;
import com.js.shipper.ui.main.fragment.CarSourceFragment;
import com.js.shipper.ui.main.fragment.CommunityFragment;
import com.js.shipper.ui.main.fragment.DeliveryFragment;
import com.js.shipper.ui.main.fragment.FindOrderFragment;
import com.js.shipper.ui.main.fragment.InformationFragment;
import com.js.shipper.ui.main.fragment.MineFragment;
import com.js.shipper.ui.main.fragment.ServiceFragment;
import com.js.shipper.ui.main.fragment.ShipFragment;
import com.js.shipper.ui.order.fragment.OrderFragment;
import com.js.shipper.ui.user.fragment.CodeLoginFragment;
import com.js.shipper.ui.user.fragment.CompanyVerifiedFragment;
import com.js.shipper.ui.user.fragment.PersonVerifiedFragment;
import com.js.shipper.ui.user.fragment.PwdLoginFragment;
import com.js.shipper.ui.wallet.fragment.BillFragment;

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

    void inject(ShipFragment shipFragment);

    void inject(DeliveryFragment deliveryFragment);

    void inject(CarSourceFragment carSourceFragment);

    void inject(BoutiqueFragment boutiqueFragment);

    void inject(CompanyVerifiedFragment companyVerifiedFragment);

    void inject(PersonVerifiedFragment personVerifiedFragment);
}

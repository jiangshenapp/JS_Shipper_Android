package com.js.shipper.di.componet;

import android.app.Activity;

import com.js.shipper.di.ActivityScope;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.SplashActivity;
import com.js.shipper.ui.center.activity.FeedBackActivity;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.message.activity.MessageActivity;
import com.js.shipper.ui.message.activity.MessageDetailActivity;
import com.js.shipper.ui.order.activity.OrderDetailActivity;
import com.js.shipper.ui.order.activity.OrderEditActivity;
import com.js.shipper.ui.order.activity.OrderSubmitActivity;
import com.js.shipper.ui.order.activity.SubmitOrderActivity;
import com.js.shipper.ui.order.activity.TypeInputActivity;
import com.js.shipper.ui.park.activity.BoutiqueDetailActivity;
import com.js.shipper.ui.park.activity.BranchDetailActivity;
import com.js.shipper.ui.park.activity.CarSourceDetailActivity;
import com.js.shipper.ui.ship.activity.ReceiverInfoActivity;
import com.js.shipper.ui.ship.activity.SelectAddressActivity;
import com.js.shipper.ui.user.activity.UserCenterActivity;
import com.js.shipper.ui.wallet.activity.BailActivity;
import com.js.shipper.ui.wallet.activity.PayActivity;
import com.js.shipper.ui.wallet.activity.RechargeActivity;
import com.js.shipper.ui.wallet.activity.WalletActivity;
import com.js.shipper.ui.wallet.activity.WithdrawActivity;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(UserCenterActivity userCenterActivity);

    void inject(WalletActivity walletActivity);

    void inject(BailActivity bailActivity);

    void inject(RechargeActivity rechargeActivity);

    void inject(WithdrawActivity withdrawActivity);


    void inject(OrderDetailActivity orderDetailActivity);

    void inject(OrderSubmitActivity orderSubmitActivity);

    void inject(SelectAddressActivity selectAddressActivity);

    void inject(ReceiverInfoActivity receiverInfoActivity);

    void inject(SplashActivity splashActivity);

    void inject(FeedBackActivity feedBackActivity);


    void inject(PayActivity payActivity);

    void inject(CarSourceDetailActivity carSourceDetailActivity);

    void inject(BoutiqueDetailActivity boutiqueDetailActivity);

    void inject(OrderEditActivity orderEditActivity);

    void inject(SubmitOrderActivity submitOrderActivity);

    void inject(BranchDetailActivity branchDetailActivity);


    void inject(TypeInputActivity typeInputActivity);

    void inject(MessageActivity messageActivity);

    void inject(MessageDetailActivity messageDetailActivity);
}


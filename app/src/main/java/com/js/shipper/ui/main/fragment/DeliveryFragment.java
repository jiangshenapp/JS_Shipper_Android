package com.js.shipper.ui.main.fragment;

import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.presenter.DeliveryPresenter;
import com.js.shipper.ui.main.presenter.contract.DeliveryContract;

/**
 * Created by huyg on 2019/4/30.
 * 城市配送
 */
public class DeliveryFragment extends BaseFragment<DeliveryPresenter> implements DeliveryContract.View {


    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }

    @Override
    protected void init() {

    }
}

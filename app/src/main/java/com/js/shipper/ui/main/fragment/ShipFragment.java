package com.js.shipper.ui.main.fragment;

import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.presenter.ShipPresenter;
import com.js.shipper.ui.main.presenter.contract.ShipContract;

/**
 * Created by huyg on 2019/4/30.
 * 发货
 */
public class ShipFragment extends BaseFragment<ShipPresenter> implements ShipContract.View {


    public static ShipFragment newInstance() {
        return new ShipFragment();
    }


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
        return R.layout.fragment_ship;
    }


    @Override
    protected void init() {

    }
}

package com.js.shipper.ui.main.fragment;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.presenter.InformationPresenter;
import com.js.shipper.ui.main.presenter.contract.InformationContract;
import com.js.frame.view.BaseFragment;

/**
 * Created by huyg on 2019/4/1.
 */
public class InformationFragment extends BaseFragment<InformationPresenter> implements InformationContract.View {

    public static InformationFragment newInstance() {
        return new InformationFragment();
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
        return R.layout.fragment_service;
    }

    @Override
    protected void init() {

    }
}

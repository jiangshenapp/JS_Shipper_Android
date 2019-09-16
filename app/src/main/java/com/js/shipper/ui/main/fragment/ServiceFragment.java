package com.js.shipper.ui.main.fragment;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.model.bean.BannerBean;
import com.js.shipper.model.bean.ServiceBean;
import com.js.shipper.ui.main.presenter.ServicePresenter;
import com.js.shipper.ui.main.presenter.contract.ServiceContract;
import com.base.frame.view.BaseFragment;

import java.util.List;

/**
 * Created by huyg on 2019/4/1.
 */
public class ServiceFragment extends BaseFragment<ServicePresenter> implements ServiceContract.View {


    public static ServiceFragment newInstance() {
        return new ServiceFragment();
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

    @Override
    public void onBannerList(List<BannerBean> mBannerBeans) {

    }

    @Override
    public void onServiceList(List<ServiceBean> mServiceBeans) {

    }
}

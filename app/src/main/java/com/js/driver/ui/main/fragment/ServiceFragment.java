package com.js.driver.ui.main.fragment;

import com.js.driver.ui.main.presenter.ServicePresenter;
import com.js.driver.ui.main.presenter.contract.ServiceContract;
import com.xlgcx.frame.view.BaseFragment;

/**
 * Created by huyg on 2019/4/1.
 */
public class ServiceFragment extends BaseFragment<ServicePresenter> implements ServiceContract.View {


    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init() {

    }
}

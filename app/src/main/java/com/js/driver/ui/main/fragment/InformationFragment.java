package com.js.driver.ui.main.fragment;

import com.js.driver.ui.main.presenter.InformationPresenter;
import com.js.driver.ui.main.presenter.contract.InformationContract;
import com.xlgcx.frame.view.BaseFragment;

/**
 * Created by huyg on 2019/4/1.
 */
public class InformationFragment extends BaseFragment<InformationPresenter> implements InformationContract.View {

    public static InformationFragment newInstance() {
        return new InformationFragment();
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

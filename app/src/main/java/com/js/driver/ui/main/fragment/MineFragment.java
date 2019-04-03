package com.js.driver.ui.main.fragment;

import com.js.driver.ui.main.presenter.MinePresenter;
import com.js.driver.ui.main.presenter.contract.MineContract;
import com.xlgcx.frame.view.BaseFragment;

/**
 * Created by huyg on 2019/4/1.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    public static MineFragment newInstance() {
        return new MineFragment();
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

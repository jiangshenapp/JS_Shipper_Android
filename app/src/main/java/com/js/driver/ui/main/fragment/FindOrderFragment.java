package com.js.driver.ui.main.fragment;

import com.js.driver.ui.main.presenter.FindOrderPresenter;
import com.js.driver.ui.main.presenter.contract.FindOrderContract;
import com.xlgcx.frame.view.BaseFragment;

/**
 * Created by huyg on 2019/4/1.
 */
public class FindOrderFragment extends BaseFragment<FindOrderPresenter> implements FindOrderContract.View {

    public static FindOrderFragment newInstance() {
        return new FindOrderFragment();
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

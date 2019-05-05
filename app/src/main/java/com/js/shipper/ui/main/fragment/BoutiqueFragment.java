package com.js.shipper.ui.main.fragment;

import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.presenter.BoutiquePresenter;
import com.js.shipper.ui.main.presenter.contract.BoutiqueContract;

/**
 *
 * Created by huyg on 2019/4/30.
 * 精品路线
 */
public class BoutiqueFragment extends BaseFragment<BoutiquePresenter> implements BoutiqueContract.View {



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
        return R.layout.fragment_boutique;
    }

    @Override
    protected void init() {

    }
}

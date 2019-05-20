package com.js.shipper.ui.ship.activity;

import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.ship.presenter.ReceiverInfoPresenter;
import com.js.shipper.ui.ship.presenter.contract.ReceiverInfoContract;

/**
 * Created by huyg on 2019/5/20.
 */
public class ReceiverInfoActivity extends BaseActivity<ReceiverInfoPresenter> implements ReceiverInfoContract.View {


    @Override
    protected void init() {

    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receiver_info;
    }

    @Override
    public void setActionBar() {

    }
}

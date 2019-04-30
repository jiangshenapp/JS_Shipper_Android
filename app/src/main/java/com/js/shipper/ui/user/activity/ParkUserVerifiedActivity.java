package com.js.shipper.ui.user.activity;

import android.content.Context;
import android.content.Intent;

import com.js.shipper.App;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.user.presenter.ParkUserVerifiedPresenter;
import com.js.shipper.ui.user.presenter.contract.ParkUserVerifiedContract;
import com.js.frame.view.BaseActivity;

/**
 * Created by huyg on 2019/4/24.
 */
public class ParkUserVerifiedActivity extends BaseActivity<ParkUserVerifiedPresenter> implements ParkUserVerifiedContract.View {


    public static void action(Context context){
        context.startActivity(new Intent(context,ParkUserVerifiedActivity.class));
    }

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
        return 0;
    }

    @Override
    public void setActionBar() {

    }
}

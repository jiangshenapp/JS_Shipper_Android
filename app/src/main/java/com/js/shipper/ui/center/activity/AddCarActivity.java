package com.js.shipper.ui.center.activity;

import android.content.Context;
import android.content.Intent;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.center.presenter.AddCarPresenter;
import com.js.shipper.ui.center.presenter.contract.AddCarContract;
import com.js.frame.view.BaseActivity;

/**
 * Created by huyg on 2019/4/29.
 */
public class AddCarActivity extends BaseActivity<AddCarPresenter> implements AddCarContract.View {


    public static void action(Context context){
        context.startActivity(new Intent(context,AddCarActivity.class));
    }
    @Override
    protected void init() {
        initView();
        initData();
    }



    private void initView() {

    }

    private void initData() {

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
        return R.layout.activity_add_car;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("添加车辆");
    }
}

package com.js.shipper.ui.car.activity;

import android.content.Context;
import android.content.Intent;

import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.ui.car.presenter.AddCarPresenter;
import com.js.shipper.ui.car.presenter.contract.AddCarContract;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public class AddCarActivity extends BaseActivity<AddCarPresenter> implements AddCarContract.View {

    public static void action(Context context) {
        Intent intent = new Intent(context, AddCarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {

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
        return R.layout.fragment_order_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("添加运力");
    }

    @Override
    public void onQueryCarList(List<CarBean> carBeans) {

    }

    @Override
    public void onAddCar(boolean isOk) {

    }
}

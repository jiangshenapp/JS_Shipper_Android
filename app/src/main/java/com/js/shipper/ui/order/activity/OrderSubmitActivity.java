package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.order.presenter.OrderSubmitPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitActivity extends BaseActivity<OrderSubmitPresenter> implements OrderSubmitContract.View {


    public static void action(Context context){
        Intent intent = new Intent(context,OrdersActivity.class);
        context.startActivity(intent);
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
        return R.layout.activity_order_submit;
    }

    @Override
    public void setActionBar() {
        setTitle("确认订单");
    }
}

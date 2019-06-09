package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.ui.order.presenter.OrderDetailPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderDetailContract;
import com.js.frame.view.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {


    private long orderId;

    public static void action(Context context, long orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initData();
    }

    private void initData() {
        mPresenter.getOrderDetail(orderId);
    }

    private void initIntent() {
        orderId = getIntent().getLongExtra("orderId", 0);
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
        return R.layout.activity_order_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("订单详情");
    }

    @Override
    public void onOrderDetail(OrderBean orderBean) {

    }
}

package com.js.driver.ui.order.activity;

import android.content.Context;
import android.content.Intent;

import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.ui.order.presenter.OrderDetailPresenter;
import com.js.driver.ui.order.presenter.contract.OrderDetailContract;
import com.xlgcx.frame.view.BaseActivity;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {

    public static void action(Context context,String orderId){
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra("orderId",orderId);
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
        return R.layout.activity_order_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("订单详情");
    }
}

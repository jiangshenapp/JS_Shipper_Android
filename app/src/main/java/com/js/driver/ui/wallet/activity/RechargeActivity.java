package com.js.driver.ui.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.ui.wallet.presenter.RechargePresenter;
import com.js.driver.ui.wallet.presenter.contract.RechargeContract;
import com.xlgcx.frame.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeContract.View {


    @BindView(R.id.recharge_money)
    EditText mMoney;


    public static void action(Context context) {
        context.startActivity(new Intent(context, RechargeActivity.class));
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
        return R.layout.activity_wallet_recharge;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("充值");
    }

    @OnClick({R.id.item_alipay_layout, R.id.item_wx_layout, R.id.item_balance_layout, R.id.wallet_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_alipay_layout://支付宝
                break;
            case R.id.item_wx_layout://微信
                break;
            case R.id.item_balance_layout://账户余额
                break;
            case R.id.wallet_recharge://充值
                break;
        }
    }
}

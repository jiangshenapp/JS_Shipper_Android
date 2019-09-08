package com.js.shipper.ui.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.wallet.presenter.BailPresenter;
import com.js.shipper.ui.wallet.presenter.contract.BailContract;
import com.base.frame.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 * 我的保证金
 */
public class BailActivity extends BaseActivity<BailPresenter> implements BailContract.View {


    @BindView(R.id.bail_money)
    TextView mMoney;

    public static void action(Context context) {
        context.startActivity(new Intent(context, BailActivity.class));
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
        return R.layout.activity_wallet_bail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的保证金");
    }


    @OnClick({R.id.bail_withdraw, R.id.bail_recharge, R.id.bail_detail_layout, R.id.statement_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bail_withdraw://提现
                break;
            case R.id.bail_recharge://充值
                break;
            case R.id.bail_detail_layout://明细
                BillActivity.action(mContext,2);
                break;
            case R.id.statement_layout://违约说明
                break;
        }
    }
}

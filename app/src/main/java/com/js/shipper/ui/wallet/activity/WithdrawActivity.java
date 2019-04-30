package com.js.shipper.ui.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.wallet.presenter.WithdrawPresenter;
import com.js.shipper.ui.wallet.presenter.contract.WithdrawContract;
import com.js.frame.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawContract.View {


    @BindView(R.id.withdraw_money)
    EditText mMoney;
    @BindView(R.id.withdraw_money_max)
    TextView mMoneyMax;
    @BindView(R.id.bank_number)
    EditText mBankNumber;
    @BindView(R.id.bank_opening)
    EditText mBankOpening;
    @BindView(R.id.bank_sub)
    EditText mBankSub;
    @BindView(R.id.item_bank_info_layout)
    LinearLayout mBankInfoLayout;

    public static void action(Context context) {
        context.startActivity(new Intent(context, WithdrawActivity.class));
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
        return R.layout.activity_wallet_withdraw;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("提现");
    }

    @OnClick({R.id.item_alipay_layout, R.id.item_wx_layout, R.id.item_bank_layout, R.id.wallet_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_alipay_layout://支付宝
                break;
            case R.id.item_wx_layout://微信
                break;
            case R.id.item_bank_layout://
                break;
            case R.id.wallet_withdraw:
                break;
        }
    }
}

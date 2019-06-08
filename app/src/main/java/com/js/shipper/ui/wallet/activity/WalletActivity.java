package com.js.shipper.ui.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.AccountInfo;
import com.js.shipper.ui.wallet.presenter.WalletPresenter;
import com.js.shipper.ui.wallet.presenter.contract.WalletContract;
import com.js.frame.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class WalletActivity extends BaseActivity<WalletPresenter> implements WalletContract.View {

    @BindView(R.id.wallet_money)
    TextView mMoney;
    @BindView(R.id.wallet_withdraw)
    TextView walletWithdraw;
    @BindView(R.id.wallet_bail)
    TextView mBail;

    AccountInfo mAccountInfo;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAccountInfo();
    }

    @OnClick({R.id.wallet_withdraw, R.id.wallet_recharge, R.id.bill_detail_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_withdraw://提现
                WithdrawActivity.action(mContext, 2, mAccountInfo.getBalance());
                break;
            case R.id.wallet_recharge://充值
                RechargeActivity.action(mContext);
                break;
            case R.id.bill_detail_layout://账单明细
                BillActivity.action(mContext, 0);
                break;
        }
    }

    public static void action(Context context) {
        context.startActivity(new Intent(context, WalletActivity.class));
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
        return R.layout.activity_wallet;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的钱包");
    }

    @Override
    public void onAccountInfo(AccountInfo accountInfo) {
        mAccountInfo = accountInfo;
        mMoney.setText(String.valueOf(accountInfo.getBalance()));
    }
}

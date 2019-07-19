package com.js.shipper.ui.wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.PayInfo;
import com.js.shipper.model.bean.PayRouter;
import com.js.shipper.ui.order.activity.OrdersActivity;
import com.js.shipper.ui.wallet.adapter.PayAdapter;
import com.js.shipper.ui.wallet.presenter.PayPresenter;
import com.js.shipper.ui.wallet.presenter.contract.PayContract;
import com.js.shipper.util.pay.PayResult;
import com.js.shipper.widget.adapter.Divider;
import com.js.shipper.wxapi.WXPayEntryActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-13.
 */
public class PayActivity extends BaseActivity<PayPresenter> implements PayContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.fee)
    TextView mFee;

    private List<PayRouter> mPayRouters;
    private int channelType = 0;
    private int routerId = 0;

    private PayAdapter mAdapter;
    private static final int SDK_PAY_FLAG = 99;
    private double fee;
    private String orderNo;


    public static void action(Context context, double fee, String orderNo) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("fee", fee);
        intent.putExtra("orderNo", orderNo);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        fee = getIntent().getDoubleExtra("fee", 0);
        orderNo = getIntent().getStringExtra("orderNo");
    }

    private void initData() {
        mPresenter.getPayRouter();
    }


    private void initView() {
        mFee.setText(String.valueOf(fee));
        initAdapter();
    }


    private void initAdapter() {
        mAdapter = new PayAdapter(R.layout.item_pay_type, mPayRouters);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(this);
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
        return R.layout.activity_pay;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("在线支付");
    }


    @OnClick({R.id.wallet_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_pay:
                if (channelType == Const.CHANNEL_ACCOUNT_PAY) {
                    mPresenter.payAccount(orderNo);
                } else {
                    //交易类型, 1账户充值, 5运费支付，10运力端保证金，11货主端保证金
                    mPresenter.payOrder(5, channelType, fee, routerId, orderNo);
                }
                break;
        }
    }


    public void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付业务：入参app_id
     */
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onPayOrder(PayInfo payInfo) {
        switch (channelType) {
            case Const.CHANNEL_ALI_PAY:
                aliPay(payInfo.getOrderInfo());
                break;
            case Const.CHANNEL_WX_PAY:
                wxPay(payInfo.getOrderInfo());
                break;
        }
    }

    private void wxPay(String orderInfo) {
        if (TextUtils.isEmpty(orderInfo)) {
            toast("获取支付信息失败");
            return;
        }
        Intent intent = new Intent(mContext, WXPayEntryActivity.class);
        intent.putExtra("orderInfo", orderInfo);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 888) { //微信支付成功
            finish();
        }
    }

    @Override
    public void onPayRouter(List<PayRouter> payRouters) {
        if (payRouters != null && payRouters.size() > 0) {
            channelType = payRouters.get(0).getChannelType();
            routerId = payRouters.get(0).getRouteId();
            payRouters.get(0).setChecked(true);
            PayRouter payRouter = new PayRouter();
            payRouter.setChannelType(Const.CHANNEL_ACCOUNT_PAY);
            payRouter.setRouteId(1);
            payRouters.add(payRouter);
            mAdapter.setNewData(payRouters);
        }
    }

    @Override
    public void onPayAccount(Boolean isOk) {
        if (isOk) {
            toast("支付成功");
            OrdersActivity.action(mContext, 0);
            finish();
        } else {
            toast("支付失败");
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<PayRouter> payRouters = adapter.getData();
        channelType = payRouters.get(position).getChannelType();
        routerId = payRouters.get(position).getRouteId();
        for (int i = 0; i < payRouters.size(); i++) {
            if (i == position) {
                payRouters.get(position).setChecked(true);
            } else {
                payRouters.get(i).setChecked(false);
            }
        }
        mAdapter.setNewData(payRouters);
    }

}

package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.order.presenter.OrderDetailPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderDetailContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {


    @BindView(R.id.order_number)
    TextView mOrderNo;
    @BindView(R.id.send_address)
    TextView mSendAddress;
    @BindView(R.id.end_address)
    TextView mEndAddress;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.car_info)
    TextView mCarInfo;
    @BindView(R.id.good_type)
    TextView mGoodType;
    @BindView(R.id.use_car_type)
    TextView mUseCarType;
    @BindView(R.id.pay_way)
    TextView mPayWay;
    @BindView(R.id.fee)
    TextView mFee;
    @BindView(R.id.pay_type)
    TextView mPayType;
    @BindView(R.id.remark)
    TextView mRemark;
    @BindView(R.id.receiver_name)
    TextView mReceiverName;
    @BindView(R.id.receiver_phone)
    TextView mReceiverPhone;
    @BindView(R.id.control_navigate)
    TextView mNavigate;
    @BindView(R.id.control_positive)
    TextView mPositive;
    @BindView(R.id.control_layout)
    LinearLayout controlLayout;
    private long orderId;
    private int status;

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
        status = orderBean.getState();
        //1发布中，2待司机接单，3待司机确认，4待支付，5待司机接货, 6待收货，7待评价，8已完成，9已取消，10已关闭
        mOrderNo.setText("订单编号：" + orderBean.getOrderNo());
        mSendAddress.setText(orderBean.getSendAddress());
        mEndAddress.setText(orderBean.getReceiveAddress());
        mTime.setText(orderBean.getLoadingTime());
        mCarInfo.setText(orderBean.getGoodsVolume() + "方/"
                + orderBean.getGoodsWeight() + "吨");
        mGoodType.setText(orderBean.getGoodsType());
        mUseCarType.setText(orderBean.getUseCarTypeName());
        switch (orderBean.getPayWay()) {
            case 1:
                mPayWay.setText("线上支付");
                break;
            case 2:
                mPayWay.setText("线下支付");
                break;
        }

        switch (orderBean.getPayType()) {
            case 1:
                mPayType.setText("到付");
                break;
            case 2:
                mPayType.setText("现付");
                break;
        }

        switch (orderBean.getFeeType()) {
            case 1:
                mFee.setText(String.valueOf(orderBean.getFee()));
                break;
            case 2:
                mFee.setText("电议");
                break;
        }
        mRemark.setText(orderBean.getRemark());
        mReceiverName.setText(orderBean.getReceiveName());
        mReceiverPhone.setText(orderBean.getReceiveMobile());
        switch (orderBean.getState()) {
            case 2:
                mPositive.setText("再发一次");
                mNavigate.setText("取消发布");
                break;

        }
    }

    @Override
    public void onCancelOrder(boolean isOk) {
        if (isOk) {
            toast("取消订单成功");
            MainActivity.action(mContext);
        } else {
            toast("取消订单失败");
        }
    }

    @Override
    public void onAgainOrder(boolean isOk) {
        if (isOk) {
            toast("下单成功");
        } else {
            toast("下单失败");
        }
    }


    @OnClick({R.id.control_navigate, R.id.control_positive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_navigate:
                switch (status) {
                    case 2://取消发布
                        mPresenter.cancelOrder(orderId);
                        break;

                }
                break;
            case R.id.control_positive:
                switch (status) {
                    case 2://再发一次
                        mPresenter.againOrder(orderId);
                        break;

                }
                break;
        }
    }
}

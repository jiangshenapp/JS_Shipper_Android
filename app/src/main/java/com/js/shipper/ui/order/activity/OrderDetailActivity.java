package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.order.presenter.OrderDetailPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderDetailContract;
import com.js.shipper.ui.wallet.activity.PayActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
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
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.detail_order_status)
    TextView mOrderStatus;
    @BindView(R.id.status_ing_layout)
    LinearLayout mIngLayout;
    @BindView(R.id.driver_count)
    TextView mDriverCount;
    private long orderId;
    private int status;
    private MenuItem menuItem;
    private OrderBean mOrderBean;

    public static void action(Context context, long orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initView() {
        mRefresh.autoRefresh();
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
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
        this.mOrderBean = orderBean;
        status = orderBean.getState();
        //1发布中，2待司机接单，3待司机确认，4待支付，5待司机接货, 6待收货，7待评价，8已完成，9已取消，10已关闭
        mOrderNo.setText("订单编号：" + orderBean.getOrderNo());
        mOrderStatus.setText(orderBean.getStateNameConsignor());
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
        if (!TextUtils.isEmpty(orderBean.getRemark())) {
            mRemark.setVisibility(View.VISIBLE);
            mRemark.setText(orderBean.getRemark());
        } else {
            mRemark.setVisibility(View.GONE);
        }
        mReceiverName.setText(orderBean.getReceiveName());
        mReceiverPhone.setText(orderBean.getReceiveMobile());

        if (orderBean.getState() == 1) {
            mDriverCount.setText("已为您通知" + orderBean.getDriverNum() + "个司机");
            mIngLayout.setVisibility(View.VISIBLE);
        } else {
            mIngLayout.setVisibility(View.GONE);
        }
        setBottom(orderBean.getState());

    }

    private void setBottom(int state) {
        mPositive.setClickable(true);
        mPositive.setBackgroundColor(getResources().getColor(R.color._ECA73F));
        menuItem.setVisible(false);
        switch (state) {
            case 2:
                controlLayout.setVisibility(View.VISIBLE);
                mPositive.setText("再发一次");
                mNavigate.setText("取消发布");
                break;
            case 3:
                menuItem.setVisible(true);
                mPositive.setClickable(false);
                mPositive.setText("立即支付");
                mPositive.setBackgroundColor(getResources().getColor(R.color._B4B4B4));
                mNavigate.setText("取消发布");
                controlLayout.setVisibility(View.VISIBLE);
                break;
            case 4:
                menuItem.setVisible(true);
                mPositive.setText("立即支付");
                mNavigate.setText("取消发布");
                controlLayout.setVisibility(View.VISIBLE);
                break;
            case 5:
                mPositive.setText("确认收货");
                mNavigate.setText("查看路线");
                controlLayout.setVisibility(View.VISIBLE);
                break;
            case 9:
                controlLayout.setVisibility(View.GONE);
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
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }


    @OnClick({R.id.control_navigate, R.id.control_positive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_navigate:
                switch (status) {
                    case 2://取消发布
                    case 3:
                    case 4:
                        mPresenter.cancelOrder(orderId);
                        break;
                    case 5://查看路线
                        break;

                }
                break;
            case R.id.control_positive:
                switch (status) {
                    case 2://再发一次
                        SubmitOrderActivity.action(mContext, 0, mOrderBean);
                        break;
                    case 4://支付
                        if (mOrderBean.getFeeType() == 2) {
                            toast("请修改价格");
                            return;
                        }

                        if (mOrderBean.getPayWay() == 1) {
                            PayActivity.action(mContext, mOrderBean.getFee(), mOrderBean.getOrderNo());
                        } else {

                        }
                        break;
                    case 5:

                        break;

                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItem = menu.add(Menu.NONE, R.id.order_edit, Menu.FIRST, "修改");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order_edit:
                Intent intent = new Intent(mContext, OrderEditActivity.class);
                intent.putExtra("order", mOrderBean);
                startActivityForResult(intent, Const.CODE_REQ);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CODE_REQ) {
            initData();
        }
    }
}

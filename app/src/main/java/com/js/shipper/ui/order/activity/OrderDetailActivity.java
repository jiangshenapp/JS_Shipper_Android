package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.mapapi.model.LatLng;
import com.base.frame.view.BaseActivity;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.ui.message.chat.EaseChatActivity;
import com.js.shipper.util.glide.CommonGlideImageLoader;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.event.CommentEvent;
import com.js.shipper.model.request.OrderComment;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.order.presenter.OrderDetailPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderDetailContract;
import com.js.shipper.ui.wallet.activity.PayActivity;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.MapUtils;
import com.js.shipper.widget.dialog.CommentFragment;
import com.js.shipper.widget.view.RatingBar;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;

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
    @BindView(R.id.good_name)
    TextView mGoodName;
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
    @BindView(R.id.detail_avatar)
    ImageView mDetailAvatar;
    @BindView(R.id.detail_name)
    TextView mDetailName;
    @BindView(R.id.detail_phone)
    TextView mDetailPhone;
    @BindView(R.id.detail_call)
    ImageView mDetailCall;
    @BindView(R.id.detail_chat)
    ImageView mDetailChat;
    @BindView(R.id.driver_info_layout)
    LinearLayout mDriverInfoLayout;
    @BindView(R.id.status_ing_layout)
    LinearLayout mIngLayout;
    @BindView(R.id.driver_count)
    TextView mDriverCount;
    @BindView(R.id.bail)
    TextView mBail;
    @BindView(R.id.pack_type)
    TextView mPackType;
    @BindView(R.id.receipt_layout)
    LinearLayout mReceiptLayout;
    @BindView(R.id.receipt_title)
    TextView mReceiptTitle;
    @BindView(R.id.detail_img1)
    ImageView mImg1;
    @BindView(R.id.detail_img2)
    ImageView mImg2;
    @BindView(R.id.detail_img3)
    ImageView mImg3;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;

    private long orderId;
    private int status;
    private MenuItem menuItem;
    private OrderBean mOrderBean;
    private String[] items = {"百度地图", "高德地图"};
    private CommentFragment mCommentFragment;

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
        mCommentFragment = CommentFragment.getInstance();
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
        //1发布中，2待司机接单，3待司机确认，4待支付，5待司机接货, 6待收货，7待确认收货，8待回单收到确认，9待评价，10已完成，11已取消，12已关闭）
        mOrderNo.setText("订单编号：" + orderBean.getOrderNo());
        mOrderStatus.setText(orderBean.getStateNameConsignor());
        mSendAddress.setText(orderBean.getSendAddress());
        mEndAddress.setText(orderBean.getReceiveAddress());
        mTime.setText(orderBean.getLoadingTime());
        String info = "";
        if (!TextUtils.isEmpty(orderBean.getCarModelName())) {
            info += orderBean.getCarModelName();
        }
        if (!TextUtils.isEmpty(orderBean.getCarLengthName())) {
            info += orderBean.getCarLengthName();
        }
        if (orderBean.getGoodsVolume() != 0) {
            info += "/" + orderBean.getGoodsVolume() + "方";
        }
        if (orderBean.getGoodsWeight() != 0) {
            info += "/" + orderBean.getGoodsWeight() + "吨";
        }
        mCarInfo.setText(info);
        mGoodName.setText(orderBean.getGoodsName());
        mUseCarType.setText(orderBean.getUseCarType());
        mBail.setText(String.valueOf(orderBean.getDeposit()));
        mPackType.setText(orderBean.getPackType());
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

        if (orderBean.getState() == 1 || orderBean.getState() == 2) {
            mIngLayout.setVisibility(View.VISIBLE);
            mDriverInfoLayout.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(orderBean.getDriverNum())) {
                mDriverCount.setText(String.format("已为您通知%s个司机", orderBean.getDriverNum()));
            } else {
                mDriverCount.setText("已为您通知个司机");
            }
        } else {
            mIngLayout.setVisibility(View.GONE);
            mDriverInfoLayout.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, com.base.http.global.Const.IMG_URL + orderBean.getDriverAvatar()
                    , mDetailAvatar, mContext.getResources().getDrawable(R.mipmap.ic_center_driver_head_land));
            if (!TextUtils.isEmpty(orderBean.getDotName())) { //网点接单
                mDetailPhone.setVisibility(View.GONE);
                mDetailName.setText(orderBean.getDotName());
            } else { //司机接单
                mDetailPhone.setVisibility(View.VISIBLE);
                mDetailName.setText(orderBean.getDriverName());
                mDetailPhone.setText(orderBean.getDriverPhone());
                mRatingBar.setClickable(false);
                mRatingBar.setStar(orderBean.getScore());
            }
        }
        setBottom(orderBean.getState());
    }

    private void setBottom(int state) {
        //(1发布中，2待司机接单，3待司机确认，4待支付，5待司机接货, 6待收货，7待确认收货，8待回单收到确认，9待评价，10已完成，11已取消，12已关闭）
        controlLayout.setVisibility(View.VISIBLE);
        mPositive.setClickable(true);
        mPositive.setBackgroundColor(getResources().getColor(R.color._ECA73F));
        switch (state) {
            case 1:
            case 2:
                mPositive.setText("再发一次");
                mNavigate.setText("取消发布");
                break;
            case 3:
                menuItem.setTitle("修改");
                mPositive.setClickable(false);
                mPositive.setText("立即支付");
                mPositive.setBackgroundColor(getResources().getColor(R.color._B4B4B4));
                mNavigate.setText("取消发布");
                break;
            case 4:
                menuItem.setTitle("修改");
                mPositive.setText("立即支付");
                mNavigate.setText("取消发布");
                break;
            case 5:
                mPositive.setText("取消发布");
                mNavigate.setVisibility(View.GONE);
                break;
            case 6:
            case 7:
                mPositive.setText("确认收货");
                mNavigate.setText("查看路线");
                break;
            case 8:
                mPositive.setText("回单收到确认");
                mNavigate.setText("查看路线");
                break;
            case 9:
                mPositive.setText("评价");
                mNavigate.setText("查看路线");
                break;
            case 10:
                mPositive.setText("重新发货");
                mNavigate.setText("查看路线");
                break;
            case 11:
                mPositive.setText("重新发货");
                mNavigate.setVisibility(View.GONE);
            case 12:
                controlLayout.setVisibility(View.GONE);
                break;
        }
        if (!TextUtils.isEmpty(mOrderBean.getCommentImage1())) {
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mOrderBean.getCommentImage1(), mImg1);
        }
        if (!TextUtils.isEmpty(mOrderBean.getCommentImage2())) {
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mOrderBean.getCommentImage2(), mImg2);
        }
        if (!TextUtils.isEmpty(mOrderBean.getCommentImage3())) {
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mOrderBean.getCommentImage3(), mImg3);
        }
        if (state > 7) {
            mReceiptTitle.setVisibility(View.VISIBLE);
            mReceiptLayout.setVisibility(View.VISIBLE);
        } else {
            mReceiptTitle.setVisibility(View.GONE);
            mReceiptLayout.setVisibility(View.GONE);
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

    @Override
    public void onConfirmOrder(boolean isOk) {
        if (isOk) {
            toast("确认收货成功");
            mPresenter.getOrderDetail(orderId);
        } else {
            toast("确认收货失败");
        }
    }

    @Override
    public void onReceiptOrder(boolean isOk) {
        if (isOk) {
            toast("确认成功");
            mPresenter.getOrderDetail(orderId);
        } else {
            toast("确认失败");
        }
    }

    @Override
    public void onCommentOrder(boolean isOk) {
        if (isOk) {
            toast("评价成功");
            mCommentFragment.dismiss();
            mPresenter.getOrderDetail(orderId);
        } else {
            toast("评价失败");
        }
    }

    @OnClick({R.id.control_navigate, R.id.control_positive, R.id.detail_send_navigate, R.id.detail_arrive_navigate})
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
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        break;
                }
                break;
            case R.id.control_positive:
                switch (status) {
                    case 1:
                    case 2://再发一次
                        SubmitOrderActivity.action(mContext, mOrderBean.getMatchSubscriberId(), mOrderBean);
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
                        mPresenter.cancelOrder(orderId);
                        break;
                    case 6://确认收货
                    case 7:
                        mPresenter.confirmOrder(orderId);
                        break;
                    case 8://回单收到确认
                        mPresenter.receiptOrder(orderId);
                        break;
                    case 9://评价
                        if (!mCommentFragment.isAdded()) {
                            mCommentFragment.show(getSupportFragmentManager(), "comment");
                        }
                        break;
                    case 10:
                    case 11://重新发货
                        SubmitOrderActivity.action(mContext, 0, mOrderBean);
                        break;
                }
                break;

            case R.id.detail_send_navigate:
                if (App.getInstance().mLocation == null) {
                    toast("定位失败");
                    return;
                }
                Gson gson = new Gson();
                LatLng latLng = new LatLng(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude());
                showSelectDialog(latLng, gson.fromJson(mOrderBean.getSendPosition(), LatLng.class), mOrderBean.getSendAddress());
                break;
            case R.id.detail_arrive_navigate:
                if (App.getInstance().mLocation == null) {
                    toast("定位失败");
                    return;
                }
                Gson gson1 = new Gson();
                LatLng latLng1 = new LatLng(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude());
                showSelectDialog(latLng1, gson1.fromJson(mOrderBean.getReceivePosition(), LatLng.class), mOrderBean.getReceiveAddress());
                break;
        }
    }

    @Subscribe
    public void onEvent(CommentEvent commentEvent) {
        OrderComment orderComment = new OrderComment();
        orderComment.setScore((int) commentEvent.score);
        mPresenter.commentOrder(orderComment, orderId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItem = menu.add(Menu.NONE, R.id.order_edit, Menu.FIRST, "再发一次");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order_edit:
                if (item.getTitle().equals("修改")) {
                    Intent intent = new Intent(mContext, OrderEditActivity.class);
                    intent.putExtra("order", mOrderBean);
                    startActivityForResult(intent, Const.CODE_REQ);
                }
                if (item.getTitle().equals("再发一次")) {
                    SubmitOrderActivity.action(mContext, mOrderBean.getMatchSubscriberId(), mOrderBean);
                }
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

    public void showSelectDialog(LatLng start, LatLng end, String dName) {
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) { //百度地图
                            MapUtils.startBaidu(mContext, start, end, dName);
                        } else {//高德地图
                            MapUtils.startGaode(mContext, start, end, dName);
                        }
                    }
                })
                .setNegative("取消", null)
                .show();
    }

    @OnClick({R.id.detail_call, R.id.detail_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_call: //打电话
                AppUtils.callPhone(mContext, mOrderBean.getDriverPhone());
                break;
            case R.id.detail_chat: //聊天
                if (!TextUtils.isEmpty(mOrderBean.getDriverPhone())) {
                    EaseChatActivity.action(mContext, EaseConstant.CHATTYPE_SINGLE, mOrderBean.getDriverPhone());
                }
                break;
        }
    }
}

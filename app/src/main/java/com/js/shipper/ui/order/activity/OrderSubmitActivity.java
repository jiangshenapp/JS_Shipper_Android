package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.request.AddStepTwo;
import com.js.shipper.ui.order.presenter.OrderSubmitPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitActivity extends BaseActivity<OrderSubmitPresenter> implements OrderSubmitContract.View {


    @BindView(R.id.good_weight)
    EditText mGoodWeight;
    @BindView(R.id.good_volume)
    EditText mGoodVolume;
    @BindView(R.id.good_type)
    TextView mGoodType;
    @BindView(R.id.ship_time)
    TextView mShipTime;
    @BindView(R.id.car_type)
    TextView mCarType;
    @BindView(R.id.image_1)
    LinearLayout mImage1;
    @BindView(R.id.image_2)
    LinearLayout mImage2;
    @BindView(R.id.remark)
    EditText mRemark;
    @BindView(R.id.fee)
    EditText mFee;
    @BindView(R.id.fee_img)
    ImageView mFeeImg;
    @BindView(R.id.power_img)
    ImageView mPowerImg;
    @BindView(R.id.pay_way)
    RadioGroup mPayWay;
    @BindView(R.id.pay_type)
    RadioGroup mPayType;

    private long orderId;
    private String img1Url;
    private String img2Url;


    public static void action(Context context, long orderId) {
        Intent intent = new Intent(context, OrderSubmitActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
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
        return R.layout.activity_order_submit;
    }

    @Override
    public void setActionBar() {
        setTitle("确认订单");
    }


    @OnClick({R.id.good_type_layout, R.id.ship_time_layout, R.id.car_type_layout, R.id.image_1, R.id.image_2, R.id.fee_mine_layout, R.id.power_layout, R.id.specified_release, R.id.release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.good_type_layout:

                break;
            case R.id.ship_time_layout:

                break;
            case R.id.car_type_layout:

                break;
            case R.id.image_1:

                break;
            case R.id.image_2:

                break;
            case R.id.fee_mine_layout:

                break;
            case R.id.power_layout:

                break;
            case R.id.specified_release:

                break;
            case R.id.release:

                break;
        }
    }


    public void confirm() {
        String weight = mGoodWeight.getText().toString().trim();
        String volume = mGoodVolume.getText().toString().trim();
        String remark = mRemark.getText().toString().trim();
        String type = mGoodType.getText().toString().trim();
        String time = mShipTime.getText().toString().trim();
        String carType = mCarType.getText().toString().trim();

        if (TextUtils.isEmpty(weight)) {
            return;
        }

        if (TextUtils.isEmpty(volume)) {
            return;
        }

        if (TextUtils.isEmpty(remark)) {
            return;
        }

        if (TextUtils.isEmpty(type)) {
            return;
        }

        if (TextUtils.isEmpty(time)) {
            return;
        }

        if (TextUtils.isEmpty(carType)) {
            return;
        }
        AddStepTwo addStepTwo = new AddStepTwo();
        addStepTwo.setId(orderId);
        addStepTwo.setGoodsWeight(Integer.parseInt(weight));
        addStepTwo.setGoodsVolume(Integer.parseInt(volume));
        addStepTwo.setRemark(remark);
        addStepTwo.setGoodsType(type);
        addStepTwo.setLoadingTime(time);
        addStepTwo.setUseCarType(carType);
        mPresenter.submit(addStepTwo);
    }

    @Override
    public void onSubmit() {
        OrdersActivity.action(mContext, 0);
    }
}

package com.js.shipper.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.ui.main.presenter.ShipPresenter;
import com.js.shipper.ui.main.presenter.contract.ShipContract;
import com.js.shipper.ui.order.activity.OrderSubmitActivity;
import com.js.shipper.ui.ship.activity.SelectAddressActivity;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/30.
 * 发货
 */
public class ShipFragment extends BaseFragment<ShipPresenter> implements ShipContract.View {


    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.ship_start_address)
    TextView mStartAddress;
    @BindView(R.id.ship_end_address)
    TextView mEndAddress;
    @BindView(R.id.ship_mileage)
    TextView mMileage;
    @BindView(R.id.ship_car_extent)
    TextView mCarExtent;
    @BindView(R.id.ship_car_type)
    TextView mCarType;
    @BindView(R.id.ship_submit)
    TextView mSubmit;



    private String startAddress;
    private String endAddress;

    public static ShipFragment newInstance() {
        return new ShipFragment();
    }


    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ship;
    }


    @Override
    protected void init() {

    }

    @OnClick({R.id.ship_start_layout, R.id.ship_end_layout,
            R.id.ship_car_extent_layout, R.id.ship_car_type_layout,
            R.id.ship_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ship_start_layout://发货地址
                SelectAddressActivity.action(mContext,0);
                break;
            case R.id.ship_end_layout://收货地址
                SelectAddressActivity.action(mContext,1);
                break;
            case R.id.ship_car_extent_layout://车长
                break;
            case R.id.ship_car_type_layout://车型
                break;
            case R.id.ship_submit://发货

                if (TextUtils.isEmpty(startAddress)){
                    toast("请输入发货地址");
                    return;
                }

                if (TextUtils.isEmpty(endAddress)){
                    toast("请输入收货地址");
                    return;
                }
                AddStepOne addStepOne = new AddStepOne();
                mPresenter.addStepOne(addStepOne);

                break;
        }
    }

    @Override
    public void onStepOne() {
        OrderSubmitActivity.action(mContext);
    }
}

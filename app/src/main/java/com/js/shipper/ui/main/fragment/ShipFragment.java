package com.js.shipper.ui.main.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.ShipBean;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.ui.main.presenter.ShipPresenter;
import com.js.shipper.ui.main.presenter.contract.ShipContract;
import com.js.shipper.ui.order.activity.OrderSubmitActivity;
import com.js.shipper.ui.ship.activity.SelectAddressActivity;
import com.youth.banner.Banner;

import java.text.DecimalFormat;

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
    private ShipBean mSendShip;
    private ShipBean mEndShip;
    private Gson mGson = new Gson();
    private DecimalFormat df = new DecimalFormat("#####0.0");

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
                Intent startIntent = new Intent(mContext, SelectAddressActivity.class);
                startIntent.putExtra("type", 0);
                startActivityForResult(startIntent, Const.CODE_REQ);
                break;
            case R.id.ship_end_layout://收货地址
                Intent endIntent = new Intent(mContext, SelectAddressActivity.class);
                endIntent.putExtra("type", 1);
                startActivityForResult(endIntent, Const.CODE_REQ);
                break;
            case R.id.ship_car_extent_layout://车长

                break;
            case R.id.ship_car_type_layout://车型

                break;
            case R.id.ship_submit://发货

                if (TextUtils.isEmpty(mStartAddress.getText().toString())) {
                    toast("请输入发货地址");
                    return;
                }

                if (TextUtils.isEmpty(mEndAddress.getText().toString())) {
                    toast("请输入收货地址");
                    return;
                }
                AddStepOne addStepOne = new AddStepOne();
                addStepOne.setCarLength("1.5米");
                addStepOne.setCarModel("卡车");
                addStepOne.setReceiveAddress(mEndShip.getAddress());
                addStepOne.setReceiveAddressCode(String.valueOf(mEndShip.getAddressCode()));
                addStepOne.setReceiveMobile(mEndShip.getPhone());
                addStepOne.setReceivePosition(mEndShip.getPosition());
                addStepOne.setSendAddress(mSendShip.getAddress());
                addStepOne.setSendMobile(mSendShip.getPhone());
                addStepOne.setSendAddressCode(String.valueOf(mSendShip.getAddressCode()));
                addStepOne.setSendName(mSendShip.getName());
                addStepOne.setSendPosition(mSendShip.getPosition());
                mPresenter.addStepOne(addStepOne);

                break;
        }
    }

    @Override
    public void onStepOne(long orderId) {
        OrderSubmitActivity.action(mContext,orderId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CODE_REQ) {
            if (data != null) {
                ShipBean shipBean = data.getParcelableExtra("ship");
                switch (shipBean.getType()) {
                    case 0:
                        mSendShip = shipBean;
                        mStartAddress.setText(shipBean.getAddress());
                        break;
                    case 1:
                        mEndShip = shipBean;
                        mEndAddress.setText(shipBean.getAddress());
                        break;
                }
            }
            if (mSendShip != null && mEndShip != null) {
                double distance = DistanceUtil.getDistance(mGson.fromJson(mSendShip.getPosition(), LatLng.class), mGson.fromJson(mEndShip.getPosition(), LatLng.class));
                mMileage.setText("总里程" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米"));
            }

        }
    }
}

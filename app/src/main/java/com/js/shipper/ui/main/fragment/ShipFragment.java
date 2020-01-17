package com.js.shipper.ui.main.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.base.frame.view.SimpleWebActivity;
import com.google.gson.Gson;
import com.base.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.BannerBean;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.model.bean.ServiceBean;
import com.js.shipper.model.bean.ShipBean;
import com.js.shipper.model.event.DictSelectEvent;
import com.js.shipper.model.event.RemoveUserEvent;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.presenter.DictPresenter;
import com.js.shipper.presenter.contract.DictContract;
import com.js.shipper.ui.main.presenter.ServicePresenter;
import com.js.shipper.ui.main.presenter.ShipPresenter;
import com.js.shipper.ui.main.presenter.contract.ServiceContract;
import com.js.shipper.ui.main.presenter.contract.ShipContract;
import com.js.shipper.ui.order.activity.OrderSubmitActivity;
import com.js.shipper.ui.order.activity.OrdersActivity;
import com.js.shipper.ui.ship.activity.SelectAddressActivity;
import com.js.shipper.util.SpManager;
import com.js.shipper.util.glide.GlideImageLoader;
import com.js.shipper.widget.window.ItemWindow;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/30.
 * 发货
 */
public class ShipFragment extends BaseFragment<ShipPresenter> implements ShipContract.View, DictContract.View, ServiceContract.View {

    @BindView(R.id.title_layout)
    FrameLayout mTitleLayout;
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
    private ShipBean mSendShip = new ShipBean();
    private ShipBean mEndShip = new ShipBean();
    private Gson mGson = new Gson();
    private DecimalFormat df = new DecimalFormat("#####0.0");
    private ItemWindow mTypeWindow;
    private ItemWindow mLengthWindow;
    private String typeStr;
    private String lengthStr;

    @Inject
    DictPresenter mDictPresenter;
    @Inject
    ServicePresenter mServicePresenter;

    private List<String> imgPaths;
    private List<BannerBean> mBannerBeans;

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
        mDictPresenter.attachView(this);
        mServicePresenter.attachView(this);
        initBanner();
        initView();
        initData();
    }

    private void initData() {
        mDictPresenter.getDictByType(Const.DICT_CAR_TYPE_NAME);
        mDictPresenter.getDictByType(Const.DICT_LENGTH_NAME);
        mServicePresenter.getBannerList(2);
    }

    private void initView() {
        mTypeWindow = new ItemWindow(mContext, Const.DICT_CAR_TYPE);
        mLengthWindow = new ItemWindow(mContext, Const.DICT_LENGTH);
    }

    public void initBanner() {
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = mBannerBeans.get(position);
                SimpleWebActivity.action(getActivity(), bannerBean.getUrl(), bannerBean.getTitle());
            }
        });
        mBanner.setImageLoader(new GlideImageLoader());
    }

    @OnClick({R.id.ship_start_layout, R.id.ship_end_layout,
            R.id.ship_car_extent_layout, R.id.ship_car_type_layout,
            R.id.ship_submit, R.id.orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ship_start_layout://发货地址
                Intent startIntent = new Intent(mContext, SelectAddressActivity.class);
                startIntent.putExtra("type", 0);
                startIntent.putExtra("ship", mSendShip);
                startActivityForResult(startIntent, Const.CODE_REQ);
                break;
            case R.id.ship_end_layout://收货地址
                Intent endIntent = new Intent(mContext, SelectAddressActivity.class);
                endIntent.putExtra("type", 1);
                endIntent.putExtra("ship", mEndShip);
                startActivityForResult(endIntent, Const.CODE_REQ);
                break;
            case R.id.ship_car_extent_layout://车长
                mLengthWindow.showAsDropDown(mTitleLayout, 0, 0);
                break;
            case R.id.ship_car_type_layout://车型
                mTypeWindow.showAsDropDown(mTitleLayout, 0, 0);
                break;

            case R.id.orders:
                OrdersActivity.action(mContext, 0);
                break;
            case R.id.ship_submit://发货

                if (TextUtils.isEmpty(SpManager.getInstance(mContext).getSP("token"))) {
                    ARouter.getInstance().build("/user/login").navigation();
                    return;
                }

                if (TextUtils.isEmpty(mStartAddress.getText().toString())) {
                    toast("请选择发货地址");
                    return;
                }

                if (TextUtils.isEmpty(mEndAddress.getText().toString())) {
                    toast("请选择收货地址");
                    return;
                }

                AddStepOne addStepOne = new AddStepOne();
                addStepOne.setCarLength(lengthStr);
                addStepOne.setCarModel(typeStr);
                addStepOne.setReceiveAddress(mEndShip.getAddress());
                addStepOne.setReceiveAddressCode(String.valueOf(mEndShip.getAddressCode()));
                addStepOne.setReceiveMobile(mEndShip.getPhone());
                addStepOne.setReceivePosition(mEndShip.getPosition());
                addStepOne.setReceiveName(mEndShip.getName());
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
        OrderSubmitActivity.action(mContext, mSendShip.getStreetCode(), mEndShip.getStreetCode(), orderId);
    }

    @Subscribe(sticky = true)
    public void onEvent(RemoveUserEvent event){
        mSendShip = new ShipBean();
        mEndShip = new ShipBean();
        if (mStartAddress!=null){
            mStartAddress.setText("");
        }
        if (mEndAddress!=null){
            mEndAddress.setText("");
        }
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
            if (mSendShip != null &&!TextUtils.isEmpty(mSendShip.getPosition())&& mEndShip != null&&!TextUtils.isEmpty(mEndShip.getPosition())) {
                double distance = DistanceUtil.getDistance(mGson.fromJson(mSendShip.getPosition(), LatLng.class), mGson.fromJson(mEndShip.getPosition(), LatLng.class));
                mMileage.setText("总里程" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米"));
            }else {
                mMileage.setText("总里程0公里");
            }
        }
    }

    @Subscribe
    public void onEvent(DictSelectEvent event) {
        switch (event.type) {
            case Const.DICT_LENGTH:
                mCarExtent.setText(event.labelStr);
                lengthStr = event.valueStr;
                break;
            case Const.DICT_CAR_TYPE:
                mCarType.setText(event.labelStr);
                typeStr = event.valueStr;
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDictPresenter != null) {
            mDictPresenter.detachView();
        }
        if (mServicePresenter != null) {
            mServicePresenter.detachView();
        }
    }

    @Override
    public void onDictByType(String type, List<DictBean> dictBeans) {
        switch (type) {
            case Const.DICT_CAR_TYPE_NAME:
                mTypeWindow.setData(dictBeans);
                break;
            case Const.DICT_LENGTH_NAME:
                mLengthWindow.setData(dictBeans);
                break;
        }
    }

    @Override
    public void onFirstDictByType(String type, DictBean dictBean) {

    }

    @Override
    public void onBannerListFail() {

    }

    @Override
    public void onBannerList(List<BannerBean> bannerBeans) {
        mBannerBeans = bannerBeans;
        if (mBannerBeans.size() == 0) {
            mBanner.setVisibility(View.GONE);
        } else {
            mBanner.setVisibility(View.VISIBLE);
            imgPaths = new ArrayList<>();
            for (int i = 0; i < mBannerBeans.size(); i++) {
                BannerBean bannerBean = mBannerBeans.get(i);
                imgPaths.add(com.base.http.global.Const.IMG_URL()  + bannerBean.getImage());
            }
            mBanner.setImages(imgPaths);
            mBanner.start();
        }
    }

    @Override
    public void onServiceList(List<ServiceBean> mServiceBeans) {

    }


}

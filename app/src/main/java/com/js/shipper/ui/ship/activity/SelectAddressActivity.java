package com.js.shipper.ui.ship.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.LatLngBean;
import com.js.shipper.model.bean.ShipBean;
import com.js.shipper.ui.ship.presenter.SelectAddressPresenter;
import com.js.shipper.ui.ship.presenter.contract.SelectAddressContrat;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/5/13.
 */
public class SelectAddressActivity extends BaseActivity<SelectAddressPresenter> implements SelectAddressContrat.View {


    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.ship_address_name)
    TextView mAddressName;
    @BindView(R.id.ship_address)
    TextView mAddress;
    @BindView(R.id.select_location)
    ImageView mLocation;
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.receiver_info)
    TextView receiverInfo;
    @BindView(R.id.confirm)
    TextView mConfirm;


    private int type;//0。发货；1.收货
    private BaiduMap mBaiduMap;
    private GeoCoder mCoder;
    private String city;
    private ShipBean mShip = new ShipBean();


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
        return R.layout.activity_select_address;
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
        mShip.setType(type);
    }

    private void initView() {
        initMap();
        initLocation();
        switch (type) {
            case 0:
                receiverInfo.setText("发货人信息");
                mConfirm.setText("确认发货地址");
                break;
            case 1:
                receiverInfo.setText("收货人信息");
                mConfirm.setText("确认收货地址");
                break;
        }

    }

    private void initLocation() {
//        mMapView.getMap().setMyLocationEnabled(true);
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(App.getInstance().mLocation.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(App.getInstance().mLocation.getDirection()).latitude(App.getInstance().mLocation.getLatitude())
//                .longitude(App.getInstance().mLocation.getLongitude()).build();
//        mMapView.getMap().setMyLocationData(locData);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(listener);
        setUserMapCenter(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude());
    }

    private void initMap() {
        BaiduMapOptions options = new BaiduMapOptions();
        options.scaleControlEnabled(false);
        //实例化UiSettings类对象
        UiSettings mUiSettings = mMapView.getMap().getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(false);
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(coderListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mCoder.destroy();
        super.onDestroy();

    }

    @OnClick({R.id.receiver_info, R.id.confirm, R.id.city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.receiver_info:
                ShipUserInfoActivity.action(mContext, type, mAddress.getText().toString(), mAddressName.getText().toString());
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(mShip.getName())) {
                    switch (type) {
                        case 0:
                            toast("请填写发货人信息");
                            break;
                        case 1:
                            toast("请填写收货人信息");
                            break;
                    }
                    return;

                }

                Intent shipIntent = new Intent();
                shipIntent.putExtra("ship", mShip);
                setResult(888, shipIntent);
                finish();
                break;
            case R.id.city:
                Intent intent = new Intent();
                intent.setClass(mContext, SelectCityActivity.class);
                startActivityForResult(intent, Const.CODE_REQ);
                break;
        }
    }


    /**
     * 设置中心点
     */
    private void setUserMapCenter(double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }


    BaiduMap.OnMapStatusChangeListener listener = new BaiduMap.OnMapStatusChangeListener() {
        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         *
         * @param status 地图状态改变开始时的地图状态
         */
        @Override
        public void onMapStatusChangeStart(MapStatus status) {

        }

        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         *
         * @param status 地图状态改变开始时的地图状态
         *
         * @param reason 地图状态改变的原因
         */

        //用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
        //int REASON_GESTURE = 1;
        //SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
        //int REASON_API_ANIMATION = 2;
        //开发者调用,导致的地图状态改变
        //int REASON_DEVELOPER_ANIMATION = 3;
        @Override
        public void onMapStatusChangeStart(MapStatus status, int reason) {

        }

        /**
         * 地图状态变化中
         *
         * @param status 当前地图状态
         */
        @Override
        public void onMapStatusChange(MapStatus status) {

        }

        /**
         * 地图状态改变结束
         *
         * @param status 地图状态改变结束后的地图状态
         */
        @Override
        public void onMapStatusChangeFinish(MapStatus status) {
            LatLng latLng = status.target;
            Log.d(getClass().getSimpleName(), latLng.toString());
            if (latLng != null) {
                mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(latLng)
                        // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                        .radius(500));
            }
        }
    };

    OnGetGeoCoderResultListener coderListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            //详细地址
            ReverseGeoCodeResult.AddressComponent address = reverseGeoCodeResult.getAddressDetail();
            List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
            if (poiInfos != null && poiInfos.size() > 0) {
                PoiInfo poiInfo = poiInfos.get(0);
                mAddressName.setText(poiInfo.name);
                mAddress.setText(poiInfo.address);
                mShip.setAddress(poiInfo.address);
                mShip.setAddressName(poiInfo.name);
                mShip.setPosition(new Gson().toJson(new LatLngBean(poiInfo.location.latitude, poiInfo.location.longitude)));
            }
            mShip.setAddressCode(address.adcode);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Const.CODE_REQ:
                if (data != null) {
                    city = data.getStringExtra("city");
                    mCity.setText(city);
                }
                break;
            case 888:
                if (data != null) {
                    ShipBean shipBean = data.getParcelableExtra("ship");
                    mShip.setAddressDetail(shipBean.getAddressDetail());
                    mShip.setName(shipBean.getName());
                    mShip.setPhone(shipBean.getPhone());
                }
                break;
        }
    }
}

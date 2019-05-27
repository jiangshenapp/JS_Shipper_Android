package com.js.shipper.ui.ship.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.ship.presenter.SelectAddressPresenter;
import com.js.shipper.ui.ship.presenter.contract.SelectAddressContrat;

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


    private int type;

    public static void action(Context context, int type) {
        Intent intent = new Intent(context, SelectAddressActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
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
    }

    private void initView() {
        initMap();
        initLocation();
    }

    private void initLocation() {
        mMapView.getMap().setMyLocationEnabled(true);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(App.getInstance().mLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(App.getInstance().mLocation.getDirection()).latitude(App.getInstance().mLocation.getLatitude())
                .longitude(App.getInstance().mLocation.getLongitude()).build();
        mMapView.getMap().setMyLocationData(locData);
    }

    private void initMap() {
        BaiduMapOptions options = new BaiduMapOptions();
        options.scaleControlEnabled(false);
        //实例化UiSettings类对象
        UiSettings mUiSettings = mMapView.getMap().getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(false);
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
        super.onDestroy();

    }

    @OnClick({R.id.receiver_info, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.receiver_info:

                break;
            case R.id.confirm:
                break;
        }
    }

}

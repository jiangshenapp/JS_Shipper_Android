package com.js.shipper.ui.ship.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.ui.ship.presenter.SelectAddressPresenter;
import com.js.shipper.ui.ship.presenter.contract.SelectAddressContrat;
import com.js.shipper.util.UIUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/5/13.
 */
public class SelectAddressActivity extends BaseActivity<SelectAddressPresenter> implements SelectAddressContrat.View {


    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.ship_address_name)
    TextView mAddressName;
    @BindView(R.id.ship_address)
    TextView mAddress;
    @BindView(R.id.select_location)
    ImageView mLocation;


    private AMap aMap;
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
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点
    }

    private void initMap() {
        mMap.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMap.getMap();
        }
        aMap.setPointToCenter(UIUtil.getScreenWidth() / 2, UIUtil.getScreenHeight() / 2);
        aMap.getUiSettings().setGestureScaleByMapCenter(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mMap.onDestroy();
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

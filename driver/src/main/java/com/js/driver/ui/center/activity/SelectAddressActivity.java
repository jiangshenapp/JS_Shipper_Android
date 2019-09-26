package com.js.driver.ui.center.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.base.frame.view.BaseActivity;
import com.google.gson.Gson;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.global.Const;
import com.js.driver.model.bean.LocationBean;
import com.js.driver.ui.center.presenter.SelectAddressPresenter;
import com.js.driver.ui.center.presenter.contract.SelectAddressContrat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/23
 * desc   : 地址选择【地图】
 * version: 3.0.0
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
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.search_address)
    AutoCompleteTextView mSearchAddress;
    @BindView(R.id.address_search_layout)
    LinearLayout mSearchLayout;

    private BaiduMap mBaiduMap;
    private GeoCoder mCoder;
    private String city;
    private LocationBean mLocationBean = new LocationBean();
    private SuggestionResult.SuggestionInfo mSugestion;
    private PoiSearch mPoiSearch;
    private ArrayAdapter<String> mAdapter;
    private InputMethodManager inputMethodManager;
    private GeoCoder poiSearch;//百度地图行政区检索实例
    private SuggestionSearch mSuggestionSearch;

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
        initData();
    }

    private void initData() {
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(mPoiListener);
    }

    private void initIntent() {
        mLocationBean = getIntent().getParcelableExtra("location");
        if (mLocationBean == null) {
            mLocationBean = new LocationBean();
        }
    }

    private void initView() {
        initMap();
        initLocation();
        if (mLocationBean != null) {
            if (mLocationBean.getLatitude()>0 && mLocationBean.getLongitude()>0) {
                setUserMapCenter(mLocationBean.getLatitude(), mLocationBean.getLongitude());
            } else {
                setUserMapCenter(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude());
            }
            if (!TextUtils.isEmpty(mLocationBean.getAddress())) {
                mAddress.setText(mLocationBean.getAddress());
            }
        }

        mSearchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                            .city(mCity.getText().toString())
                            .keyword(s.toString()));

                }
            }
        });

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line);
    }

    private void initLocation() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(listener);
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
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(mSuggestionListener);
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
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
        super.onDestroy();
    }

    @OnClick({R.id.confirm, R.id.city, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                Intent shipIntent = new Intent();
                shipIntent.putExtra("location", mLocationBean);
                setResult(888, shipIntent);
                finish();
                break;
            case R.id.city:
                Intent intent = new Intent();
                intent.setClass(mContext, SelectCityActivity.class);
                startActivityForResult(intent, Const.CODE_REQ);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    OnGetSuggestionResultListener mSuggestionListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            List<SuggestionResult.SuggestionInfo> poiAddrInfos = suggestionResult.getAllSuggestions();
            List<String> suggest = new ArrayList<>();
            if (poiAddrInfos != null && poiAddrInfos.size() > 0) {
                for (SuggestionResult.SuggestionInfo info : poiAddrInfos) {
                    suggest.add(info.getKey());
                }
                mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, suggest);
                mSearchAddress.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mSearchAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSugestion = poiAddrInfos.get(position);
                        mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUids(mSugestion.getUid()));
                    }
                });
            }
        }
    };

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
            mSearchAddress.setText("");
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
            setUserMapCenter(geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude);
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

                mLocationBean.setAddress(poiInfo.address);
                mLocationBean.setLatitude(poiInfo.location.latitude);
                mLocationBean.setLongitude(poiInfo.location.longitude);
            }
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
                    updateMapCenter(city);
                }
                break;
        }
    }

    public void updateMapCenter(String city) {
        mCoder.geocode(new GeoCodeOption()
                .city(city)
                .address(city));
    }


    OnGetPoiSearchResultListener mPoiListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            List<PoiDetailInfo> poiDetailInfos = poiDetailSearchResult.getPoiDetailInfoList();
            if (poiDetailInfos!=null&&poiDetailInfos.size()>0) {
                PoiDetailInfo poiDetailInfo = poiDetailInfos.get(0);
                mLocationBean.setAddress(poiDetailInfo.getAddress());
                mLocationBean.setLatitude(poiDetailInfo.getLocation().latitude);
                mLocationBean.setLongitude(poiDetailInfo.getLocation().longitude);

                mAddressName.setText(poiDetailInfo.getName());
                mAddress.setText(poiDetailInfo.getAddress());
                setUserMapCenter(poiDetailInfo.getLocation().latitude, poiDetailInfo.getLocation().longitude);
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };
}

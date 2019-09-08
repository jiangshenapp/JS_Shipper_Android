package com.js.shipper.ui.park.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.CollectPark;
import com.js.shipper.ui.order.activity.SubmitOrderActivity;
import com.js.shipper.ui.park.presenter.BranchDetailPresenter;
import com.js.shipper.ui.park.presenter.contract.BranchDetailContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.glide.GlideImageLoader;
import com.youth.banner.Banner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-16.
 */
public class BranchDetailActivity extends BaseActivity<BranchDetailPresenter> implements BranchDetailContract.View {


    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.remark)
    TextView mRemark;
    @BindView(R.id.branch_name)
    TextView mBranchName;
    @BindView(R.id.branch_distance)
    TextView mBranchDistance;
    @BindView(R.id.branch_contact)
    TextView mBranchContact;
    @BindView(R.id.branch_address)
    TextView mBranchAddress;
    private long id;
    private boolean isCollection = false;
    private MenuItem moreItem;
    private ParkBean mParkBean;
    private DecimalFormat df = new DecimalFormat("#####0.0");
    private List<String> imgPaths;

    public static void action(Context context, long id) {
        Intent intent = new Intent(context, BranchDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        id = getIntent().getLongExtra("id",0);
    }

    private void initView() {
        initBanner();
    }

    private void initBanner() {
        mBanner.setImageLoader(new GlideImageLoader());
    }

    private void initData() {
        mPresenter.getBranchDetail(id);
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
        return R.layout.activity_branch_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("车源详情");
    }


    @Override
    public void onBranchDetail(ParkBean parkBean) {
        mParkBean = parkBean;
        mRemark.setText(parkBean.getRemark());
        isCollection = parkBean.isCollect();
        mBranchName.setText(parkBean.getCompanyName());
        mBranchContact.setText(parkBean.getContactName());
        mBranchAddress.setText(parkBean.getContactAddress());
        if (moreItem != null) {
            if (isCollection) {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_selected);
            } else {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_default);
            }
        }
        if (TextUtils.isEmpty(parkBean.getBusinessLicenceImage())) {
            mBanner.setVisibility(View.GONE);
        } else {
            imgPaths = new ArrayList<>();
            imgPaths.add(com.base.http.global.Const.IMG_URL+parkBean.getBusinessLicenceImage());
            mBanner.setVisibility(View.VISIBLE);
            mBanner.setImages(imgPaths);
            mBanner.start();
        }
        if (App.getInstance().mLocation==null || TextUtils.isEmpty(parkBean.getContactLocation())){
            return;
        }
        Gson gson = new Gson();
        LatLng latLng = new LatLng(App.getInstance().mLocation.getLatitude(),App.getInstance().mLocation.getLongitude());
        try {
            double distance = DistanceUtil.getDistance(gson.fromJson(parkBean.getContactLocation(), LatLng.class), latLng);
            mBranchDistance.setText("距离您" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米"));
        }catch (Exception e) {

        }
    }

    @Override
    public void onAddCollect(boolean isOk) {
        if (isOk) {
            isCollection = true;
            if (moreItem != null) {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_selected);
            }
        }
    }

    @Override
    public void onRemoveCollect(boolean isOk) {
        if (isOk) {
            isCollection = false;
            if (moreItem != null) {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_default);
            }
        }
    }


    @OnClick({R.id.phone, R.id.im,R.id.place_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone:
                AppUtils.callPhone(mContext,mParkBean.getContractPhone());
                break;
            case R.id.im:
                toast("该功能暂未开放");
                break;
            case R.id.place_order:
                SubmitOrderActivity.action(mContext, mParkBean.getSubscriberId(),null);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        moreItem = menu.add(Menu.NONE, R.id.collection, Menu.FIRST, null);
        moreItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        if (isCollection) {
            moreItem.setIcon(R.mipmap.ic_navigationbar_collection_selected);
        } else {
            moreItem.setIcon(R.mipmap.ic_navigationbar_collection_default);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.collection:
                if (isCollection) {
                    mPresenter.removeCollect(new CollectPark(mParkBean.getParkId()));
                } else {
                    mPresenter.addCollect(new CollectPark(mParkBean.getParkId()));
                }
                break;
        }
        return true;
    }

}

package com.js.shipper.ui.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.base.http.global.Const;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.CollectLine;
import com.js.shipper.ui.order.activity.SubmitOrderActivity;
import com.js.shipper.ui.park.presenter.CarSourceDetailPresenter;
import com.js.shipper.ui.park.presenter.contract.CarSourceDetailContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.glide.GlideImageLoader;
import com.js.shipper.widget.view.RatingBar;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-16.
 */
public class CarSourceDetailActivity extends BaseActivity<CarSourceDetailPresenter> implements CarSourceDetailContract.View {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.ship_start_address)
    TextView mStartAddress;
    @BindView(R.id.ship_end_address)
    TextView mEndAddress;
    @BindView(R.id.car_driver_name)
    TextView mDriverName;
    @BindView(R.id.car_number)
    TextView mNumber;
    @BindView(R.id.car_type)
    TextView mType;
    @BindView(R.id.car_length)
    TextView mLength;
    @BindView(R.id.remark)
    TextView mRemark;
    @BindView(R.id.place_order)
    TextView mPlaceOrder;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    private long id;
    private boolean isCollection = false;
    private MenuItem moreItem;
    private LineBean mLineBean;
    private List<String> imgPaths;

    public static void action(Context context, long id) {
        Intent intent = new Intent(context, CarSourceDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initBanner();
        initData();
    }

    private void initBanner() {
        mBanner.setImageLoader(new GlideImageLoader());
    }

    private void initIntent() {
        id = getIntent().getLongExtra("id", 0);
    }

    private void initData() {
        mPresenter.getLineDetail(id);
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
        return R.layout.activity_car_source_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("车源详情");
    }

    @Override
    public void onLineDetail(LineBean lineBean) {
        mLineBean = lineBean;
        if (TextUtils.isEmpty(lineBean.getImage2())) {
            mBanner.setVisibility(View.GONE);
        } else {
            imgPaths = new ArrayList<>();
            imgPaths.add(Const.IMG_URL + lineBean.getImage2());
            mBanner.setVisibility(View.VISIBLE);
            mBanner.setImages(imgPaths);
            mBanner.start();
        }
        mStartAddress.setText(lineBean.getStartAddressCodeName());
        mEndAddress.setText(lineBean.getArriveAddressCodeName());
        mDriverName.setText(lineBean.getDriverName());
        mRatingBar.setStar(lineBean.getScore());
        mRatingBar.setClickable(false);
        mNumber.setText(lineBean.getCphm());
        mType.setText(lineBean.getCarModelName());
        mLength.setText(lineBean.getCarLengthName());
        mRemark.setText(lineBean.getRemark());
        isCollection = lineBean.isCollect();
        if (moreItem != null) {
            if (isCollection) {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_selected);
            } else {
                moreItem.setIcon(R.mipmap.ic_navigationbar_collection_default);
            }
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

    @OnClick({R.id.phone, R.id.im, R.id.place_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone:
                AppUtils.callPhone(mContext, mLineBean.getDriverPhone());
                break;
            case R.id.im:
                toast("该功能暂未开放");
                break;
            case R.id.place_order:
                SubmitOrderActivity.action(mContext, mLineBean.getSubscriberId(), null);
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
                    mPresenter.removeCollect(new CollectLine(id));
                } else {
                    mPresenter.addCollect(new CollectLine(id));
                }
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

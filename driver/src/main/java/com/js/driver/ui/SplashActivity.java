package com.js.driver.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.frame.view.SimpleWebActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.presenter.SplashPresenter;
import com.js.driver.presenter.contract.SplashContract;
import com.js.driver.service.LocationService;
import com.js.driver.ui.main.activity.MainActivity;
import com.base.frame.view.BaseActivity;
import com.js.driver.ui.main.presenter.ServicePresenter;
import com.js.driver.ui.main.presenter.contract.ServiceContract;
import com.js.driver.util.glide.CommonGlideImageLoader;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huyg on 2019-05-23.
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View, ServiceContract.View {

    @BindView(R.id.iv_ad)
    ImageView mIvAd;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    @Inject
    ServicePresenter mServicePresenter;

    private BannerBean mBannerBean;

    @Override
    protected void init() {
        mServicePresenter.attachView(this);
        initPermission();
        initLocation();
    }

    private void initLocation() {
        Intent intent = new Intent(mContext, LocationService.class);
        startService(intent);
    }

    private void initPermission() {
        XXPermissions.with(this)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.ACCESS_COARSE_LOCATION,
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.CAMERA)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            mServicePresenter.getBannerList(3);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        finish();
                    }
                });
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
        return R.layout.activity_splash;
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_ad, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ad: //广告点击
                SimpleWebActivity.action(this, mBannerBean.getUrl(), mBannerBean.getTitle());
                break;
            case R.id.tv_skip: //跳过
                if (mServicePresenter != null) {
                    mServicePresenter.detachView();
                }
                MainActivity.action(mContext);
                finish();
                break;
        }
    }

    @Override
    public void onBannerListFail() {
        MainActivity.action(mContext);
        finish();
    }

    @Override
    public void onBannerList(List<BannerBean> mBannerBeans) {
        if (mBannerBeans.size() > 0) {
            mBannerBean = mBannerBeans.get(0);
            mIvAd.setVisibility(View.VISIBLE);
            mTvSkip.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mBannerBean.getImage(), mIvAd);
            Observable.timer(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            MainActivity.action(mContext);
                            finish();
                        }
                    });
        } else {
            MainActivity.action(mContext);
            finish();
        }
    }

    @Override
    public void onServiceList(List<ServiceBean> mServiceBeans) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServicePresenter != null) {
            mServicePresenter.detachView();
        }
    }
}

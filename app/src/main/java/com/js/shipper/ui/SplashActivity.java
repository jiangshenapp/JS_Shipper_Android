package com.js.shipper.ui;

import android.content.Intent;
import android.view.View;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.presenter.SplashPresenter;
import com.js.shipper.presenter.contract.SplashContract;
import com.js.shipper.service.LocationService;
import com.js.shipper.ui.main.activity.MainActivity;

import java.util.List;

/**
 * Created by huyg on 2019-05-23.
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    protected void init() {
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
                        Permission.WRITE_EXTERNAL_STORAGE)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            MainActivity.action(mContext);
                        }
                        finish();
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
}

package com.js.shipper.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.manager.SpManager;
import com.js.shipper.ui.user.presenter.UserCenterPresenter;
import com.js.shipper.ui.user.presenter.contract.UserCenterContract;
import com.js.frame.view.BaseActivity;

import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class UserCenterActivity extends BaseActivity<UserCenterPresenter> implements UserCenterContract.View {


    public static void action(Context context) {
        context.startActivity(new Intent(context, UserCenterActivity.class));
    }

    @Override
    protected void init() {

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
        return R.layout.activity_user_center;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("用户中心");
    }


    @OnClick({R.id.center_avatar_layout, R.id.center_name_layout, R.id.center_verified_layout, R.id.center_campus_layout, R.id.center_feedback_layout, R.id.center_version_layout, R.id.center_about_layout, R.id.center_cache_layout, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_avatar_layout://头像
                break;
            case R.id.center_name_layout://昵称
                break;
            case R.id.center_verified_layout://实名认证
                UserVerifiedActivity.action(mContext);
                break;
            case R.id.center_campus_layout://园区地址
                break;
            case R.id.center_feedback_layout://意见反馈
                break;
            case R.id.center_version_layout://版本号
                break;
            case R.id.center_about_layout://关于
                break;
            case R.id.center_cache_layout://清除缓存
                break;
            case R.id.logout://登出
                SpManager.getInstance(this).removeSP("token");
                LoginActivity.action(this,true);
                break;
        }
    }
}

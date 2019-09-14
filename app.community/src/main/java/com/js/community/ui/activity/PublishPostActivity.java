package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.base.frame.view.BaseActivity;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.ui.presenter.PublishPostPresenter;
import com.js.community.ui.presenter.contract.PublishPostContract;

/**
 * Created by huyg on 2019-09-11.
 * 发布帖子
 */
public class PublishPostActivity extends BaseActivity<PublishPostPresenter> implements PublishPostContract.View {


    private CircleBean mCircle;

    public static void action(Context context, CircleBean circleBean) {
        Intent intent = new Intent(context, PublishPostActivity.class);
        intent.putExtra("circle", circleBean);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
    }

    private void initIntent() {
        mCircle = getIntent().getParcelableExtra("circle");
    }


    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(CommunityApp.getApp().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publist_post;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("发帖");
    }
}

package com.js.community.ui.activity;

import com.base.frame.view.BaseActivity;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.ui.presenter.PostDetailPresenter;
import com.js.community.ui.presenter.contract.PostDetailContract;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostDetailActivity extends BaseActivity<PostDetailPresenter> implements PostDetailContract.View {


    @Override
    protected void init() {

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
        return R.layout.activity_post_detail;
    }

    @Override
    public void setActionBar() {

    }
}

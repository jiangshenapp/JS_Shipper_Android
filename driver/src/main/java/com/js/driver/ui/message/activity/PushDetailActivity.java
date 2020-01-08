package com.js.driver.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.model.bean.PushBean;
import com.js.driver.ui.message.presenter.PushDetailPresenter;
import com.js.driver.ui.message.presenter.contract.PushDetailContract;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/08
 * desc   : 推送通知详情
 * version: 3.0.0
 */
public class PushDetailActivity extends BaseActivity<PushDetailPresenter> implements PushDetailContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private PushBean mPushBean;

    public static void action(Context context, PushBean pushBean) {
        Intent intent = new Intent(context, PushDetailActivity.class);
        intent.putExtra("pushBean", pushBean);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initData();
    }

    private void initIntent() {
        mPushBean = getIntent().getParcelableExtra("pushBean");
    }

    private void initData() {
        mTvTitle.setText(mPushBean.getTemplateName());
        mTvTime.setText(mPushBean.getPushTime());
        mTvContent.setText(mPushBean.getPushContent());
        mIvImage.setVisibility(View.GONE);
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
        return R.layout.activity_message_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("通知详情");
    }
}

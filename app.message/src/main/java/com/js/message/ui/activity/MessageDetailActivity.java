package com.js.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.base.http.global.Const;
import com.js.message.MessageApp;
import com.js.message.R;
import com.js.message.R2;
import com.js.message.di.componet.DaggerActivityComponent;
import com.js.message.di.module.ActivityModule;
import com.js.message.model.bean.MessageBean;
import com.js.message.ui.presenter.MessageDetailPresenter;
import com.js.message.ui.presenter.contract.MessageDetailContract;
import com.js.message.util.glide.CommonGlideImageLoader;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   : 系统消息详情
 * version: 3.0.0
 */
public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements MessageDetailContract.View {

    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_time)
    TextView mTvTime;
    @BindView(R2.id.iv_image)
    ImageView mIvImage;
    @BindView(R2.id.tv_content)
    TextView mTvContent;
    private long messageId;

    public static void action(Context context, long messageId) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("messageId", messageId);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initData();
    }

    private void initIntent() {
        messageId = getIntent().getLongExtra("messageId", 0);
    }

    private void initData() {
        mPresenter.getMessageDetail(messageId);
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(MessageApp.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("消息详情");
    }

    @Override
    public void onMessageDetail(MessageBean messageBean) {
        mTvTitle.setText(messageBean.getTitle());
        mTvTime.setText(messageBean.getPublishTime());
        mTvContent.setText(messageBean.getContent());
        if (!TextUtils.isEmpty(messageBean.getImage())) {
            mIvImage.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + messageBean.getImage(), mIvImage);
        } else {
            mIvImage.setVisibility(View.GONE);
        }
    }
}

package com.js.community.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.ui.presenter.CommentPresenter;
import com.js.community.ui.presenter.contract.CommentContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-09-19.
 */
public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract.View {

    @BindView(R2.id.comment_cancel)
    TextView commentCancel;
    @BindView(R2.id.comment_submit)
    TextView commentSubmit;
    @BindView(R2.id.comment_content)
    EditText mContent;
    private long postId;

    public static void action(Activity context, long postId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("postId", postId);
        context.startActivityForResult(intent, 999);
    }

    @Override
    protected void init() {
        initIntent();
    }

    private void initIntent() {
        postId = getIntent().getLongExtra("postId", 0);
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
        return R.layout.activity_comment;
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void onComment() {
        finish();
    }

    @OnClick({R2.id.comment_cancel, R2.id.comment_submit})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.comment_cancel) {
            finish();
        } else if (view.getId() == R.id.comment_submit) {
            String comment = mContent.getText().toString().trim();
            if (TextUtils.isEmpty(comment)) {
                toast("请输入评论内容");
                return;
            }
            mPresenter.comment(postId, comment);
        }

    }
}

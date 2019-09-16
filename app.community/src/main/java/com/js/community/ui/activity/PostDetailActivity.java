package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.Comment;
import com.js.community.model.bean.PostBean;
import com.js.community.ui.adapter.CommentAdapter;
import com.js.community.ui.presenter.PostDetailPresenter;
import com.js.community.ui.presenter.contract.PostDetailContract;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostDetailActivity extends BaseActivity<PostDetailPresenter> implements PostDetailContract.View {

    @BindView(R2.id.circle_name)
    TextView mCircleName;
    @BindView(R2.id.post_name)
    TextView mName;
    @BindView(R2.id.post_time)
    TextView mTime;
    @BindView(R2.id.post_content)
    TextView mContent;
    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.post_like)
    TextView mLike;
    @BindView(R2.id.post_comment)
    TextView mComment;


    private PostBean postBean;
    private CommentAdapter mAdapter;
    private List<Comment> mComments;

    public static void action(Context context, PostBean postBean) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("post", postBean);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initData() {
        mPresenter.getCommentList(postBean.getId());
    }

    private void initView() {
        initDetail();
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new CommentAdapter(R.layout.item_comment, mComments);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void initDetail() {
        mName.setText(postBean.getTitle());
        mCircleName.setText(postBean.getSubject());
        mTime.setText(postBean.getCreateTime());
        mContent.setText(postBean.getContent());
        mLike.setText(String.valueOf(postBean.getLikeCount()));
        mComment.setText(String.valueOf(postBean.getCommentCount()));
    }

    private void initIntent() {
        postBean = getIntent().getParcelableExtra("post");
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
        mTitle.setText("详情");
    }

    @OnClick({R2.id.to_comment, R2.id.post_like, R2.id.post_comment})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.to_comment) {

        } else if (view.getId() == R.id.post_like) {
            mPresenter.likePost(postBean.getId());
        } else if (view.getId() == R.id.post_comment) {

        }
    }

    @Override
    public void onCommentList(List<Comment> comments) {
        mAdapter.setNewData(comments);
    }

    @Override
    public void onLikePost() {

    }
}

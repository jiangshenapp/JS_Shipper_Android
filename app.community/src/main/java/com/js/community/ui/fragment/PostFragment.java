package com.js.community.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerFragmentComponent;
import com.js.community.di.module.FragmentModule;
import com.js.community.model.bean.PostBean;
import com.js.community.ui.activity.PostDetailActivity;
import com.js.community.ui.adapter.PostAdapter;
import com.js.community.ui.presenter.PostPresenter;
import com.js.community.ui.presenter.contract.PostContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-09-16.
 */
public class PostFragment extends BaseFragment<PostPresenter> implements PostContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
    SmartRefreshLayout mRefresh;


    private PostAdapter mAdapter;
    private List<PostBean> mPosts;
    private boolean commentFlag;
    private boolean likeFlag;
    private boolean myFlag;

    public static PostFragment newInstance(boolean commentFlag, boolean likeFlag, boolean myFlag) {
        PostFragment postFragment = new PostFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("commentFlag", commentFlag);
        bundle.putBoolean("likeFlag", likeFlag);
        bundle.putBoolean("myFlag", myFlag);
        postFragment.setArguments(bundle);
        return postFragment;
    }

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(CommunityApp.getApp().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post;
    }

    @Override
    protected void init() {
        initArgument();
        initView();
    }

    private void initArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            commentFlag = bundle.getBoolean("commentFlag");
            likeFlag = bundle.getBoolean("likeFlag");
            myFlag = bundle.getBoolean("myFlag");
        }

    }

    private void initView() {
        initRefresh();
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new PostAdapter(R.layout.item_index_circle, mPosts);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(this);
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getPosts(-1, "", commentFlag, likeFlag, myFlag);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PostBean postBean = (PostBean) adapter.getItem(position);
        PostDetailActivity.action(mContext,postBean);
    }

    @Override
    public void onPosts(List<PostBean> postBeans) {
        mAdapter.setNewData(postBeans);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }
}

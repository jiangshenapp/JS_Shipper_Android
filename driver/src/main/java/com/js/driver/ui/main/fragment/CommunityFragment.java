package com.js.driver.ui.main.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.model.bean.CircleBean;
import com.js.community.ui.activity.CircleIndexActivity;
import com.js.community.ui.activity.FindCircleActivity;
import com.js.community.ui.activity.PostListActivity;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.ui.main.adapter.CircleAdapter;
import com.js.driver.ui.main.presenter.CommunityPresenter;
import com.js.driver.ui.main.presenter.contract.CommunityContract;
import com.base.frame.view.BaseFragment;
import com.js.driver.util.UserManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/1.
 */
public class CommunityFragment extends BaseFragment<CommunityPresenter> implements CommunityContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private CircleAdapter mAdapter;
    private List<CircleBean> mData;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }


    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCircles();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCircles();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CircleAdapter(R.layout.item_community_circle, mData);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.favorite, R.id.comment, R.id.find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.favorite://我的喜欢
                if (!UserManager.getUserManager().isVerified()) {
                    toast("未认证");
                    return;
                }
                PostListActivity.action(mContext,1);
                break;
            case R.id.comment://评论
                if (!UserManager.getUserManager().isVerified()) {
                    toast("未认证");
                    return;
                }
                PostListActivity.action(mContext,2);
                break;
            case R.id.find://找圈子
                FindCircleActivity.action(mContext,2);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CircleBean circleBean = (CircleBean)adapter.getItem(position);
        CircleIndexActivity.action(mContext,circleBean);
    }

    @Override
    public void onCircles(List<CircleBean> circleBeans) {
        mAdapter.setNewData(circleBeans);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }
}

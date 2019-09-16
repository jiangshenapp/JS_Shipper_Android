package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.Member;
import com.js.community.ui.adapter.MemberAdapter;
import com.js.community.ui.presenter.MemberManagePresenter;
import com.js.community.ui.presenter.contract.MemberManageContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-09-11.
 */
public class MemberManageActivity extends BaseActivity<MemberManagePresenter> implements MemberManageContract.View, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
    SmartRefreshLayout mRefresh;


    private CircleBean mCircle;
    private MemberAdapter mAdapter;
    private List<Member> members;


    public static void action(Context context, CircleBean circleBean) {
        Intent intent = new Intent(context, MemberManageActivity.class);
        intent.putExtra("circle", circleBean);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();

    }

    private void initView() {
        mTitle.setText(mCircle.getName());
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setEnableLoadMore(false);
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMembers(mCircle.getId());
            }
        });
    }

    private void initRecycler() {
        mAdapter = new MemberAdapter(R.layout.item_member, members);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemChildClickListener(this);
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
        return R.layout.activity_member_list;
    }

    @Override
    public void setActionBar() {

    }

    @Override
    public void onMembers(List<Member> members) {
        mAdapter.setNewData(members);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    @Override
    public void onAuditApply(boolean b) {
        if (b){
            mPresenter.getMembers(mCircle.getId());
        }
    }

    @Override
    public void onDeleteSubscriber(boolean b) {
        if (b){
            mPresenter.getMembers(mCircle.getId());
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Member member = (Member) adapter.getItem(position);
        if (view.getId() == R.id.item_agree) {
            mPresenter.auditApplyCircle(member.getId(), "1");
        } else if (view.getId() == R.id.item_refuse) {
            mPresenter.auditApplyCircle(member.getId(), "2");
        } else if (view.getId() == R.id.item_delete) {
            mPresenter.deleteSubscriber(member.getId());
        }
    }
}

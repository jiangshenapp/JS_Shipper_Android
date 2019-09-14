package com.js.community.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.js.community.ui.adapter.AllCircleAdapter;
import com.js.community.ui.adapter.PostAdapter;
import com.js.community.ui.presenter.FindCirclePresenter;
import com.js.community.ui.presenter.contract.FindCircleContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-09-09.
 */
public class FindCircleActivity extends BaseActivity<FindCirclePresenter> implements FindCircleContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
    SmartRefreshLayout mRefresh;


    private AllCircleAdapter mAdapter;
    private PostAdapter mPostAdapter;
    private List<CircleBean> mCircles;
    private int showSide;

    public static void action(Context context,int showSide){
        Intent intent = new Intent(context,FindCircleActivity.class);
        intent.putExtra("showSide",showSide);
        context.startActivity(intent);
    }
    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        showSide = getIntent().getIntExtra("showSide",1);
    }


    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getAllCircle("330200", 1);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new AllCircleAdapter(R.layout.item_find_circle, mCircles);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(this);
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
        return R.layout.activity_find_circle;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("找圈子");
    }


    @Override
    public void onAllCircle(List<CircleBean> circleBeans) {
        mAdapter.setNewData(circleBeans);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    @Override
    public void onApplayCircle(Boolean b) {
        if (b) {
            toast("申请成功");
            finish();
        }else {
            toast("申请失败");
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CircleBean circleBean = (CircleBean) adapter.getItem(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("加入圈子");
        builder.setMessage("是否加入？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mPresenter.applyCircle(circleBean.getId());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}

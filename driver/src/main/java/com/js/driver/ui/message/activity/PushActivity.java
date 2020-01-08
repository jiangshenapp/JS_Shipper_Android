package com.js.driver.ui.message.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.model.bean.PushBean;
import com.js.driver.ui.message.adapter.PushAdapter;
import com.js.driver.ui.message.presenter.PushPresenter;
import com.js.driver.ui.message.presenter.contract.PushContract;
import com.js.driver.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/07
 * desc   : 推送通知列表
 * version: 3.0.0
 */
public class PushActivity extends BaseActivity<PushPresenter> implements PushContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private PushAdapter mAdapter;
    private List<PushBean> mList;

    public static void action(Context context) {
        Intent intent = new Intent(context, PushActivity.class);
        context.startActivity(intent);
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
        return R.layout.activity_message;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("推送通知");
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initRefresh();
        initRecycler();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getPushMessage(1);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new PushAdapter(R.layout.item_message, mList);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<PushBean> pushBeans = adapter.getData();
        PushBean pushBean = pushBeans.get(position);
        if (pushBean != null) {
            MessageDetailActivity.action(mContext, pushBean.getId());
        }
    }

    @Override
    public void onPushMessage(List<PushBean> mPushBeans) {
        mList = mPushBeans;
        mAdapter.setNewData(mList);
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
    }
}

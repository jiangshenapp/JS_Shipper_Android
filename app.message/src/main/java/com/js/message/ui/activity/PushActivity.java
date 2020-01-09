package com.js.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.message.MessageApp;
import com.js.message.R;
import com.js.message.R2;
import com.js.message.di.componet.DaggerActivityComponent;
import com.js.message.di.module.ActivityModule;
import com.js.message.model.bean.PushBean;
import com.js.message.model.event.MessageEvent;
import com.js.message.ui.adapter.PushAdapter;
import com.js.message.ui.presenter.PushPresenter;
import com.js.message.ui.presenter.contract.PushContract;
import com.js.message.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

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

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
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
                .appComponent(MessageApp.getInstance().getAppComponent())
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
                getPushMessage();
            }
        });
    }

    private void getPushMessage() {
        if ("shipper".equals(MessageApp.getInstance().appType)) {
            mPresenter.getPushMessage(2);
        } else {
            mPresenter.getPushMessage(1);
        }
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
            if ("shipper".equals(MessageApp.getInstance().appType)) {
                mPresenter.readPushLog(pushBean.getId(), 2);
            } else {
                mPresenter.readPushLog(pushBean.getId(), 1);
            }
            PushDetailActivity.action(mContext, pushBean);
        }
    }

    @Override
    public void onPushMessage(List<PushBean> mPushBeans) {
        mList = mPushBeans;
        mAdapter.setNewData(mList);
    }

    @Override
    public void onReadPushLog(boolean isOk) {
        EventBus.getDefault().post(new MessageEvent
                (MessageEvent.PUSH_COUNT_CHANGE));
        getPushMessage();
    }

    @Override
    public void onReadAllPushLog(boolean isOk) {
        EventBus.getDefault().post(new MessageEvent
                (MessageEvent.PUSH_COUNT_CHANGE));
        getPushMessage();
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.message_all_read) {
            if ("shipper".equals(MessageApp.getInstance().appType)) {
                mPresenter.readAllPushLog(2);
            } else {
                mPresenter.readAllPushLog(1);
            }
        }
        return true;
    }
}

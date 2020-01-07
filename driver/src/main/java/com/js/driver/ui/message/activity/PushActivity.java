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
import com.js.driver.global.Const;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.response.ListResponse;
import com.js.driver.ui.message.adapter.MessageAdapter;
import com.js.driver.ui.message.presenter.MessagePresenter;
import com.js.driver.ui.message.presenter.contract.MessageContract;
import com.js.driver.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/07
 * desc   : 推送通知列表
 * version: 3.0.0
 */
public class PushActivity extends BaseActivity<MessagePresenter> implements MessageContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private MessageAdapter mAdapter;
    private List<MessageBean> mList;
    private int type;

    public static void action(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
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
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                getMessage(num);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMessage(Const.PAGE_NUM);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new MessageAdapter(R.layout.item_message, mList);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<MessageBean> messageBeans = adapter.getData();
        MessageBean messageBean = messageBeans.get(position);
        if (messageBean != null) {
            MessageDetailActivity.action(mContext, messageBean.getId());
        }
    }

    private void getMessage(int num) {
        if (num == Const.PAGE_NUM) {
            type = Const.REFRESH;
        } else {
            type = Const.MORE;
        }
        mPresenter.getMessage(3, num, Const.PAGE_SIZE);
    }

    @Override
    public void onMessage(ListResponse<MessageBean> mMessageBeans) {
        switch (type) {
            case Const.REFRESH:
                mAdapter.setNewData(mMessageBeans.getRecords());
                break;
            case Const.MORE:
                mAdapter.addData(mMessageBeans.getRecords());
                break;
        }
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }
}

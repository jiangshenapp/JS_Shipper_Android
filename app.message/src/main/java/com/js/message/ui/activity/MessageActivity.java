package com.js.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.MeasureFormat;
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
import com.js.message.global.Const;
import com.js.message.model.bean.MessageBean;
import com.js.message.model.event.MessageEvent;
import com.js.message.model.response.ListResponse;
import com.js.message.ui.adapter.MessageAdapter;
import com.js.message.ui.presenter.MessagePresenter;
import com.js.message.ui.presenter.contract.MessageContract;
import com.js.message.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   : 系统消息列表
 * version: 3.0.0
 */
public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
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
        mTitle.setText("系统消息");
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
            if ("shipper".equals(MessageApp.getInstance().appType)) {
                mPresenter.readMessage(messageBean.getId(), 2);
            } else {
                mPresenter.readMessage(messageBean.getId(), 1);
            }
            MessageDetailActivity.action(mContext, messageBean.getId());
        }
    }

    private void getMessage(int num) {
        if (num == Const.PAGE_NUM) {
            type = Const.REFRESH;
        } else {
            type = Const.MORE;
        }
        if ("shipper".equals(MessageApp.getInstance().appType)) {
            mPresenter.getMessage(3, num, Const.PAGE_SIZE);
        } else {
            mPresenter.getMessage(2, num, Const.PAGE_SIZE);
        }
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
    public void onReadMessage(boolean isOk) {
        EventBus.getDefault().post(new MessageEvent
                (MessageEvent.MESSAGE_COUNT_CHANGE));
        getMessage(Const.PAGE_NUM);
    }

    @Override
    public void onReadAllMessage(boolean isOk) {
        EventBus.getDefault().post(new MessageEvent
                (MessageEvent.MESSAGE_COUNT_CHANGE));
        getMessage(Const.PAGE_NUM);
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
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
                mPresenter.readAllMessage(2);
            } else {
                mPresenter.readAllMessage(1);
            }
        }
        return true;
    }
}

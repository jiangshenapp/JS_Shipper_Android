package com.js.shipper.ui.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.order.activity.OrderDetailActivity;
import com.js.shipper.ui.order.adapter.OrderAdapter;
import com.js.shipper.ui.order.presenter.OrderPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.js.frame.view.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private OrderAdapter mOrderAdapter;
    private List<OrderBean> mData;
    private int type;

    public static OrderFragment newInstance(int type) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        orderFragment.setArguments(bundle);
        return orderFragment;
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
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                type = Const.MORE;
                int num = (int) Math.ceil((mOrderAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                mPresenter.getOrderList(type, num, Const.PAGE_SIZE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                type = Const.REFRESH;
                mPresenter.getOrderList(type, Const.PAGE_NUM, Const.PAGE_SIZE);
            }
        });
    }

    private void initRecycler() {
        mOrderAdapter = new OrderAdapter(R.layout.item_waybill, mData);
        mRecycler.setAdapter(mOrderAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<OrderBean> orderBeans = adapter.getData();
        OrderBean orderBean = orderBeans.get(position);
        OrderDetailActivity.action(mContext, orderBean.getId());
    }

    @Override
    public void onOrderList(ListResponse<OrderBean> orders) {
        switch (type) {
            case Const.REFRESH:
                mOrderAdapter.setNewData(orders.getRecords());
                break;
            case Const.MORE:
                mOrderAdapter.addData(orders.getRecords());
                break;
        }
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }
}

package com.js.driver.ui.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.ui.main.presenter.contract.MainContract;
import com.js.driver.ui.order.activity.OrderDetailActivity;
import com.js.driver.ui.order.adapter.OrderAdapter;
import com.js.driver.ui.order.presenter.OrderPresenter;
import com.js.driver.ui.order.presenter.contract.OrderContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xlgcx.frame.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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
    private List<Object> mData;

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
        mData = new ArrayList<>();
        mData.add(new Object());
        mOrderAdapter.setNewData(mData);
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {

    }

    private void initRecycler() {
        mOrderAdapter = new OrderAdapter(R.layout.item_waybill, mData);
        mRecycler.setAdapter(mOrderAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderDetailActivity.action(mContext,"");
    }
}

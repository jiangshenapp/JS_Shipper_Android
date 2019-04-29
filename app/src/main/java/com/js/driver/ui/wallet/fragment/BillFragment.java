package com.js.driver.ui.wallet.fragment;

import android.os.Bundle;

import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.model.bean.BillBean;
import com.js.driver.ui.wallet.adapter.BillAdapter;
import com.js.driver.ui.wallet.presenter.BillPresenter;
import com.js.driver.ui.wallet.presenter.contract.BillContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xlgcx.frame.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/24.
 * 账单详情
 */
public class BillFragment extends BaseFragment<BillPresenter> implements BillContract.View {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    private int type;


    private BillAdapter mAdapter;
    private List<BillBean> mBills;

    public static BillFragment newInstance(int type) {
        BillFragment billFragment = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        billFragment.setArguments(bundle);
        return billFragment;
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
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void init() {
        initArgument();
        initView();
        initData();
    }

    private void initData() {
        mBills = new ArrayList<>();
        mBills.add(new BillBean());
        mAdapter.setNewData(mBills);
    }

    private void initView() {
        iniRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    private void iniRecycler() {
        mAdapter = new BillAdapter(R.layout.item_bill_detail, mBills);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
    }

    private void initArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }
}

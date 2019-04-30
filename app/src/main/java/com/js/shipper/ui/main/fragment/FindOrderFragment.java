package com.js.shipper.ui.main.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.ui.main.adapter.FindOrderAdapter;
import com.js.shipper.ui.main.presenter.FindOrderPresenter;
import com.js.shipper.ui.main.presenter.contract.FindOrderContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.js.frame.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/1.
 */
public class FindOrderFragment extends BaseFragment<FindOrderPresenter> implements FindOrderContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;


    private FindOrderAdapter mAdapter;
    private List<OrderBean> orders;

    public static FindOrderFragment newInstance() {
        return new FindOrderFragment();
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
        return R.layout.fragment_find_order;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        orders = new ArrayList<>();
        orders.add(new OrderBean());
        mAdapter.setNewData(orders);
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRecycler() {
        mAdapter = new FindOrderAdapter(R.layout.item_home_order,orders);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void initRefresh() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

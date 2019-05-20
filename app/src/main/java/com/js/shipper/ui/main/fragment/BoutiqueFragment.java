package com.js.shipper.ui.main.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.adapter.BoutiqueAdapter;
import com.js.shipper.ui.main.adapter.CarSourceAdapter;
import com.js.shipper.ui.main.presenter.BoutiquePresenter;
import com.js.shipper.ui.main.presenter.contract.BoutiqueContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 * 精品路线
 */
public class BoutiqueFragment extends BaseFragment<BoutiquePresenter> implements BoutiqueContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;


    private BoutiqueAdapter mAdapter;
    private List<Object> mList;

    public static BoutiqueFragment newInstance() {
        return new BoutiqueFragment();
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
        return R.layout.fragment_boutique;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new Object());
        mAdapter.setNewData(mList);
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new BoutiqueAdapter(R.layout.item_bill_detail, mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

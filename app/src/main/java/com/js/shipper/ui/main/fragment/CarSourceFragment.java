package com.js.shipper.ui.main.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.adapter.CarSourceAdapter;
import com.js.shipper.ui.main.presenter.CarSourcePresenter;
import com.js.shipper.ui.main.presenter.contract.CarSourceContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 * 车源
 */
public class CarSourceFragment extends BaseFragment<CarSourcePresenter> implements CarSourceContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private CarSourceAdapter mAdapter;
    private List<Object> mList;

    public static CarSourceFragment newInstance(){
        return new CarSourceFragment();
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
        return R.layout.fragment_car_source;
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
        mAdapter = new CarSourceAdapter(R.layout.item_car_source, mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

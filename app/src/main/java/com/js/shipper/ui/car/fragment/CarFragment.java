package com.js.shipper.ui.car.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.ui.car.adapter.CarAdapter;
import com.js.shipper.ui.car.presenter.CarPresenter;
import com.js.shipper.ui.car.presenter.contract.CarContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public class CarFragment extends BaseFragment<CarPresenter> implements CarContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    private int type;

    private CarAdapter mAdapter;
    private List<CarBean> mCars;

    public static CarFragment newInstance(int type) {
        CarFragment carFragment = new CarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        carFragment.setArguments(bundle);
        return carFragment;
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
        mPresenter.getCarList(type);
    }

    private void initView() {
        iniRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCarList(type);
            }
        });
    }

    private void iniRecycler() {
        mAdapter = new CarAdapter(R.layout.item_car, mCars);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty, mRecycler);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    private void initArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    @Override
    public void onCarList(List<CarBean> carBeans) {
        mCars = carBeans;
        mAdapter.setNewData(mCars);
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }

    @Override
    public void onRemoveCar(boolean isOk) {
        toast("删除成功");
        mPresenter.getCarList(type);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<CarBean> carBeans = adapter.getData();
        CarBean carBean = carBeans.get(position);
        switch (view.getId()) {
            case R.id.item_phone:
                AppUtils.callPhone(mContext,carBean.getMobile());
                break;
            case R.id.item_submit:
                toast("下单");
                break;
            case R.id.item_car_delete:
                mPresenter.removeCar(carBean.getCarId());
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

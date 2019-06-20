package com.js.shipper.ui.park.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.main.adapter.DeliveryAdapter;
import com.js.shipper.ui.park.activity.BranchDetailActivity;
import com.js.shipper.ui.park.presenter.CollectDeliveryPresenter;
import com.js.shipper.ui.park.presenter.contract.CollectDeliveryContract;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 * 城市配送
 */
public class CollectBranchFragment extends BaseFragment<CollectDeliveryPresenter> implements CollectDeliveryContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private DeliveryAdapter mAdapter;
    private List<ParkBean> mLists;
    private int type;

    public static CollectBranchFragment newInstance() {
        return new CollectBranchFragment();
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
        return R.layout.fragment_collect_branch;
    }

    @Override
    protected void init() {
        initView();
    }


    private void initView() {
        initAdapter();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                type = Const.MORE;
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                getParkList(num);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                type = Const.REFRESH;
                getParkList(Const.PAGE_NUM);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new DeliveryAdapter(R.layout.item_delivery, mLists);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemChildClickListener(this);
    }


    private void getParkList(int num) {
        mPresenter.getCollectBranchs(num, Const.PAGE_SIZE);
    }


    @Override
    public void onCollectBranchs(ListResponse<ParkBean> listResponse) {
        switch (type) {
            case Const.REFRESH:
                mAdapter.setNewData(listResponse.getRecords());
                break;
            case Const.MORE:
                mAdapter.addData(listResponse.getRecords());
                break;
        }
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishLoadMore();
        mRefresh.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<ParkBean> parkBeans = adapter.getData();
        BranchDetailActivity.action(mContext, parkBeans.get(position).getId());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ParkBean> parkBeans = adapter.getData();
        ParkBean parkBean = parkBeans.get(position);
        parkBean.setRemark(!parkBean.isRemark());
        mAdapter.setNewData(parkBeans);
    }
}

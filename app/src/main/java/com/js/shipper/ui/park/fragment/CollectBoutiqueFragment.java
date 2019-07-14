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
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.main.adapter.BoutiqueAdapter;
import com.js.shipper.ui.park.activity.BoutiqueDetailActivity;
import com.js.shipper.ui.park.presenter.CollectBoutiquePresenter;
import com.js.shipper.ui.park.presenter.contract.CollectBoutiqueContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 * 精品路线收藏
 */
public class CollectBoutiqueFragment extends BaseFragment<CollectBoutiquePresenter> implements CollectBoutiqueContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;


    private BoutiqueAdapter mAdapter;
    private List<LineBean> mList;
    private int type;

    public static CollectBoutiqueFragment newInstance() {
        return new CollectBoutiqueFragment();
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
        return R.layout.fragment_collect_boutique;
    }

    @Override
    protected void init() {
        initView();
    }


    private void initView() {
        initRefresh();
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new BoutiqueAdapter(R.layout.item_boutique, mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                type = Const.MORE;
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                mPresenter.getCollectClassics(num, Const.PAGE_SIZE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                type = Const.REFRESH;
                mPresenter.getCollectClassics(Const.PAGE_NUM, Const.PAGE_SIZE);
            }
        });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<LineBean> lineBeans = adapter.getData();
        LineBean lineBean = lineBeans.get(position);
        if (lineBean != null) {
            BoutiqueDetailActivity.action(mContext, lineBean.getId());
        }
    }


    @Override
    public void onCollectClassics(ListResponse<LineBean> response) {
        switch (type) {
            case Const.REFRESH:
                mAdapter.setNewData(response.getRecords());
                break;
            case Const.MORE:
                mAdapter.addData(response.getRecords());
                break;
        }
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<LineBean> lineBeans = adapter.getData();
        LineBean lineBean = lineBeans.get(position);
        switch (view.getId()) {
            case R.id.item_phone:
                AppUtils.callPhone(lineBean.getDriverPhone());
                break;
            case R.id.item_chat:
                toast("该功能暂未开放");
                break;
        }
    }
}

package com.js.shipper.ui.main.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.park.activity.BoutiqueDetailActivity;
import com.js.shipper.ui.main.adapter.BoutiqueAdapter;
import com.js.shipper.ui.main.presenter.BoutiquePresenter;
import com.js.shipper.ui.main.presenter.contract.BoutiqueContract;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
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
    private List<LineBean> mList;
    private int type;

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
    }


    private void initView() {
        initRefresh();
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new BoutiqueAdapter(R.layout.item_boutique, mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(this);
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                type = Const.MORE;
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                mPresenter.getClassicLine(num, "", "", Const.PAGE_SIZE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                type = Const.REFRESH;
                mPresenter.getClassicLine(Const.PAGE_NUM, "", "", Const.PAGE_SIZE);
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
    public void onClassicLine(ListResponse<LineBean> response) {
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
}

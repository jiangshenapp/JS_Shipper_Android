package com.js.shipper.ui.park.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.base.frame.view.BaseFragment;
import com.hyphenate.easeui.EaseConstant;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.main.adapter.CarSourceAdapter;
import com.js.message.ui.chat.EaseChatActivity;
import com.js.shipper.ui.park.activity.CarSourceDetailActivity;
import com.js.shipper.ui.park.presenter.CollectCarSourcePresenter;
import com.js.shipper.ui.park.presenter.contract.CollectCarSourceContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.UserManager;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 * 车源
 */
public class CollectCarSourceFragment extends BaseFragment<CollectCarSourcePresenter> implements CollectCarSourceContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private CarSourceAdapter mAdapter;
    private List<LineBean> mList;
    private int type;

    public static CollectCarSourceFragment newInstance() {
        return new CollectCarSourceFragment();
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
        return R.layout.fragment_collect_car_source;
    }

    @Override
    protected void init() {
        initView();
    }


    private void initView() {
        initRefresh();
        initRecycler();
    }


    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                type = Const.MORE;
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                getCarSource(num);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                type = Const.REFRESH;
                getCarSource(Const.PAGE_NUM);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new CarSourceAdapter(R.layout.item_car_source, mList);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty, mRecycler);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<LineBean> lineBeans = adapter.getData();
        LineBean lineBean = lineBeans.get(position);
        if (lineBean != null) {
            CarSourceDetailActivity.action(mContext, lineBean.getId());
        }
    }

    @Override
    public void onCollectLines(ListResponse<LineBean> mLineBeans) {
        switch (type) {
            case Const.REFRESH:
                mAdapter.setNewData(mLineBeans.getRecords());
                break;
            case Const.MORE:
                mAdapter.addData(mLineBeans.getRecords());
                break;
        }
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }


    private void getCarSource(int num) {
        mPresenter.getCollectLines(num, Const.PAGE_SIZE);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<LineBean> lineBeans = adapter.getData();
        LineBean lineBean = lineBeans.get(position);
        switch (view.getId()) {
            case R.id.item_phone:
                AppUtils.callPhone(mContext, lineBean.getDriverPhone());
                break;
            case R.id.item_chat:
                if (UserManager.getUserManager().isVerified()) {
                    if (!TextUtils.isEmpty(lineBean.getDriverPhone())) {
                        EaseChatActivity.action(mContext, EaseConstant.CHATTYPE_SINGLE, "driver"+lineBean.getDriverPhone());
                    }else {
                        toast("手机号码为空");
                    }
                } else {
                    toast("未认证");
                }

                break;
        }
    }
}

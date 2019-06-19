package com.js.shipper.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.js.shipper.model.event.CitySelectEvent;
import com.js.shipper.model.event.SortEvent;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.park.activity.CarSourceDetailActivity;
import com.js.shipper.ui.main.adapter.CarSourceAdapter;
import com.js.shipper.ui.main.presenter.CarSourcePresenter;
import com.js.shipper.ui.main.presenter.contract.CarSourceContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.widget.adapter.Divider;
import com.js.shipper.widget.window.CityWindow;
import com.js.shipper.widget.window.FilterWindow;
import com.js.shipper.widget.window.SortWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/30.
 * 车源
 */
public class CarSourceFragment extends BaseFragment<CarSourcePresenter> implements CarSourceContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.condition_layout)
    LinearLayout mCondition;
    @BindView(R.id.send_address)
    TextView mSendAddress;
    @BindView(R.id.end_address)
    TextView mEndAddress;
    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.filter)
    TextView mFilter;

    private CarSourceAdapter mAdapter;
    private List<LineBean> mList;
    private int type;
    private CityWindow mStartWindow;
    private CityWindow mEndWindow;
    private SortWindow mSortWindow;
    private FilterWindow mFilterWindow;
    private LineAppFind lineAppFind = new LineAppFind();
    private String arriveAddressCode = "0";
    private String startAddressCode = "0";
    private int sort;

    public static CarSourceFragment newInstance() {
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
    }


    private void initView() {
        initRefresh();
        initRecycler();
        initCityWindow();
    }

    private void initCityWindow() {
        mStartWindow = new CityWindow(mContext, 0);
        mEndWindow = new CityWindow(mContext, 1);
        mSortWindow = new SortWindow(mContext);
        mFilterWindow = new FilterWindow(mContext);
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
//        mAdapter.setEmptyView(AppUtils.getEmptyView());
        mAdapter.setOnItemClickListener(this);
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
    public void onCarSource(ListResponse<LineBean> mLineBeans) {
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

    @OnClick({R.id.send_address, R.id.end_address, R.id.sort, R.id.filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_address:
                mStartWindow.showAsDropDown(mCondition, 0, 0);
                break;
            case R.id.end_address:
                mEndWindow.showAsDropDown(mCondition, 0, 0);
                break;
            case R.id.sort:
                mSortWindow.showAsDropDown(mCondition, 0, 0);
                break;
            case R.id.filter:

                break;
        }
    }

    @Subscribe
    public void onEvent(CitySelectEvent event) {
        switch (event.type) {
            case 0:
                startAddressCode = event.areaBean.getCode();
                if (!TextUtils.isEmpty(event.areaBean.getAlias())) {
                    mSendAddress.setText(event.areaBean.getAlias());
                } else {
                    mSendAddress.setText(event.areaBean.getName());
                }

                break;
            case 1:
                arriveAddressCode = event.areaBean.getCode();
                if (!TextUtils.isEmpty(event.areaBean.getAlias())) {
                    mEndAddress.setText(event.areaBean.getAlias());
                } else {
                    mEndAddress.setText(event.areaBean.getName());
                }
                break;
        }

        getCarSource(Const.PAGE_NUM);
    }


    @Subscribe
    public void onEvent(SortEvent sortEvent) {
        sort = sortEvent.type;

    }

    private void getCarSource(int num) {
        if ("发货地".equals(mSendAddress.getText().toString())) {
            lineAppFind.setStartAddressCode("0");
        } else {
            lineAppFind.setStartAddressCode(startAddressCode);
        }
        if ("收货地".equals(mEndAddress.getText().toString())) {
            lineAppFind.setArriveAddressCode("0");
        } else {
            lineAppFind.setArriveAddressCode(arriveAddressCode);
        }

        lineAppFind.setSort(sort);

        mPresenter.getCarSource(num, lineAppFind, Const.PAGE_SIZE);
    }
}

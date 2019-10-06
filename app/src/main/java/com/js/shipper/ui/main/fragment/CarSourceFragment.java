package com.js.shipper.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.event.CitySelectEvent;
import com.js.shipper.model.event.FilterEvent;
import com.js.shipper.model.event.SortEvent;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.presenter.DictPresenter;
import com.js.shipper.presenter.contract.DictContract;
import com.js.shipper.ui.message.chat.EaseChatActivity;
import com.js.shipper.ui.park.activity.CarSourceDetailActivity;
import com.js.shipper.ui.main.adapter.CarSourceAdapter;
import com.js.shipper.ui.main.presenter.CarSourcePresenter;
import com.js.shipper.ui.main.presenter.contract.CarSourceContract;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.UserManager;
import com.js.shipper.widget.adapter.Divider;
import com.js.shipper.widget.window.CityWindow;
import com.js.shipper.widget.window.FilterWindow;
import com.js.shipper.widget.window.SortWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/30.
 * 车源
 */
public class CarSourceFragment extends BaseFragment<CarSourcePresenter> implements CarSourceContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, DictContract.View {


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

    @Inject
    DictPresenter mDictPresenter;

    private CarSourceAdapter mAdapter;
    private List<LineBean> mList;
    private int type;
    private CityWindow mStartWindow;
    private CityWindow mEndWindow;
    private SortWindow mSortWindow;
    private FilterWindow mFilterWindow;
    private String arriveAddressCode = "0";
    private String startAddressCode = "0";
    private int sort = 1;
    private String lengthStr;
    private String typeStr;

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
        initData();
    }

    private void initData() {
        mDictPresenter.getDictByType(Const.DICT_CAR_TYPE_NAME);
        mDictPresenter.getDictByType(Const.DICT_LENGTH_NAME);
        mDictPresenter.getDictByType(Const.DICT_USE_CAR_TYPE_NAME);
    }


    private void initView() {
        mDictPresenter.attachView(this);
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
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                getCarSource(num);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCarSource(Const.PAGE_NUM);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new CarSourceAdapter(R.layout.item_car_source, mList);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<LineBean> lineBeans = adapter.getData();
        LineBean lineBean = lineBeans.get(position);
        switch (view.getId()) {
            case R.id.item_phone:
                AppUtils.callPhone(mContext,lineBean.getDriverPhone());
                break;
            case R.id.item_chat:
                if (UserManager.getUserManager().isVerified()) {
                    EaseChatActivity.action(mContext, EaseConstant.CHATTYPE_SINGLE, lineBean.getDriverPhone());
                } else {
                    toast("未认证");
                }

                break;
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
                mFilterWindow.showAsDropDown(mCondition, 0, 0);
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
                getCarSource(Const.PAGE_NUM);
                break;
            case 1:
                arriveAddressCode = event.areaBean.getCode();
                if (!TextUtils.isEmpty(event.areaBean.getAlias())) {
                    mEndAddress.setText(event.areaBean.getAlias());
                } else {
                    mEndAddress.setText(event.areaBean.getName());
                }
                getCarSource(Const.PAGE_NUM);
                break;
        }
    }

    @Subscribe
    public void onEvent(SortEvent sortEvent) {
        sort = sortEvent.type;
        mSort.setText(sort == 1 ? "默认排序" : "距离排序");
        getCarSource(Const.PAGE_NUM);
    }

    @Subscribe
    public void onEvent(FilterEvent event){
        lengthStr = event.lengthStr;
        typeStr = event.typeStr;
        getCarSource(Const.PAGE_NUM);
    }

    private void getCarSource(int num) {
        if (num == Const.PAGE_NUM) {
            type = Const.REFRESH;
        } else {
            type = Const.MORE;
        }
        LineAppFind lineAppFind = new LineAppFind();
        if (startAddressCode.length() == 6) {
            lineAppFind.setStartAddressCode(startAddressCode);
        }
        if (arriveAddressCode.length() == 6) {
            lineAppFind.setArriveAddressCode(arriveAddressCode);
        }
        lineAppFind.setSort(sort);
        if (!TextUtils.isEmpty(lengthStr)) {
            lineAppFind.setCarLength(lengthStr);
        }
        if (!TextUtils.isEmpty(typeStr)) {
            lineAppFind.setCarModel(typeStr);
        }
        mPresenter.getCarSource(num, lineAppFind, Const.PAGE_SIZE);
    }

    @Override
    public void onDictByType(String type, List<DictBean> dictBeans) {
        switch (type) {
            case Const.DICT_CAR_TYPE_NAME:
                mFilterWindow.setTypes(dictBeans);
                break;
            case Const.DICT_LENGTH_NAME:
                mFilterWindow.setCarLengths(dictBeans);
                break;
            case Const.DICT_USE_CAR_TYPE_NAME:
                mFilterWindow.setCarTypes(dictBeans);
                break;
        }
    }
}

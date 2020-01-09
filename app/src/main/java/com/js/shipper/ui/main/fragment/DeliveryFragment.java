package com.js.shipper.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.base.frame.view.BaseFragment;
import com.hyphenate.easeui.EaseConstant;
import com.js.community.CommunityApp;
import com.js.shipper.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.event.CitySelectEvent;
import com.js.shipper.model.event.CompanyEvent;
import com.js.shipper.model.event.SortEvent;
import com.js.shipper.model.request.ParkList;
import com.js.shipper.model.response.ListResponse;
import com.js.shipper.ui.main.adapter.DeliveryAdapter;
import com.js.shipper.ui.main.presenter.DeliveryPresenter;
import com.js.shipper.ui.main.presenter.contract.DeliveryContract;
import com.js.message.ui.chat.EaseChatActivity;
import com.js.shipper.ui.park.activity.BranchDetailActivity;
import com.js.shipper.util.AppUtils;
import com.js.shipper.util.MapUtils;
import com.js.shipper.util.UserManager;
import com.js.shipper.widget.adapter.Divider;
import com.js.shipper.widget.window.CityWindow;
import com.js.shipper.widget.window.CompanyWindow;
import com.js.shipper.widget.window.SortWindow;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/30.
 * 专线
 */
public class DeliveryFragment extends BaseFragment<DeliveryPresenter> implements DeliveryContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.area)
    TextView mArea;
    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.branch)
    TextView mBranch;
    @BindView(R.id.condition_layout)
    LinearLayout mCondition;

    private DeliveryAdapter mAdapter;
    private List<ParkBean> mLists;
    private int type;
    private int sort = 1;
    private String areaCode = "0";
    private String companyType;
    private double latitude;
    private double longitude;

    private CityWindow mAreaWindow;
    private SortWindow mSortWindow;
    private CompanyWindow mCompanyWindow;
    private String[] items = {"百度地图", "高德地图"};

    public static DeliveryFragment newInstance() {
        return new DeliveryFragment();
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
        return R.layout.fragment_delivery;
    }

    @Override
    protected void init() {
        initData();
        initView();
    }

    private void initData() {
        BDLocation mLocation = App.getInstance().mLocation;
        if (mLocation != null
                && !TextUtils.isEmpty(mLocation.getAdCode()) && mLocation.getAdCode().length() == 6) {
            mArea.setText(mLocation.getCity());
            areaCode = mLocation.getAdCode();
            areaCode = areaCode.substring(0, 4) + "00";
        }
    }

    private void initCityWindow() {
        mAreaWindow = new CityWindow(mContext, 2);
        mSortWindow = new SortWindow(mContext);
        mCompanyWindow = new CompanyWindow(mContext);
    }

    private void initView() {
        initCityWindow();
        initAdapter();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int num = (int) Math.ceil(((float) mAdapter.getItemCount() / Const.PAGE_SIZE)) + 1;
                getParkList(num);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getParkList(Const.PAGE_NUM);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new DeliveryAdapter(R.layout.item_delivery, mLists);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setEmptyView(R.layout.layout_data_empty, mRecycler);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
    }

    private void getParkList(int num) {
        if (num == Const.PAGE_NUM) {
            type = Const.REFRESH;
        } else {
            type = Const.MORE;
        }
        ParkList mPark = new ParkList();
//        if (!TextUtils.isEmpty(companyType)) {
//            mPark.setCompanyType(companyType);
//        }
        mPark.setCompanyType("1");
        if (areaCode.length() == 6) {
            mPark.setAddressCode(areaCode);
        }
        mPark.setSort(sort);
        mPark.setLatitude(latitude);
        mPark.setLongitude(longitude);
        mPresenter.getParkList(num, Const.PAGE_SIZE, mPark);
    }

    @Override
    public void onParkList(ListResponse<ParkBean> listResponse) {
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
        switch (view.getId()) {
            case R.id.remark_status_layout:

                parkBean.setRemark(!parkBean.isRemark());
                mAdapter.setNewData(parkBeans);
                break;
            case R.id.navigation:
                if (App.getInstance().mLocation == null) {
                    toast("定位失败");
                    return;
                }
                Gson gson = new Gson();
                LatLng latLng = new LatLng(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude());
                LatLng endLat = gson.fromJson(parkBean.getContactLocation(), LatLng.class);
                if (endLat == null || endLat.latitude == 0 || endLat.latitude == 0) {
                    toast("位置错误");
                    return;
                }
                showSelectDialog(latLng, endLat, parkBean.getAddress());
                break;
            case R.id.item_phone:
                AppUtils.callPhone(mContext, parkBean.getContractPhone());
                break;
            case R.id.item_chat:
                if (UserManager.getUserManager().isVerified()) {
                    if (!TextUtils.isEmpty(parkBean.getContractPhone())) {
                        EaseChatActivity.action(mContext, EaseConstant.CHATTYPE_SINGLE, "driver" + parkBean.getContractPhone());
                    }
                } else {
                    toast("未认证");
                }


                break;
        }
    }

    @OnClick({R.id.area_layout, R.id.branch_layout, R.id.sort_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.area_layout:
                mAreaWindow.showAsDropDown(mCondition, 0, 0);
                break;
            case R.id.branch_layout:
                mCompanyWindow.showAsDropDown(mCondition, 0, 0);
                break;
            case R.id.sort_layout:
                mSortWindow.showAsDropDown(mCondition, 0, 0);
                break;
        }
    }

    public void showSelectDialog(LatLng start, LatLng end, String dName) {
        new CircleDialog.Builder(getActivity())
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) { //百度地图
                            MapUtils.startBaidu(mContext, start, end, dName);
                        } else {//高德地图
                            MapUtils.startGaode(mContext, start, end, dName);
                        }
                    }
                })
                .setNegative("取消", null)
                .show();
    }

    @Subscribe
    public void onEvent(SortEvent sortEvent) {
        sort = sortEvent.type;
        mSort.setText(sort == 1 ? "默认排序" : "距离排序");
        if (sort == 1) {
            latitude = 0;
            longitude = 0;
        } else {
            if (App.getInstance().mLocation==null){
                return;
            }
            latitude = App.getInstance().mLocation.getLatitude();
            longitude = App.getInstance().mLocation.getLongitude();
        }
        getParkList(Const.PAGE_NUM);
    }

    @Subscribe
    public void onEvent(CitySelectEvent event) {
        switch (event.type) {
            case 2:
                areaCode = event.areaBean.getCode();
                if (!TextUtils.isEmpty(event.areaBean.getAlias())) {
                    mArea.setText(event.areaBean.getAlias());
                } else {
                    mArea.setText(event.areaBean.getName());
                }
                getParkList(Const.PAGE_NUM);
                break;
        }
    }

    @Subscribe
    public void onEvent(CompanyEvent event) {
        mBranch.setText(event.content);
        if (!"全部".equals(event.content)) {
            companyType = String.valueOf(event.position);
        } else {
            companyType = "";
        }
        getParkList(Const.PAGE_NUM);
    }

}
